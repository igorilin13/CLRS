package igorilin13.com.github.test.datastructures.hash;

class TestInteger {
    private final int value;

    TestInteger(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TestInteger testInteger = (TestInteger) o;
        return value == testInteger.value;
    }

    @Override
    public int hashCode() {
        return value;
    }
}
