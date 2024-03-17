
import edu.princeton.cs.algs4.MinPQ;
import game.Board;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolutionAlgortihm {
    private List<Board> solution;
    private boolean isSolvable;

    public SolutionAlgortihm(Board initialState) {
        if (initialState == null || !initialState.isSolvable()) {
            System.out.println("The puzzle is not solvable");
            isSolvable = false;
        } else {
            isSolvable = true;
        }

        MinPQ<SearchNode> priorityQueue = new MinPQ<>();
        Map<Board, Integer> visited = new HashMap<>();
        priorityQueue.insert(new SearchNode(initialState, 0, null));

        while (!priorityQueue.isEmpty()) {
            SearchNode current = priorityQueue.delMin();

            // If current board is the goal, build the solution path.
            if (current.board.isGoalState()) {
                solution = new ArrayList<>();
                for (SearchNode node = current; node != null; node = node.previous) {
                    solution.add(node.board);
                }
                Collections.reverse(solution);
                break;
            }

            for (Board neighbor : current.board.neighbors()) {
                if (current.previous == null || !neighbor.equals(current.previous.board)) {
                    SearchNode next = new SearchNode(neighbor, current.moves + 1, current);

                    // Only consider this neighbor if it has not been visited or has a lower
                    // priority.
                    if (!visited.containsKey(neighbor) || visited.get(neighbor) > next.priority) {
                        priorityQueue.insert(next);
                        visited.put(neighbor, next.priority);
                    }
                }
            }
        }
    }

    public boolean isSolvable() {
        return isSolvable;
    }

    public Iterable<Board> returnSolution() {
        return solution;
    }

    public int moves() {
        return isSolvable() ? solution.size() - 1 : -1;
    }
    

    private static class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int moves;
        private final SearchNode previous;
        private final int priority;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            this.priority = board.manhattan() + moves;
        }

        @Override
        public int compareTo(SearchNode that) {
            return Integer.compare(this.priority, that.priority);
        }
    }
}
