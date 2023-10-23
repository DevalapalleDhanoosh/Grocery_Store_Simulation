package Grocery_Store;

import java.util.Map;

public class Customer {
    String name;
    Map<String, Integer> shoppingList;
    double budget;

    public Customer(String name, Map<String, Integer> shoppingList, double budget) {
        this.name = name;
        this.shoppingList = shoppingList;
        this.budget = budget;
    }
}
