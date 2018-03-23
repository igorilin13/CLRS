package igorilin13.com.github.test.datastructures.hash;

import igorilin13.com.github.main.datastructures.hash.ChainedHashTable;
import org.junit.Test;

public class ChainedHashTableTest extends BaseTableTest {
    @Test
    public void testDivisionHash() {
        testTableOperations(() -> new ChainedHashTable<>(ChainedHashTable.Type.DIVISION));
    }

    @Test
    public void testMultiplicationHash() {
        testTableOperations(() -> new ChainedHashTable<>(ChainedHashTable.Type.MULTIPLICATION));
    }
}
