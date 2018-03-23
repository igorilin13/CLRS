package igorilin13.com.github.main.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UniqueRandomInt {
    private final List<Integer> pool;
    private int currentIndex = -1;

    public UniqueRandomInt(int least, int bound) {
        if (bound <= least) {
            throw new IllegalArgumentException("Precondition failed: bound > least");
        }
        pool = new ArrayList<>(bound - least);
        for (int i = least; i < bound; i++) {
            pool.add(i);
        }
        Collections.shuffle(pool);
    }

    public int next() {
        currentIndex++;
        if (currentIndex == pool.size()) {
            Collections.shuffle(pool);
            currentIndex = 0;
        }
        return pool.get(currentIndex);
    }
}
