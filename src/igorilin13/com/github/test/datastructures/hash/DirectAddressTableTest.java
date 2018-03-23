package igorilin13.com.github.test.datastructures.hash;

import igorilin13.com.github.main.datastructures.hash.DirectAddressTable;
import org.junit.Test;

public class DirectAddressTableTest extends BaseTableTest {
    @Test
    public void testDirectAddressTable() {
        testTableOperations(() -> new DirectAddressTable<>(MAX_VALUE));
    }
}
