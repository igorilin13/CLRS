package igorilin13.com.github.main.parallel;

import java.util.concurrent.RecursiveTask;

public class FibNumbers {
    public static int compute(int n) {
        if (n < 2) {
            return n;
        } else {
            RecursiveTask<Integer> task = new FibNumberTask(n - 1);
            task.fork();
            return task.join() + compute(n - 2);
        }
    }

    private static class FibNumberTask extends RecursiveTask<Integer> {
        private final int n;

        private FibNumberTask(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n < 2) {
                return n;
            } else {
                return FibNumbers.compute(n - 1) + FibNumbers.compute(n - 2);
            }
        }
    }
}
