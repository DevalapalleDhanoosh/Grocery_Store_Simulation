import java.util.*;

class Product {
    String name;
    double price;
    int quantity;
    String category;
    String unit;

    public Product(String name, double price, int quantity, String category, String unit) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.unit = unit;
    }
}

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

public class GroceryStoreSimulation {
    static HashMap<String, Product> inventory = new HashMap<>();
    static LinkedList<Product> shoppingCart = new LinkedList<>();
    static ArrayList<Customer> customerList = new ArrayList<>();

    public static void main(String[] args) {
        // Initialize products in the inventory
        initializeInventory();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Are you a Customer or an Administrator?");
        System.out.print("Enter 'C' for Customer or 'A' for Administrator: ");
        String userType = scanner.nextLine();

        if (userType.equalsIgnoreCase("C")) {
            // Simulate customer arrivals and shopping
            simulateCustomerArrivals();
            simulateShopping();
            simulateCheckout();
        } else if (userType.equalsIgnoreCase("A")) {
            System.out.println("\nWelcome Administrator!");
            System.out.print("Do you want to view the list of customers and their orders? (yes/no): ");
            String viewData = scanner.nextLine();

            if (viewData.equalsIgnoreCase("yes")) {
                viewCustomerData();
            } else {
                System.out.println("Exiting...");
            }
        } else {
            System.out.println("Invalid input. Exiting...");
        }
    }

    static void initializeInventory() {
        // Add products to the inventory
        inventory.put("Apple", new Product("Apple", 1.0, 10, "Fruits", "units"));
        inventory.put("Milk", new Product("Milk", 2.0, 20, "Dairy", "liters"));
        inventory.put("Bread", new Product("Bread", 1.5, 15, "Bakery", "loaves"));
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
                System.out.print("Enter product name: ");
                String productName = scanner.nextLine();
                Product product = inventory.get(productName);

                if (product == null) {
                    System.out.println("Invalid product name. Please choose from the available items.");
                    continue;
                }

                System.out.print("Enter quantity (in " + product.unit + "): ");
                int quantity = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                shoppingList.put(productName, quantity);
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

    static void simulateCheckout() {
        double totalCost = 0;

        System.out.println("\nChecking out...");
        for (Product product : shoppingCart) {
            totalCost += product.price;
        }

        System.out.println("Total cost: $" + totalCost);

        // Clear the shopping cart
        shoppingCart.clear();
    }

    static void viewCustomerData() {
        System.out.println("\nList of Customers and Their Orders:");
        for (Customer customer : customerList) {
            System.out.println("Customer Name: " + customer.name);
            System.out.println("Budget: $" + customer.budget);
            System.out.println("Shopping List:");
            for (Map.Entry<String, Integer> entry : customer.shoppingList.entrySet()) {
                String productName = entry.getKey();
                int quantity = entry.getValue();
                System.out.println(productName + ": " + quantity + " " + inventory.get(productName).unit);
            }
            System.out.println();
        }
    }
}


/*
                                                   OUTPUT

"C:\Program Files\Java\jdk-1.8\bin\java.exe" "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2023.2\lib\idea_rt.jar=50582:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2023.2\bin" -Dfile.encoding=UTF-8 -classpath "C:\Program Files\Java\jdk-1.8\jre\lib\charsets.jar;C:\Program Files\Java\jdk-1.8\jre\lib\deploy.jar;C:\Program Files\Java\jdk-1.8\jre\lib\ext\access-bridge-64.jar;C:\Program Files\Java\jdk-1.8\jre\lib\ext\cldrdata.jar;C:\Program Files\Java\jdk-1.8\jre\lib\ext\dnsns.jar;C:\Program Files\Java\jdk-1.8\jre\lib\ext\jaccess.jar;C:\Program Files\Java\jdk-1.8\jre\lib\ext\jfxrt.jar;C:\Program Files\Java\jdk-1.8\jre\lib\ext\localedata.jar;C:\Program Files\Java\jdk-1.8\jre\lib\ext\nashorn.jar;C:\Program Files\Java\jdk-1.8\jre\lib\ext\sunec.jar;C:\Program Files\Java\jdk-1.8\jre\lib\ext\sunjce_provider.jar;C:\Program Files\Java\jdk-1.8\jre\lib\ext\sunmscapi.jar;C:\Program Files\Java\jdk-1.8\jre\lib\ext\sunpkcs11.jar;C:\Program Files\Java\jdk-1.8\jre\lib\ext\zipfs.jar;C:\Program Files\Java\jdk-1.8\jre\lib\javaws.jar;C:\Program Files\Java\jdk-1.8\jre\lib\jce.jar;C:\Program Files\Java\jdk-1.8\jre\lib\jfr.jar;C:\Program Files\Java\jdk-1.8\jre\lib\jfxswt.jar;C:\Program Files\Java\jdk-1.8\jre\lib\jsse.jar;C:\Program Files\Java\jdk-1.8\jre\lib\management-agent.jar;C:\Program Files\Java\jdk-1.8\jre\lib\plugin.jar;C:\Program Files\Java\jdk-1.8\jre\lib\resources.jar;C:\Program Files\Java\jdk-1.8\jre\lib\rt.jar;C:\Users\dhano\IdeaProjects\Grocery_Store_Simulation\out\production\Grocery_Store_Simulation" GroceryStoreSimulation
Enter customer name (or 'q' to quit): Dhanoosh
Enter customer budget: 5000
How many items do you want to purchase? 4
Enter product name: Milk
Enter quantity (in units): 1 litre
Enter product name: Rice
Enter quantity (in units): 1 Kg
Enter product name: Wheat
Enter quantity (in units): 1 Kg
Enter product name: Apple
Enter quantity (in units): 1 Kg
Enter customer name (or 'q' to quit): q

Customer Dhanoosh is shopping...
Added 1 Apple(s) to cart.
Unable to add 1 Wheat(s) to cart.
Added 1 Milk(s) to cart.
Unable to add 1 Rice(s) to cart.

Checking out...
Total cost: $3.0

Process finished with exit code 0

 */