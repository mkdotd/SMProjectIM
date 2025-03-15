package com.inventory;

import UtilityFunctions.IOFunction;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        IOFunction.ValidateUserNamePassword();

        InventoryManager inventoryManager = new InventoryManager();
        inventoryManager.loadInventory();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\1nInventory Management System");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Product Quantity");
            System.out.println("4. Delete Product");
            System.out.println("5. Search Product");
            System.out.println("6. Generate Inventory Report");
            System.out.println("7. Sort Products by Name");
            System.out.println("8. Sort Products by Price");
            System.out.println("9. Check Low Stock Products");
            System.out.println("10. Filter Products by Price Range");
            System.out.println("11. Clear Inventory");
            System.out.println("12. Save and Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> new AddProduct(inventoryManager).execute();
                case 2 -> new ViewProducts(inventoryManager).execute();
                case 3 -> new UpdateQuantity(inventoryManager).execute();
                case 4 -> new DeleteProduct(inventoryManager).execute();
                case 5 -> new SearchProduct(inventoryManager).execute();
                case 6 -> System.out.println("Inventory Report:\n" + inventoryManager.getInventory());
                case 7 -> new SortProductsByName(inventoryManager).execute();
                case 8 -> new SortProductsByPrice(inventoryManager).execute();
                case 9 -> new CheckLowStock(inventoryManager).execute();
                case 10 -> new FilterByPriceRange(inventoryManager).execute();
                case 11 -> new ClearInventory(inventoryManager).execute();
                case 12 -> {
                    inventoryManager.saveInventory();
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}