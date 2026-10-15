import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VendorTest {

    @Test
    void addMoneyTest(){
        Vending vendor = new Vending("Vendor", 10, 10);

        vendor.addMoney(5.00);

        assertEquals(5.00, vendor.getBalance(), 0.001);
    }

    @Test
    void buyItemTest(){
        Vending vendor = new Vending("Vendor", 10, 10);

        vendor.addMoney(1.25);
        vendor.select("Candy");

        assertEquals(0.0, vendor.getBalance(), 0.001);
    }


    @Test
    void emptyInventoryTest(){
        Vending vendor = new Vending("Vendor", 10, 10);
    }

    @Test
    void restockItemTest() {
        Vending vendor = new Vending("Vendor", 10, 10);
        vendor.restockItem("Candy", 5);

        assertEquals(15, vendor.getItemStock("Candy"));
    }

    @Test
    void restockNewItemTest() {
        Vending vendor = new Vending("Vendor", 10, 10);

        vendor.restockNewItem("Chocolate", 2.00, 5, "Sweets");

        assertEquals(5, vendor.getItemStock("Chocolate"));
    }


    @Test
    void renameItemTest() {
        Vending vendor = new Vending("Vendor", 10, 10);

        vendor.renameItem("Candy", "Chocolate");

        assertEquals(10, vendor.getItemStock("Chocolate"));
        assertEquals(0, vendor.getItemStock("Candy"));
    }

    @Test
    void multipleVendorsTest() {
        Vending vendor1 = new Vending("Vendor", 10, 10);
        Vending vendor2 = new Vending("Vendor", 10, 10);

        vendor1.restockItem("Candy", 5);

        assertEquals(15, vendor1.getItemStock("Candy"));
        assertEquals(5, vendor2.getItemStock("Candy"));
    }

    @Test
    void removeItemTest() {
        Vending vendor = new Vending("Vendor", 10, 10);

        assertTrue(vendor.removeItem("Gum"));
        assertEquals(0, vendor.getItemStock("Gum"));
        assertEquals(10, vendor.getItemStock("Candy"));
    }

    @Test
    void removeMissingItemTest() {
        Vending vendor = new Vending("Vendor", 10, 10);
        assertFalse(vendor.removeItem("Pizza"));
    }

    @Test
    void purchaseCountTest() {
        Vending vendor = new Vending("Vendor",10, 10);
        vendor.addMoney(5.00);

        vendor.select("Candy");
        vendor.select("Candy");
        vendor.select("Gum");

        assertEquals(2, vendor.getPurchaseCount("Candy"));
        assertEquals(1, vendor.getPurchaseCount("Gum"));
    }

    @Test
    void failedPurchaseNotCountedTest() {
        Vending vendor = new Vending("Vendor",10, 10);
        vendor.select("Candy"); // no money
        assertEquals(0, vendor.getPurchaseCount("Candy"));
    }

    @Test
    void mostPopularItemTest() {
        Vending vendor = new Vending("Vendor",10, 10);
        vendor.addMoney(10.00);

        vendor.select("Gum");
        vendor.select("Gum");
        vendor.select("Candy");

        assertEquals("Gum", vendor.getMostPopularItem());
    }

    @Test
    void mostPopularItemNoSalesTest() {
        Vending vendor = new Vending("Vendor",10, 10);
        assertNull(vendor.getMostPopularItem());
    }
    @Test
    void itemDescriptionTest() {
        Vending vendor = new Vending("Vendor", 10, 10);
        vendor.setItemDescription("Candy", "Sweet and chewy");

        String details = vendor.getItemDescription("Candy");

        assertTrue(details.contains("Sweet and chewy"));
        assertTrue(details.contains("1.25"));
    }

    @Test
    void unknownItemDescriptionTest() {
        Vending vendor = new Vending("Vendor", 10, 10);
        assertEquals("Sorry, don't know that item", vendor.getItemDescription("Pizza"));
    }
}