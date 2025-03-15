package com.inventory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckLowStockTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final ByteArrayInputStream inputStream = new ByteArrayInputStream("John\n".getBytes());
    private final InputStream originalIn = System.in;
    private InventoryManager inventoryManager;
    private CheckLowStock checkLowStock;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
        System.setIn(inputStream);
        inventoryManager = mock(InventoryManager.class);
        checkLowStock = new CheckLowStock(inventoryManager);
    }

    @Test
    public void testCheckLowStockNotFound() {
        //Arrange
        String expectedOutput = "Low Stock Products (Quantity < 10):\r\nNo products with low stock.\r\n";
        List<Product> products= new ArrayList<>();
        when(inventoryManager.getInventory()).thenReturn(products);

        //Act
        checkLowStock.execute();

        //Assert
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testCheckLowStockFound() {
        //Arrange
        String expectedOutput = "Low Stock Products (Quantity < 10):\r\nID: 1, Name: Test, Quantity: 5, Price: 12.0\r\n";
        List<Product> products= new ArrayList<>();
        Product lowStockProduct = new Product(1, "Test", 5, 12);
        products.add(lowStockProduct);
        when(inventoryManager.getInventory()).thenReturn(products);

        //Act
        checkLowStock.execute();

        //Assert
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testCheckLowStockGetInventory() {
        //Arrange
        List<Product> products= new ArrayList<>();
        when(inventoryManager.getInventory()).thenReturn(products);

        //Act
        checkLowStock.execute();

        //Assert
        verify(inventoryManager).getInventory();
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }
}