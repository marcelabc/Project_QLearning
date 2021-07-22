import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class QLearning {
    private static double _alpha = 0.5;
    private static double _gamma = 0.8;

    private static double _qMatrix[];
    private static double _rewardMatrix[] = new double[] { -1, -1, -1, -1, -1,
            6 };

    public static void main(String[] args) {
        Map<Integer, List<String>> paths = new HashMap<>();

        paths.put(1, Arrays.asList("UP", "UP", "UP", "RG"));
        paths.put(5, Arrays.asList("LF", "LF", "RG", "UP"));

        for (Entry<Integer, List<String>> entry : paths.entrySet()) {
            _qMatrix = new double[6];
            Integer state = entry.getKey();

            List<String> actions = entry.getValue();
            for (String action : actions) {
                state = calculateQ(state, action);

                if (state == 6) {
                    break;
                }
            }

        }
    }

    private static Integer calculateQ(Integer state, String action) {
        Integer nextState = state;
        Double reward = 0D;

        if (action.equals("UP")) {
            if ((state == 3) || (state == 6)) {
                reward = -10D;
            } else {
                nextState = state + 1;
            }
        } else if (action.equals("DW")) {
            if ((state == 1) || (state == 4)) {
                reward = -10D;
            } else {
                nextState = state - 1;
            }
        } else if (action.equals("LF")) {
            if ((state == 1) || (state == 2) || (state == 3)) {
                reward = -10D;
            } else {
                nextState = state - 3;
            }
        } else if (action.equals("RG")) {
            if ((state == 4) || (state == 5) || (state == 6)) {
                reward = -10D;
            } else {
                nextState = state + 3;
            }
        }

        if (reward == 0) {
            reward = _rewardMatrix[nextState - 1];
        }

        _qMatrix[state - 1] = _qMatrix[state - 1]
                + (_alpha * ((reward + (_gamma * _qMatrix[nextState - 1]))
                - _qMatrix[state - 1]));

        printMatrix();
        printPolicy(state, nextState);

        return nextState;
    }

    private static void printMatrix() {
        System.out.println("--------------------------");
        System.out.println("Q matrix");
        for (int pos = 2; pos >= 0; pos--) {
            String value = String.format("%6.4f", _qMatrix[pos]);
            for (int i = 0; i < (7 - value.length()); i++) {
                System.out.print(" ");
            }
            System.out.print(value);
            value = String.format("%6.4f", _qMatrix[pos + 3]);
            for (int i = 0; i < (8 - value.length()); i++) {
                System.out.print(" ");
            }
            System.out.print(value);
            System.out.println();
        }
        System.out.println();
    }

    private static void printPolicy(Integer state, Integer nextState) {
        System.out.println("Policy");
        System.out.println("From state " + state + " goto state " + nextState);
    }
}
