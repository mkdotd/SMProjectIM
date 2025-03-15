package com.inventory.integration;

import com.inventory.AddProduct;
import com.inventory.DeleteProduct;
import com.inventory.InventoryManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class DeleteProductTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final ByteArrayInputStream inputStream = new ByteArrayInputStream("5\nDelete\n10\n25".getBytes());
    private final InputStream originalIn = System.in;
    private InventoryManager inventoryManager;
    private AddProduct addProduct;
    private DeleteProduct deleteProduct;

    DeleteProductTest(){
        inventoryManager = new InventoryManager();
        inventoryManager.loadInventory();
        addProduct = new AddProduct(inventoryManager);
        deleteProduct = new DeleteProduct(inventoryManager);
        System.setIn(inputStream);
        addProduct.execute();
        inventoryManager.saveInventory();
    }

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
        System.setIn(inputStream);
    }

    @Test
    public void testDeleteProductValidInputPrompts() throws Exception {
        //Arrange
        String input = "5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        System.setOut(new PrintStream(outputStream));
        String expectedOutput = "Enter Product ID to Delete: Product deleted if it existed.\r\nInventory saved. Goodbye!\r\n";
        var existingRecord = inventoryManager.findProductById(5);

        //Act
        deleteProduct.execute();
        inventoryManager.saveInventory();

        //Assert
        assertEquals(expectedOutput, outputStream.toString());
        inventoryManager.loadInventory();
        assertNotNull(existingRecord);
        assertNull(inventoryManager.findProductById(5));
    }

    @Test
    public void testDeleteProductInValidInput() {
        //Arrange
        String input = "999\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        System.setOut(new PrintStream(outputStream));
        String expectedOutput = "Enter Product ID to Delete: Product deleted if it existed.\r\n";

        //Act
        deleteProduct.execute();

        //Assert
        assertEquals(expectedOutput, outputStream.toString());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }
}