package Deliverr

import org.junit.Test

import static org.junit.Assert.*

class InventoryAllocatorTest {
    @Test
    void testEmptyOrderVal() throws Exception {
        InventoryAllocator dv = new InventoryAllocator()
        String orderString = "{}"
        String invent = "[ { \"name\": \"owd\", \"inventory\": { \"apple\": 5, \"orange\": 10 } }, { \"name\": \"dm\", \"inventory\": { \"banana\": 5, \"orange\": 10 } } ]"
        List<Warehouse> w = dv.customerOrder(orderString, invent)
        assertEquals(w, [])
    }

    @Test
    void testEmptyInventVal() throws IOException {
        InventoryAllocator dv = new InventoryAllocator()
        String orderString = "{\"apple\": 1}"
        String invent = "[]"
        List<Warehouse> w = dv.customerOrder(orderString, invent)
        assertEquals(w, [])
    }

    @Test
    void testCase1() throws IOException {
        InventoryAllocator dv = new InventoryAllocator()
        String orderString = "{\"apple\": 5,\"banana\": 5}"
        String invent = "[ { \"name\": \"owd\", \"inventory\": { \"apple\": 5, \"orange\": 10 } }, { \"name\": \"dm\", \"inventory\": { \"banana\": 5, \"orange\": 10 } } ]"
        List<Warehouse> w = dv.customerOrder(orderString, invent)
        for (Warehouse each : w) {
            assertNotNull(each.getName())
        }
        assertEquals(w[0].getName(), "owd")
        assertEquals(w[1].getName(), "dm")
        assertEquals(w.size(), 2)
    }

    @Test
    void testCase2() throws IOException {
        InventoryAllocator dv = new InventoryAllocator()
        String orderString = "{\"apple\": 15,\"orange\": 12}"
        String invent = "[ { \"name\": \"owd\", \"inventory\": { \"apple\": 5, \"orange\": 10 } }, { \"name\": \"dm\", \"inventory\": { \"banana\": 5, \"orange\": 10 } } ]"
        List<Warehouse> w = dv.customerOrder(orderString, invent)
        for (Warehouse each : w) {
            assertNotNull(each.getName())
        }
        assertEquals(w[0].getName(), "owd")
        assertEquals(w[1].getName(), "dm")
        assertEquals(w[0].getInventory().get("apple"), 5)
        assertEquals(w.size(), 2)
    }

    @Test
    void testCase3() throws IOException {
        InventoryAllocator dv = new InventoryAllocator()
        String orderString = "{\"apple\": 1}"
        String invent = "[ { \"name\": \"owd\", \"inventory\": { \"apple\": 0}} ]"
        List<Warehouse> w = dv.customerOrder(orderString, invent)
        assertEquals(w.size(), 0)
    }

    @Test
    void testCase4() throws IOException {
        InventoryAllocator dv = new InventoryAllocator()
        String orderString = "{\"apple\": 1}"
        String invent = "[ { \"name\": \"owd\", \"inventory\": { \"apple\": 1}} ]"
        List<Warehouse> w = dv.customerOrder(orderString, invent)
        assertEquals(w.size(), 1)
    }

    @Test
    void testCase5() throws IOException {
        InventoryAllocator dv = new InventoryAllocator()
        String orderString = "{\"kiwi\": 15,\"mango\": 12}"
        String invent = "[ { \"name\": \"owd\", \"inventory\": { \"apple\": 5, \"orange\": 10 } }, { \"name\": \"dm\", \"inventory\": { \"banana\": 5, \"orange\": 10 } } ]"
        List<Warehouse> w = dv.customerOrder(orderString, invent)
        assertEquals(w, [])
    }

    @Test
    void testCase6() throws IOException {
        InventoryAllocator dv = new InventoryAllocator()
        String orderString = "{\"orange\": 15}"
        String invent = "[ { \"name\": \"owd\", \"inventory\": { \"apple\": 5, \"orange\": 5 } }, { \"name\": \"dm\", \"inventory\": { \"banana\": 5, \"orange\": 10 } } ]"
        List<Warehouse> w = dv.customerOrder(orderString, invent)
        assertEquals(w[0].getInventory().get("orange"), 5)
        assertEquals(w[1].getInventory().get("orange"), 10)
    }

    @Test
    void testCase7() throws IOException {
        InventoryAllocator dv = new InventoryAllocator()
        String orderString = "{\"apple\":5,\"banana\":5,\"orange\": 5}"
        String invent = "[ { \"name\": \"owd\", \"inventory\": { \"apple\": 5, \"orange\": 10 } }, { \"name\": \"dm\", \"inventory\": { \"banana\": 5, \"orange\": 10 } } ]"
        List<Warehouse> w = dv.customerOrder(orderString, invent)
        assertEquals(w[0].getInventory().get("orange"), 5)
        assertEquals(w[0].getInventory().get("apple"), 5)
        assertEquals(w[1].getInventory().get("banana"), 5)
    }

    @Test
    void testCase8() throws IOException {
        InventoryAllocator dv = new InventoryAllocator()
        String orderString = "{\"apple\":2,\"banana\":1,\"orange\": 4}"
        String invent = "[ { \"name\": \"owd\", \"inventory\": { \"apple\": 5, \"orange\": 10 } }, { \"name\": \"dm\", \"inventory\": { \"banana\": 5, \"orange\": 10 } } ]"
        List<Warehouse> w = dv.customerOrder(orderString, invent)
        assertEquals(w[0].getInventory().get("orange"), 4)
        assertEquals(w[0].getInventory().get("apple"), 2)
        assertEquals(w[1].getInventory().get("banana"), 1)
    }
}
