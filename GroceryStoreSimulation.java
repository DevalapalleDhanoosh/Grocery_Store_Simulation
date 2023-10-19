import java.util.*;

public class GroceryStoreSimulation {
    static HashMap<String, Product> inventory = new HashMap<>();
    static LinkedList<Product> shoppingCart = new LinkedList<>();
    static ArrayList<Customer> customerList = new ArrayList<>();

    public static void main(String[] args) {
        // Initialize products in the inventory
        initializeInventory();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Are you a Customer ?");
        System.out.print("Enter 'C' for Customer : ");
        String userType = scanner.nextLine();

        if (userType.equalsIgnoreCase("C")) {
            // Simulate customer arrivals and shopping
            simulateCustomerArrivals();
            simulateShopping();
            displayBillAndThankYou();
        } else {
            System.out.println("Invalid input. Exiting...");
        }
    }

    static void initializeInventory() {
        // Add products to the inventory
        inventory.put("Apple", new Product("Apple", 1.0, 10, "Fruits", "Kg"));
        inventory.put("Milk", new Product("Milk", 2.0, 20, "Dairy", "liters"));
        inventory.put("Bread", new Product("Bread", 1.5, 15, "Bakery", "loaves"));
        inventory.put("Orange", new Product("Orange", 1.0, 10, "Fruits", "Kg"));
        // Add more products as needed
    }

    static void simulateCustomerArrivals() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter customer name (or 'q' to quit): ");
            String name = scanner.nextLine();

            if (name.equals("q")) {
                break;
            }

            System.out.print("Enter customer budget: ");
            double budget = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character

            HashMap<String, Integer> shoppingList = new HashMap<>();
            Random random = new Random();

            System.out.println("\nItems available in inventory:");
            for (Product product : inventory.values()) {
                System.out.println(product.name + " - Price: $" + product.price + " per " + product.unit + ", Quantity: " + product.quantity);
            }

            System.out.print("\nHow many items do you want to purchase? ");
            int numProducts = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            for (int i = 0; i < numProducts; i++) {
                boolean validProduct = false;
                do {
                    System.out.print("Enter product name: ");
                    String productName = scanner.nextLine();
                    Product product = inventory.get(productName);

                    if (product == null) {
                        System.out.println("Invalid product name. Please choose from the available items.");
                    } else {
                        System.out.print("Enter quantity (in " + product.unit + "): ");
                        int quantity = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character
                        shoppingList.put(productName, quantity);
                        validProduct = true;
                    }
                } while (!validProduct);
            }

            Customer customer = new Customer(name, budget, shoppingList);
            customerList.add(customer);
        }
    }

    static void simulateShopping() {
        for (Customer customer : customerList) {
            System.out.println("\nCustomer " + customer.name + " is shopping...");

            for (Map.Entry<String, Integer> entry : customer.shoppingList.entrySet()) {
                String productName = entry.getKey();
                int quantity = entry.getValue();

                Product product = inventory.get(productName);

                if (product != null && product.quantity >= quantity && product.price * quantity <= customer.budget) {
                    shoppingCart.add(product);
                    product.quantity -= quantity;
                    customer.budget -= product.price * quantity;
                    System.out.println("Added " + quantity + " " + productName + "(s) to cart.");
                } else {
                    System.out.println("Unable to add " + quantity + " " + productName + "(s) to cart.");
                }
            }
        }
    }

    static void displayBillAndThankYou() {
        double totalCost = 0;

        System.out.println("\nDisplaying Bill...");
        for (Product product : shoppingCart) {
            totalCost += product.price;
        }

        System.out.println("Total cost: $" + totalCost);
        System.out.println("Thank you for shopping!");
    }
}









/*
                                                   OUTPUT

Are you a Customer ?
Enter 'C' for Customer : c
Enter customer name (or 'q' to quit): Dhanoosh
Enter customer budget: 100

Items available in inventory:
Apple - Price: $1.0 per Kg, Quantity: 10
Orange - Price: $1.0 per Kg, Quantity: 10
Milk - Price: $2.0 per liters, Quantity: 20
Bread - Price: $1.5 per loaves, Quantity: 15

How many items do you want to purchase? 2
Enter product name: Orange
Enter quantity (in Kg): 5
Enter product name: brew
Invalid product name. Please choose from the available items.
Enter product name: Bread
Enter quantity (in loaves): 6
Enter customer name (or 'q' to quit): q

Customer Dhanoosh is shopping...
Added 5 Orange(s) to cart.
Added 6 Bread(s) to cart.

Displaying Bill...
Total cost: $2.5
Thank you for shopping!

Process finished with exit code 0

 */