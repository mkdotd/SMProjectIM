package com.inventory;

import java.util.Scanner;

public class AddProduct {
    private final InventoryManager inventoryManager;

    public AddProduct(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Product ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        while(inventoryManager.findProductById(id) != null) {
            System.out.print("Product already exists. Please choose another product ID: ");
            id = scanner.nextInt();
            scanner.nextLine();
        }
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        while(inventoryManager.findProductByName(name) != null) {
            System.out.print("Product Name already exists. Please choose another product Name: ");
            name = scanner.nextLine();
        }
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        inventoryManager.addProduct(new Product(id, name, quantity, price));
        System.out.println("Product added successfully.");
    }
}
