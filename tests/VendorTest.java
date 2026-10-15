import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VendorTest {

    @Test
    void addMoneyTest(){
        Vending vendor = new Vending(10, 10);

        vendor.addMoney(5.00);

        assertEquals(5.00, vendor.getBalance(), 0.001);
    }

    @Test
    void buyItemTest(){
        Vending vendor = new Vending(10, 10);

        vendor.addMoney(1.25);
        vendor.select("Candy");

        assertEquals(0.0, vendor.getBalance(), 0.001);
    }


    @Test
    void emptyInventoryTest(){
        Vending vendor = new Vending(10, 10);
    }

    @Test
    void restockItemTest() {
        Vending vendor = new Vending(10, 10);

        vendor.restockItem("Candy", 5);

        assertEquals(15, vendor.getItemStock("Candy"));
    }

    @Test
    void restockNewItemTest() {
        Vending vendor = new Vending(10, 10);

        vendor.restockNewItem("Chocolate", 2.00, 5);

        assertEquals(5, vendor.getItemStock("Chocolate"));
    }


    @Test
    void renameItemTest() {
        Vending vendor = new Vending(10, 10);

        vendor.renameItem("Candy", "Chocolate");

        assertEquals(10, vendor.getItemStock("Chocolate"));
        assertEquals(0, vendor.getItemStock("Candy"));
    }

    @Test
    void multipleVendorsTest() {
        Vending vendor1 = new Vending(10, 10);
        Vending vendor2 = new Vending(5, 3);

        vendor1.restockItem("Candy", 5);

        assertEquals(15, vendor1.getItemStock("Candy"));
        assertEquals(5, vendor2.getItemStock("Candy"));
    }
}