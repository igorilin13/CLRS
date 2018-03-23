package igorilin13.com.github.test.datastructures.hash;

import igorilin13.com.github.main.datastructures.hash.Table;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

public abstract class BaseTableTest {
    static final int MAX_VALUE = 1000;

    private Table<TestInteger> table;
    private List<TestInteger> values = new ArrayList<>();

    @Before
    public void setUp() {
        values.clear();
        for (int i = 0; i < MAX_VALUE; i++) {
            values.add(new TestInteger(i));
        }
    }

    void testTableOperations(Supplier<Table<TestInteger>> tableSupplier) {
        table = tableSupplier.get();

        int inserted = 0;
        for (int i = 0; i < MAX_VALUE; i++) {
            TestInteger value = values.get(i);
            verifyMembership(false, value);

            try {
                table.insert(value);
            } catch (OutOfMemoryError e) {
                break;
            }
            verifyMembership(true, value);
            inserted++;
        }

        for (int i = 0; i < inserted; i++) {
            TestInteger value = values.get(i);
            table.delete(value);
            verifyMembership(false, value);
        }
    }

    private void verifyMembership(boolean shouldContain, TestInteger value) {
        assertEquals(shouldContain, table.contains(value));
        assertEquals(shouldContain ? value : null, table.get(value.hashCode()));
    }
}
