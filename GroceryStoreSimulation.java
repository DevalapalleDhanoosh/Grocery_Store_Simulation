package Grocery_Store;

import java.util.*;
import java.util.LinkedList;

public class GroceryStoreSimulation {
    static List<Product> inventory = new ArrayList<>();
    static Queue<Customer> customerQueue = new LinkedList<>();

    public static void initializeInventory() {
        // Add some products to the inventory
        inventory.add(new Product("Apple", 2.0, 20, "Fruits"));
        inventory.add(new Product("Bread", 1.5, 20, "Bakery"));
        inventory.add(new Product("Orange", 2.0, 20, "Fruits"));
        inventory.add(new Product("Watermelon", 2.0, 20, "Fruits"));
        inventory.add(new Product("Onion", 2.0, 20, "Vegetable"));
        inventory.add(new Product("Tomato", 2.0, 20, "Vegetable"));
        inventory.add(new Product("Aata", 2.0, 20, "Flour"));
        inventory.add(new Product("Maidha", 2.0, 20, "Flour"));
        // Add more products as needed
    }

    public static void displayAvailableProducts() {
        System.out.println("Available Products:");
        for (Product product : inventory) {
            System.out.println(product.name + " - $" + product.price + " (" + product.quantity + " available)");
        }
        System.out.println();
    }

    public static Customer generateRandomCustomer() {
        Random random = new Random();
        String[] names = { "Alice", "Bob", "Charlie", "David", "Eve" };
        String name = names[random.nextInt(names.length)];
        double budget = 20 + random.nextDouble() * 80; // Random budget between $20 and $100

        Map<String, Integer> shoppingList = new HashMap<>();
        for (Product product : inventory) {
            if (random.nextBoolean() && product.quantity > 0 && product.price <= budget) {
                int quantity = random.nextInt(5) + 1; // Random quantity between 1 and 5
                shoppingList.put(product.name, quantity);
                budget -= quantity * product.price; // Deduct cost from budget
            }
        }

        return new Customer(name, shoppingList, budget);
    }

    public static void simulateShopping(Customer customer) {
        double totalCost = 0.0;

        for (Map.Entry<String, Integer> entry : customer.shoppingList.entrySet()) {
            String item = entry.getKey();
            int quantity = entry.getValue();

            for (Product product : inventory) {
                if (product.name.equalsIgnoreCase(item)) {
                    totalCost += product.price * quantity;
                    product.quantity -= quantity;
                    break;
                }
            }
        }

        System.out.println(customer.name + " has finished shopping. Total cost: $" + totalCost);

        customer.budget -= totalCost;
    }

    public static void main(String[] args) {
        initializeInventory();

        // Generate random customers and add them to the queue
        for (int i = 0; i < 5; i++) {
            Customer customer = generateRandomCustomer();
            customerQueue.add(customer);
        }

        // Process customers from the queue
        while (!customerQueue.isEmpty()) {
            Customer customer = customerQueue.poll();
            System.out.println("Customer: " + customer.name);
            displayAvailableProducts();
            simulateShopping(customer);
        }

        // Print updated inventory
        System.out.println("\nUpdated Inventory:");
        for (Product product : inventory) {
            System.out.println(product.name + " - $" + product.price + " (" + product.quantity + " available)");
        }
    }
}
