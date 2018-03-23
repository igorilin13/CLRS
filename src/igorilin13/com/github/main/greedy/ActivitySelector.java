package igorilin13.com.github.main.greedy;

import java.util.HashSet;
import java.util.Set;

public class ActivitySelector {

    public static Set<Integer> recursiveActivitySelector(int[] startTime, int[] finishTime) {
        return recursiveActivitySelector(startTime, finishTime, 0, startTime.length - 1);
    }

    private static Set<Integer> recursiveActivitySelector(int[] activityStart,
                                                          int[] activityFinish, int firstActivity,
                                                          int lastActivity) {
        int current = firstActivity + 1;
        while (current <= lastActivity && activityStart[current] < activityFinish[firstActivity]) {
            current++;
        }

        if (current <= lastActivity) {
            Set<Integer> result = new HashSet<>();
            result.add(current);
            Set<Integer> next = recursiveActivitySelector(activityStart, activityFinish, current, lastActivity);
            if (next != null) {
                result.addAll(next);
            }
            return result;
        }

        return null;
    }

    public static Set<Integer> greedyActivitySelector(int[] startTime, int[] finishTime) {
        int activitiesNumber = startTime.length;
        Set<Integer> result = new HashSet<>();
        result.add(1);
        int current = 1;
        for (int next = 2; next < activitiesNumber; next++) {
            if (startTime[next] >= finishTime[current]) {
                result.add(next);
                current = next;
            }
        }
        return result;
    }
}
