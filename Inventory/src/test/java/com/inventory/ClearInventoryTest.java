package com.inventory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClearInventoryTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final ByteArrayInputStream inputStream = new ByteArrayInputStream("John\n".getBytes());
    private final InputStream originalIn = System.in;
    private InventoryManager inventoryManager;
    private ClearInventory clearInventory;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
        System.setIn(inputStream);
        inventoryManager = mock(InventoryManager.class);
        clearInventory = new ClearInventory(inventoryManager);
    }

    @Test
    public void testInventoryClearPrompts() {
        //Arrange
        String input = "Yes\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        String expectedOutput = "Are you sure you want to clear the inventory? (yes/no): Inventory cleared.\r\n";
        doNothing().when(inventoryManager).clearInventory();

        //Act
        clearInventory.execute();

        //Assert
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testInventoryClearPromptsCaseInsensitive() {
        //Arrange
        String input = "YES\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        String expectedOutput = "Are you sure you want to clear the inventory? (yes/no): Inventory cleared.\r\n";
        doNothing().when(inventoryManager).clearInventory();

        //Act
        clearInventory.execute();

        //Assert
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testInventoryClearCancelPrompts() {
        //Arrange
        String input = "No\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        String expectedOutput = "Are you sure you want to clear the inventory? (yes/no): Operation cancelled.\r\n";
        doNothing().when(inventoryManager).clearInventory();

        //Act
        clearInventory.execute();

        //Assert
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testClearInventoryManagerCall() {
        //Arrange
        String input = "Yes\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        doNothing().when(inventoryManager).clearInventory();

        //Act
        clearInventory.execute();

        //Assert
        verify(inventoryManager).clearInventory();
    }

    @Test
    public void testClearInventoryManagerShouldNotCall() {
        //Arrange
        String input = "No\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        doNothing().when(inventoryManager).clearInventory();

        //Act
        clearInventory.execute();

        //Assert
        verify(inventoryManager, never()).clearInventory();
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }
}