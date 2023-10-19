import java.util.HashMap;

class Customer {
    String name;
    double budget;
    HashMap<String, Integer> shoppingList;

    public Customer(String name, double budget, HashMap<String, Integer> shoppingList) {
        this.name = name;
        this.budget = budget;
        this.shoppingList = shoppingList;
    }
}
