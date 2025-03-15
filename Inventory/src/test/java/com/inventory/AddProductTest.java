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

class AddProductTest {

  private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final ByteArrayInputStream inputStream = new ByteArrayInputStream("John\n".getBytes());
  private final InputStream originalIn = System.in;
  private InventoryManager inventoryManager;
  private AddProduct addProduct;

  @BeforeEach
  public void setUp() {
    System.setOut(new PrintStream(outputStream));
    System.setIn(inputStream);
    inventoryManager = mock(InventoryManager.class);
    addProduct = new AddProduct(inventoryManager);
  }

  @Test
  public void testAddProductValidInputPrompts() {
    //Arrange
    String input = "1\nTest\n10\n25\n";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    String expectedOutput = "Enter Product ID: Enter Product Name: Enter Quantity: Enter Price: Product added successfully.\r\n";

    //Act
    addProduct.execute();

    //Assert
    assertEquals(expectedOutput, outputStream.toString());
  }

  @Test
  public void testAddProductInventoryManagerSave() {
    //Arrange
    String input = "1\nTest\n10\n25\n";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    //Act
    addProduct.execute();

    //Assert
    verify(inventoryManager).addProduct(any(Product.class));
  }

  @AfterEach
  public void tearDown() {
    System.setOut(originalOut);
    System.setIn(originalIn);
  }
}