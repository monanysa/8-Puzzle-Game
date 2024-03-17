package example;
import java.util.*;

public class puzzle8 {
    static class State {
        int[][] board;
        int x;
        int y;
        int cost;
        int depth;
        State parent;

        State(int[][] board, int x, int y, int cost, int depth, State parent) {
            this.board = new int[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    this.board[i][j] = board[i][j];
                }
            }
            this.x = x;
            this.y = y;
            this.cost = cost;
            this.depth = depth;
            this.parent = parent;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return Arrays.deepEquals(board, state.board);
        }

        @Override
        public int hashCode() {
            return Arrays.deepHashCode(board);
        }
    }

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) {

        int[][] board = new int[3][3];
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = sc.nextInt();
            }
        }
        PriorityQueue<State> pq = new PriorityQueue<>(new Comparator<State>() {
            @Override
            public int compare(State o1, State o2) {
                return o1.cost - o2.cost;
            }
        });
        int[] pos = new int[2];
        find_zero(board, pos);
        State initialState = new State(board, pos[0], pos[1], Manhattan(board), 0, null);
        pq.add(initialState);

        State goalState = null;

        while (!pq.isEmpty()) {
            State cur = pq.poll();
            board = cur.board;

            if (Manhattan(board) == 0) {
                goalState = cur;
                break;
            }

            find_zero(board, pos);
            int x = pos[0];
            int y = pos[1];
            for (int k = 0; k < 4; k++) {
                int nx = x + dx[k];
                int ny = y + dy[k];
                if (nx >= 0 && nx < 3 && ny >= 0 && ny < 3) {
                    int[][] new_board = new int[3][3];
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            new_board[i][j] = board[i][j];
                        }
                    }
                    new_board[x][y] = new_board[nx][ny];
                    new_board[nx][ny] = 0;
                    var newState = new State(new_board, nx, ny, Manhattan(new_board) + cur.depth + 1, cur.depth + 1, cur);
                    pq.add(newState);
                }
            }
        }

        if (goalState != null) {
            Stack<State> path = new Stack<>();
            State currentState = goalState;
            while (currentState != null) {
                path.push(currentState);
                currentState = currentState.parent;
            }

            while (!path.isEmpty()) {
                State state = path.pop();
                printArr(state.board);
            }
        } else {
            System.out.println("No solution found.");
        }
    }

    static void printArr(int[][] arr) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    static void find_zero(int[][] arr, int[] pos) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (arr[i][j] == 0) {
                    pos[0] = i;
                    pos[1] = j;
                    return;
                }
            }
        }
    }

    static int Manhattan(int[][] arr) {
        int ans = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (arr[i][j] == 0) continue;
                int x = (arr[i][j] - 1) % 3;
                int y = (arr[i][j] - 1) / 3;
                ans += Math.abs(x - i) + Math.abs(y - j);
            }
        }
        return ans;
    }
}