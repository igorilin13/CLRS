package igorilin13.com.github.test.datastructures.hash;

import igorilin13.com.github.main.datastructures.hash.OpenAddressTable;
import org.junit.Test;

public class OpenAddressTableTest extends BaseTableTest {
    @Test
    public void testLinearProbing() {
        testTableOperations(() -> new OpenAddressTable<>(OpenAddressTable.Type.LINEAR));
    }

    @Test
    public void testQuadraticProbing() {
        testTableOperations(() -> new OpenAddressTable<>(OpenAddressTable.Type.QUADRATIC));
    }

    @Test
    public void testDoubleProbing() {
        testTableOperations(() -> new OpenAddressTable<>(OpenAddressTable.Type.DOUBLE));
    }
}
