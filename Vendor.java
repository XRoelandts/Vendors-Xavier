import java.util.HashMap;


/**
 * Class for a Vending Machine.  Contains a hashtable mapping item names to item data, as
 * well as the current balance of money that has been deposited into the machine.
 */
class Vending {
    private HashMap<String, Item> stock = new HashMap<String,Item>();
    private double balance;

    Vending(int numCandy, int numGum) {
        stock.put("Candy", new Item(1.25, numCandy));
        stock.put("Gum", new Item(.5, numGum));
        this.balance = 0;
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
    void select (String name) {
        if (stock.containsKey(name)) {
            Item item = stock.get(name);
            if (balance >= item.price) {
                item.purchase(1);
                this.balance = this.balance - item.price;
            }
            else
                System.out.println("Gimme more money");
        }
        else System.out.println("Sorry, don't know that item");
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

    void restockNewItem(String name, double price, int amount) {
        if (stock.containsKey(name)) {
            stock.get(name).restock(amount);
        } else {
            stock.put(name, new Item(price, amount));
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
        System.out.println("Vendor Inventory:");

        for (String name : stock.keySet()) {
            Item item = stock.get(name);

            System.out.println(
                    name + " - Price: $" + item.price
                            + ", Stock: " + item.stock
            );
        }
    }



}


class Examples {
}

