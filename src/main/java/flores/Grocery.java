package flores;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Grocery {
    private Map<String, Integer> inventory;
    private Scanner sc;

    public Grocery() {
        this.inventory = new HashMap<>();
        this.sc = new Scanner(System.in);
    }

    public void run() {
        displayMenu();
        int choice;
        do {
            System.out.print("Choose option: ");
            choice = getUserChoice();

            switch (choice) {
                case 1:
                    viewInventory();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    checkProduct();
                    break;
                case 4:
                    updateStock();
                    break;
                case 5:
                    removeProduct();
                    break;
                case 6:
                    System.out.println("Exiting System...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 6);
        sc.close();
    }

    private void displayMenu() {
        System.out.println("\n--- Grocery Inventory Menu ---");
        System.out.println("1. View Inventory");
        System.out.println("2. Add Product");
        System.out.println("3. Check Product");
        System.out.println("4. Update Stock");
        System.out.println("5. Remove Product");
        System.out.println("6. Exit");
    }

    private int getUserChoice() {
        while (true) {
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                return choice;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number from 1-6.");
                sc.next();
                System.out.print("Choose option: ");
            }
        }
    }

    public void addProduct() {
        System.out.print("Enter product name: ");
        String productName = sc.nextLine().trim();

        if (productName.isEmpty()) {
            System.out.println("Product name cannot be empty.");
            return;
        }

        int quantity = -1;
        while (quantity < 0) {
            try {
                System.out.print("Enter quantity: ");
                quantity = sc.nextInt();
                if (quantity < 0) {
                    System.out.println("Quantity cannot be negative. Please enter a positive number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number for quantity.");
                sc.next();
            } finally {
                sc.nextLine();
            }
        }
        inventory.put(productName, inventory.getOrDefault(productName, 0) + quantity);
        System.out.println(productName + " added!");
        //System.out.println(productName + " added. Current stock: " + inventory.get(productName) + " pcs");
    }

    public void checkProduct() {
        System.out.print("Enter product name to check: ");
        String productToCheck = sc.nextLine().trim();

        if (productToCheck.isEmpty()) {
            System.out.println("Product name cannot be empty.");
            return;
        }

        if (inventory.containsKey(productToCheck)) {
            System.out.println(productToCheck + " is in stock: " + inventory.get(productToCheck) + " pcs");
        } else {
            System.out.println(productToCheck + " is not found in the inventory.");
        }
    }

    public void updateStock() {
        System.out.print("Enter product name to update: ");
        String productNameUpd = sc.nextLine().trim();

        if (productNameUpd.isEmpty()) {
            System.out.println("Product name cannot be empty.");
            return;
        }

        if (!inventory.containsKey(productNameUpd)) {
            System.out.println(productNameUpd + " is not found in the inventory.");
            return;
        }

        int newQuantity = -1;
        while (newQuantity < 0) {
            try {
                System.out.print("Enter new stock quantity: ");
                newQuantity = sc.nextInt();
                if (newQuantity < 0) {
                    System.out.println("Quantity cannot be negative. Please enter a positive number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number for quantity.");
                sc.next();
            } finally {
                sc.nextLine();
            }
        }

        inventory.put(productNameUpd, newQuantity);
        System.out.println(productNameUpd + " stock updated to: " + newQuantity + " pcs");
    }

    public void removeProduct() {
        System.out.print("Enter product to remove: ");
        String productToRemove = sc.nextLine().trim();

        if (productToRemove.isEmpty()) {
            System.out.println("Product name cannot be empty.");
            return;
        }

        if (inventory.containsKey(productToRemove)) {
            inventory.remove(productToRemove);
            System.out.println(productToRemove + " removed from inventory.");
        } else {
            System.out.println(productToRemove + " is not found in the inventory.");
        }
    }

    public void viewInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty. Add some products first!");
            return;
        }
        System.out.println("\n--- Current Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue() + " pcs");
        }
    }
}