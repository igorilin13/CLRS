package igorilin13.com.github.main.datastructures.advanced;

import igorilin13.com.github.main.util.MathUtils;
import org.jetbrains.annotations.Nullable;

public class VanEmdeBoasTree {
    private VanEmdeBoasStructure head;

    public VanEmdeBoasTree(int universeSize) {
        head = new VanEmdeBoasStructure(universeSize);
    }

    @Nullable
    public Integer min() {
        return head.min;
    }

    @Nullable
    public Integer max() {
        return head.max;
    }

    public boolean contains(int x) {
        return contains(head, x);
    }

    private boolean contains(VanEmdeBoasStructure current, int x) {
        return current.min != null && (x == current.min || current.max != null && x == current.max)
                || current.uSize != 2 && contains(current.cluster[current.high(x)], current.low(x));
    }

    @Nullable
    public Integer successor(int x) {
        return successor(head, x);
    }

    private Integer successor(VanEmdeBoasStructure current, int x) {
        if (current.uSize == 2) {
            return x == 0 && current.max != null && current.max == 1 ? 1 : null;
        }
        if (current.min != null && x < current.min) {
            return current.min;
        }

        int currentHigh = current.high(x);
        int currentLow = current.low(x);
        Integer maxLow = current.cluster[currentHigh].max;
        if (maxLow != null && currentLow < maxLow) {
            Integer offset = successor(current.cluster[currentHigh], currentLow);
            return offset != null ? current.index(currentHigh, offset) : null;
        } else {
            Integer successorCluster = successor(current.summary, currentHigh);
            if (successorCluster != null) {
                Integer offset = current.cluster[successorCluster].min;
                return current.index(successorCluster, offset);
            } else {
                return null;
            }
        }
    }

    @Nullable
    public Integer predecessor(int x) {
        return predecessor(head, x);
    }

    private Integer predecessor(VanEmdeBoasStructure current, int x) {
        if (current.uSize == 2) {
            return x == 1 && current.min == 0 ? 0 : null;
        }
        if (current.max != null && x > current.max) {
            return current.max;
        }
        Integer currentHigh = current.high(x);
        Integer currentLow = current.low(x);
        Integer minLow = current.cluster[currentHigh].min;
        if (minLow != null && currentLow > minLow) {
            Integer offset = predecessor(current.cluster[currentHigh], currentLow);
            return offset != null ? current.index(currentHigh, offset) : null;
        }
        Integer predecessorCluster = predecessor(current.summary, current.high(x));
        if (predecessorCluster == null) {
            return current.min != null && x > current.min ? current.min : null;
        }
        Integer offset = current.cluster[predecessorCluster].max;
        return current.index(predecessorCluster, offset);
    }

    public void insert(int x) {
        insert(head, x);
    }

    private void insert(VanEmdeBoasStructure current, int x) {
        if (current.min == null) {
            emptyInsert(current, x);
            return;
        }
        if (x < current.min) {
            int swap = current.min;
            current.min = x;
            x = swap;
        }
        if (current.uSize > 2) {
            int currentHigh = current.high(x);
            int currentLow = current.low(x);
            VanEmdeBoasStructure currentHighCluster = current.cluster[currentHigh];
            if (currentHighCluster.min == null) {
                insert(current.summary, currentHigh);
                emptyInsert(currentHighCluster, currentLow);
            } else {
                insert(currentHighCluster, currentLow);
            }
        }
        if (x > current.max) {
            current.max = x;
        }
    }

    private void emptyInsert(VanEmdeBoasStructure node, Integer x) {
        node.min = node.max = x;
    }

    public void delete(int x) {
        delete(head, x);
    }

    private void delete(VanEmdeBoasStructure current, int x) {
        if (current.min == null || current.min.equals(current.max)) {
            emptyInsert(current, null);
            return;
        }
        if (current.uSize == 2) {
            emptyInsert(current, x == 0 ? 1 : 0);
            return;
        }
        if (x == current.min) {
            Integer firstCluster = current.summary.min;
            x = current.index(firstCluster, current.cluster[firstCluster].min);
            current.min = x;
        }
        int currentHigh = current.high(x);
        int currentLow = current.low(x);
        VanEmdeBoasStructure currentHighCluster = current.cluster[currentHigh];
        delete(currentHighCluster, currentLow);
        if (currentHighCluster.min == null) {
            delete(current.summary, currentHigh);
            if (x == current.max) {
                Integer summaryMax = current.summary.max;
                if (summaryMax == null) {
                    current.max = current.min;
                } else {
                    current.max = current.index(summaryMax, current.cluster[summaryMax].max);
                }
            }
        } else if (x == current.max) {
            current.max = current.index(currentHigh, currentHighCluster.max);
        }
    }

    private static class VanEmdeBoasStructure {
        private final int uSize;
        private final int uSize_upper;
        private final int uSize_lower;
        private VanEmdeBoasStructure summary;
        private VanEmdeBoasStructure[] cluster;

        @Nullable
        private Integer min;

        @Nullable
        private Integer max;

        private VanEmdeBoasStructure(int uSize) {
            this.uSize = uSize;
            uSize_upper = MathUtils.pow2((int) Math.ceil(MathUtils.log2(uSize) / 2));
            uSize_lower = MathUtils.pow2((int) Math.floor(MathUtils.log2(uSize) / 2));

            if (uSize != 2) {
                summary = new VanEmdeBoasStructure(uSize_upper);
                cluster = new VanEmdeBoasStructure[uSize_upper];
                for (int i = 0; i < uSize_upper; i++) {
                    cluster[i] = new VanEmdeBoasStructure(uSize_upper);
                }
            }
        }

        private int high(int x) {
            return (int) Math.floor(x / (double) uSize_lower);
        }

        private int low(int x) {
            return x % uSize_lower;
        }

        private int index(int x, int y) {
            return x * uSize_lower + y;
        }
    }
}
