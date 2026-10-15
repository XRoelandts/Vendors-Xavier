import java.util.*;


/**
 * Class for a Vending Machine.  Contains a hashtable mapping item names to item data, as
 * well as the current balance of money that has been deposited into the machine.
 */
class Vending {
    private Map<String, Item> stock = new LinkedHashMap<>();
    private double balance;
    private String vendorName;

    Vending(String vendorName, int numCandy, int numGum) {
        this.vendorName = vendorName;
        stock.put("Candy", new Item(1.25, numCandy, "Sweets"));
        stock.put("Gum", new Item(.5, numGum, "Sweets"));
        this.balance = 0;
    }

    String getVendorName() {
        return vendorName;
    }

    /** resets the Balance to 0 */
    void resetBalance () {
        this.balance = 0;
    }

    /** returns the current balance */
    double getBalance () {
        return this.balance;
    }

    /** adds money to the machine's balance
     * @param amt how much money to add
     * */
    void addMoney (double amt) {
        this.balance = this.balance + amt;
    }

    /** attempt to purchase named item.  Message returned if
     * the balance isn't sufficient to cover the item cost.
     *
     * @param name The name of the item to purchase ("Candy" or "Gum")
     */
    void select(String name) {
        if (stock.containsKey(name)) {
            Item item = stock.get(name);
            if (item.stock <= 0) {
                System.out.println("Sorry, " + name + " is sold out");
            } else if (balance >= item.getCurrentPrice()) {
                item.purchase(1);
                this.balance = this.balance - item.getCurrentPrice();
            } else {
                System.out.println("Gimme more money");
            }
        } else {
            System.out.println("Sorry, don't know that item");
        }
    }

    void restockItem (String name, int amount){
        if(stock.containsKey(name)){
            Item item = stock.get(name);
            item.restock(amount);
        }
    }

    int getItemStock(String name) {
        if (stock.containsKey(name)) {
            return stock.get(name).stock;
        }

        return 0;
    }

    void restockNewItem(String name, double price, int amount, String category) {
        if (stock.containsKey(name)) {
            stock.get(name).restock(amount);
        } else {
            stock.put(name, new Item(price, amount, category));
        }
    }


    void renameItem(String oldName, String newName) {
        if (stock.containsKey(oldName) && !stock.containsKey(newName)) {
            Item item = stock.get(oldName);
            stock.remove(oldName);
            stock.put(newName, item);
        }
    }

    void printInventory() {
        System.out.println(vendorName + " Inventory:");
        for (String name : stock.keySet()) {
            Item item = stock.get(name);
            String line = name + " - Price: $" + item.getCurrentPrice() + ", Stock: " + item.stock;
            if (item.discountPercent > 0) line += " (" + item.discountPercent + "% off)";
            if (item.bestseller) line += " [BESTSELLER]";
            System.out.println(line);
        }
    }
    boolean removeItem(String name) {
        return stock.remove(name) != null;
    }
    int getPurchaseCount(String name) {
        return stock.containsKey(name) ? stock.get(name).purchaseCount : 0;
    }

    /** name of the item purchased most often, or null if nothing has sold */
    String getMostPopularItem() {
        String best = null;
        int bestCount = 0;
        for (Map.Entry<String, Item> e : stock.entrySet()) {
            if (e.getValue().purchaseCount > bestCount) {
                best = e.getKey();
                bestCount = e.getValue().purchaseCount;
            }
        }
        return best;
    }

    void printSalesReport() {
        System.out.println(vendorName + " Sales Report:");
        for (Map.Entry<String, Item> e : stock.entrySet()) {
            System.out.println(e.getKey() + " - Sold: " + e.getValue().purchaseCount);
        }
    }

    void setItemDescription(String name, String description) {
        if (stock.containsKey(name)) {
            stock.get(name).description = description;
        }
    }

    String getItemDescription(String name) {
        if (stock.containsKey(name)) {
            Item item = stock.get(name);
            return name + ": " + item.description + " (Category: " + item.category
                    + ", Price: $" + item.getCurrentPrice() + ")";
        }
        return "Sorry, don't know that item";
    }
    void applyItemDiscount(String name, double percent) {
        checkPercent(percent);
        if (stock.containsKey(name)) {
            stock.get(name).discountPercent = percent;
        }
    }

    void applyCategoryDiscount(String category, double percent) {
        checkPercent(percent);
        for (Item item : stock.values()) {
            if (item.category.equals(category)) {
                item.discountPercent = percent;
            }
        }
    }

    double getItemPrice(String name) {
        return stock.containsKey(name) ? stock.get(name).getCurrentPrice() : 0;
    }

    private void checkPercent(double percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("Discount must be between 0 and 100");
        }
    }
    void markBestseller(String name, boolean isBestseller) {
        if (stock.containsKey(name)) {
            stock.get(name).bestseller = isBestseller;
        }
    }

    boolean isBestseller(String name) {
        return stock.containsKey(name) && stock.get(name).bestseller;
    }

    List<String> getBestsellers() {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Item> e : stock.entrySet()) {
            if (e.getValue().bestseller) result.add(e.getKey());
        }
        return result;
    }


}


class Examples {
}

