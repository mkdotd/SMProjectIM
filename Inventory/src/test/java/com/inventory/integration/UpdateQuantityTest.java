package com.inventory.integration;

import com.inventory.InventoryManager;
import com.inventory.UpdateQuantity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class UpdateQuantityTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final ByteArrayInputStream inputStream = new ByteArrayInputStream("1\nTest\n10\n25".getBytes());
    private final InputStream originalIn = System.in;
    private InventoryManager inventoryManager;
    private UpdateQuantity updateQuantity;

    UpdateQuantityTest(){
        inventoryManager = new InventoryManager();
        inventoryManager.loadInventory();
        updateQuantity = new UpdateQuantity(inventoryManager);
    }

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
        System.setIn(inputStream);
    }

    @Test
    public void testUpdateProductValidInputPrompts() {
        //Arrange
        String input = "1\n25\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        System.setOut(new PrintStream(outputStream));
        String expectedOutput = "Enter Product ID to Update: Enter New Quantity: Product quantity updated.\r\n";

        //Act
        updateQuantity.execute();

        //Assert
        assertEquals(expectedOutput, outputStream.toString());
        assertEquals(25, inventoryManager.findProductById(1).getQuantity());
    }

    @Test
    public void testUpdateProductInValidInputPrompts() {
        //Arrange
        String input = "999\n25\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        System.setOut(new PrintStream(outputStream));
        String expectedOutput = "Enter Product ID to Update: Product not found.\r\n";

        //Act
        updateQuantity.execute();

        //Assert
        assertEquals(expectedOutput, outputStream.toString());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }
}