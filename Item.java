class Item {
    double price;
    int stock;
    String description = "No description available.";
    String category = "General";
    int purchaseCount = 0;        // story 6: purchase tracking
    double discountPercent = 0;   // story 8: discounts
    boolean bestseller = false;   // story 9: bestsellers

    Item(double price, int numPieces) {
        this.price = price;
        this.stock = numPieces;
    }

    Item(double price, int numPieces, String category) {
        this(price, numPieces);
        this.category = category;
    }

    void restock(int amount) {
        this.stock = this.stock + amount;
    }

    void purchase(int amount) {
        this.stock = this.stock - amount;
        this.purchaseCount = this.purchaseCount + amount;
    }

    /** price after any discount is applied */
    double getCurrentPrice() {
        return price * (1 - discountPercent / 100.0);
    }
}