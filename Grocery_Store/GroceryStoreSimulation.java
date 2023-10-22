package Grocery_Store;

import java.util.*;

public class GroceryStoreSimulation {
    static List<Product> inventory = new ArrayList<>();

    public static void initializeInventory() {
        // Add some products to the inventory
        inventory.add(new Product("Apple", 2.0, 20, "Fruits"));
        inventory.add(new Product("Bread", 1.5, 20, "Bakery"));
        inventory.add(new Product("Orange",2.0, 20, "Fruits"));
        inventory.add(new Product("Watermelon",2.0, 20, "Fruits"));
        inventory.add(new Product("Onion",2.0, 20, "Vegetable"));
        inventory.add(new Product("Tomato",2.0, 20, "Vegetable"));
        inventory.add(new Product("Aata",2.0, 20, "Flour"));
        inventory.add(new Product("Maidha",2.0, 20, "Flour"));
        // Add more products as needed
    }

    public static void displayAvailableProducts() {
        System.out.println("Available Products:");
        for (Product product : inventory) {
            System.out.println(product.name + " - $" + product.price + " (" + product.quantity + " available)");
        }
        System.out.println();
    }

    public static Customer getCustomerDetails() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();

        System.out.print("Enter budget: $");
        double budget = scanner.nextDouble();
        scanner.nextLine();  // Consume the newline character

        Map<String, Integer> shoppingList = new HashMap<>();
        while (true) {
            displayAvailableProducts();

            System.out.print("Enter product to add to shopping list (or 'done' to finish): ");
            String item = scanner.nextLine();

            if (item.equalsIgnoreCase("done")) {
                break;
            }

            boolean validItem = false;
            for (Product product : inventory) {
                if (product.name.equalsIgnoreCase(item) && product.quantity > 0 && product.price <= budget) {
                    System.out.print("Enter quantity for " + item + ": ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();  // Consume the newline character

                    if (quantity > 0 && quantity <= product.quantity && quantity * product.price <= budget) {
                        shoppingList.put(item, quantity);
                        validItem = true;
                        budget -= quantity * product.price; // Deduct cost from budget
                    } else {
                        System.out.println("Invalid quantity. Please select a valid quantity within your budget.");
                    }

                    break;
                }
            }

            if (!validItem) {
                System.out.println("Invalid choice. Please select a valid product within your budget.");
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

        Customer customer = getCustomerDetails();
        simulateShopping(customer);

        // Print updated inventory
        System.out.println("\nUpdated Inventory:");
        for (Product product : inventory) {
            System.out.println(product.name + " - $" + product.price + " (" + product.quantity + " available)");
        }
    }
}
