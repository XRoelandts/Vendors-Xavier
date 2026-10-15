import org.junit.jupiter.api.Test;

import java.util.List;

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
        Vending vendor1 = new Vending("Vendor1", 10, 10);
        Vending vendor2 = new Vending("Vendor2", 10, 10);

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

    @Test
    void itemDiscountTest() {
        Vending vendor = new Vending("Vendor",10, 10);

        vendor.applyItemDiscount("Candy", 20);

        assertEquals(1.00, vendor.getItemPrice("Candy"), 0.001);
        assertEquals(0.50, vendor.getItemPrice("Gum"), 0.001);
    }

    @Test
    void discountedPurchaseTest() {
        Vending vendor = new Vending("Vendor", 10, 10);
        vendor.applyItemDiscount("Candy", 20);
        vendor.addMoney(1.00);

        vendor.select("Candy");

        assertEquals(0.0, vendor.getBalance(), 0.001);
        assertEquals(9, vendor.getItemStock("Candy"));
    }

    @Test
    void categoryDiscountTest() {
        Vending vendor = new Vending("Vendor", 10, 10);
        vendor.restockNewItem("Soda", 2.00, 5, "Drinks");

        vendor.applyCategoryDiscount("Sweets", 50);

        assertEquals(0.625, vendor.getItemPrice("Candy"), 0.001);
        assertEquals(0.25, vendor.getItemPrice("Gum"), 0.001);
        assertEquals(2.00, vendor.getItemPrice("Soda"), 0.001);
    }

    @Test
    void invalidDiscountTest() {
        Vending vendor = new Vending("Vending", 10, 10);
        assertThrows(IllegalArgumentException.class, () -> vendor.applyItemDiscount("Candy", 150));
        assertThrows(IllegalArgumentException.class, () -> vendor.applyItemDiscount("Candy", -5));
    }

    @Test
    void bestsellerTest() {
        Vending vendor = new Vending("Vendor", 10, 10);

        vendor.markBestseller("Candy", true);

        assertTrue(vendor.isBestseller("Candy"));
        assertFalse(vendor.isBestseller("Gum"));
        assertEquals(List.of("Candy"), vendor.getBestsellers());
    }

    @Test
    void unmarkBestsellerTest() {
        Vending vendor = new Vending("Vendor",10, 10);
        vendor.markBestseller("Candy", true);
        vendor.markBestseller("Candy", false);
        assertTrue(vendor.getBestsellers().isEmpty());
    }

    @Test
    void bestsellerSurvivesRenameTest() {
        Vending vendor = new Vending("Vendor",10, 10);
        vendor.markBestseller("Candy", true);
        vendor.renameItem("Candy", "Chocolate");
        assertTrue(vendor.isBestseller("Chocolate"));
    }
}