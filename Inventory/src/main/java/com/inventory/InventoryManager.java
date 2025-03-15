package com.inventory;

import java.util.*;
import java.io.*;

public class InventoryManager {
    private final List<Product> inventory = new ArrayList<>();
    private static final String FILE_NAME = "inventory.txt";
    private boolean hasChanges = false;

    public void loadInventory() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                inventory.add(Product.fromString(line));
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing inventory found. Starting fresh.");
        } catch (IOException e) {
            System.err.println("Error loading inventory: " + e.getMessage());
        }
    }

    public void saveInventory() {
        if(hasChanges){
            try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
                for (Product product : inventory) {
                    writer.println(product);
                }
                System.out.println("Inventory saved. Goodbye!");
            } catch (IOException e) {
                System.err.println("Error saving inventory: " + e.getMessage());
            }
        } else {
            System.out.println("No pending changes. Skipping inventory save.");
        }
        hasChanges = false;
    }

    public List<Product> getInventory() {
        return inventory;
    }

    public void addProduct(Product product) {
        inventory.add(product);
        hasChanges = true;
    }

    public Product findProductById(int id) {
        for (Product product : inventory) {
            if (product.getId() == id) return product;
        }
        return null;
    }

    public Product findProductByName(String name) {
        for (Product product : inventory) {
            if (product.getName().equals(name)) return product;
        }
        return null;
    }

    public void removeProduct(int id) {
        inventory.removeIf(product -> product.getId() == id);
        hasChanges = true;
    }

    public void clearInventory() {
        inventory.clear();
    }
}
