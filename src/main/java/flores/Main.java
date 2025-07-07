package flores;


import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static Map<String, Integer> map = new HashMap<>();
    public static void main(String[] args) {

        System.out.println("--- Grocery Inventory Menu ---\n1. View Inventory\n2. Add Product\n3. Check Product\n4. Update Stock\n5. Remove Product\n6. Exit");
        int choice;
        do{
            System.out.print("Choose option: ");
            choice = getUserChoice();
            switch(choice){
                case 1:
                    viewInventory();
                    break;
                case 2:
                    addProduct(map);
                    break;
                case 3:
                    checkProduct(map);
                    break;
                case 4:
                    updateProduct(map);
                    break;
                case 5:
                    removeProduct(map);
                    break;
                case 6:
                    System.out.println("Thank You");
                    break;
                default:
                    System.out.println("Invalid option");
            }
        } while (choice != 6);
        sc.close();
    }

    public static void addProduct(Map<String, Integer> map){
        System.out.print("Enter product name: ");
        String productName = sc.nextLine();
        int quantity = -1;
        while(quantity<0){
            System.out.print("Enter quantity: ");
            quantity = sc.nextInt();
        }
        map.put(productName, quantity);
        System.out.println("Product added!");
    }
    public static void checkProduct(Map<String, Integer> map){
        System.out.print("Enter product name to check: ");
        String productToCheck = sc.nextLine();
        //String key = String.valueOf(map.get(productToCheck));
        //String key = map.get();
        Integer value = map.get(productToCheck);
        System.out.println(productToCheck + " is in stock: " + value);
    }
    public static void updateProduct(Map<String, Integer> map){
        System.out.print("Enter product name to update: ");
        String productNameUpd = sc.nextLine();
        //String value = String.valueOf(map.get(productNameUpd));
        System.out.print("Enter new stock quantity: ");
        int newQuantity = sc.nextInt();
        map.put(productNameUpd, newQuantity);
    }
    public static void removeProduct(Map<String, Integer> map){
        System.out.print("Enter product to remove: ");
        String productToRemove = sc.nextLine();
        map.remove(productToRemove);
        System.out.println("Product removed");
    }
    public static void viewInventory(){
        for(Map.Entry<String, Integer> entry: map.entrySet()){
            System.out.println( entry.getKey() + " - " + entry.getValue() + " pcs");
        }
        /*
        for(String val: map.values()){
            System.out.println(val);
        }
        for (Integer key: map.keySet()){
            System.out.println(key);
        }
        */
    }
    private static int getUserChoice(){
        while(!sc.hasNextInt()){
            System.out.println("Invalid input");
            sc.next();
            System.out.println("Choose option: ");
        }
        int choice = sc.nextInt();
        sc.nextLine();
        return choice;
    }
}