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
}