package com.inventory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AIAddProductTest {

  @Mock
  private InventoryManager inventoryManager;

  @Mock
  private Product product; // if needed for verification

  private AddProduct addProduct;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    addProduct = new AddProduct(inventoryManager);
  }

  @Test
  void testAddProduct_Success() throws Exception {
    // Mock InventoryManager to return no existing product for the given ID and name
    when(inventoryManager.findProductById(1)).thenReturn(null);
    when(inventoryManager.findProductByName("Apple")).thenReturn(null);

    // Prepare input: ID 1, Name Apple, Quantity 10, Price 5.99
    String input = "1\nApple\n10\n5.99\n";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    addProduct.execute();

    // Verify that the product was added with correct parameters
    ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
    verify(inventoryManager).addProduct(productCaptor.capture());

    Product addedProduct = productCaptor.getValue();
    assertEquals(1, addedProduct.getId());
    assertEquals("Apple", addedProduct.getName());
    assertEquals(10, addedProduct.getQuantity());
    assertEquals(5.99, addedProduct.getPrice(), 0.001);

    // Reset System.in to avoid affecting other tests
    System.setIn(System.in);
  }

  @Test
  void testAddProduct_ExistingProductId() throws Exception {
    // Mock to return a product when ID 1 is queried, but not for ID 2
    when(inventoryManager.findProductById(1)).thenReturn(new Product(1, "Existing", 5, 2.99));
    when(inventoryManager.findProductById(2)).thenReturn(null);
    when(inventoryManager.findProductByName("Banana")).thenReturn(null);

    // Input: first ID 1 (taken), then 2 (available), Name Banana, etc.
    String input = "1\n2\nBanana\n20\n9.99\n";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    addProduct.execute();

    // Verify that the second ID (2) is used
//    ArgumentCaptor<Product> productCaptor = ArgumentC
}
  }
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
        import static org.junit.jupiter.api.Assertions.*;

        import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;


  class AIPRoductTest2{
    private InventoryManager mockInventory;
    private AddProduct addProduct;
    private ByteArrayOutputStream outContent;
    private ByteArrayInputStream inContent;
    private PrintStream originalOut;
    private PrintStream originalIn;

    @BeforeEach
    void setUp() {
      mockInventory = Mockito.mock(InventoryManager.class);
      addProduct = new AddProduct(mockInventory);
      originalOut = System.out;
      originalIn =  System.in;
      outContent = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
      System.setOut(originalOut);
      System.setIn(originalIn);
    }

    @Test
    void testAddProduct_Success() {
      when(mockInventory.findProductById(anyInt())).thenReturn(null);
      when(mockInventory.findProductByName(anyString())).thenReturn(null);

      inContent = new ByteArrayInputStream("1\nApple\n10\n0.99\n".getBytes());
      System.setIn(inContent);

      addProduct.execute();

      verify(mockInventory).addProduct(argThat(new ArgumentMatcher<Product>() {
        @Override
        public boolean matches(Product product) {
          return product.getId() == 1 && product.getName().equals("Apple") && product.getQuantity() == 10 && product.getPrice() == 0.99;
        }
      }));
      assertTrue(outContent.toString().contains("Product added successfully."));
    }

    @Test
    void testAddProduct_ExistingId() {
      when(mockInventory.findProductById(1)).thenReturn(new Product(1, "Existing", 5, 2.99));
      when(mockInventory.findProductById(2)).thenReturn(null);
      when(mockInventory.findProductByName(anyString())).thenReturn(null);

      inContent = new ByteArrayInputStream("1\n2\nBanana\n20\n1.99\n".getBytes());
      System.setIn(inContent);

      addProduct.execute();

      verify(mockInventory, times(1)).findProductById(1);
      verify(mockInventory, times(1)).findProductById(2);
      verify(mockInventory).addProduct(argThat(new ArgumentMatcher<Product>() {
        @Override
        public boolean matches(Product product) {
          return product.getId() == 2 && product.getName().equals("Banana") && product.getQuantity() == 20 && product.getPrice() == 1.99;
        }
      }));
      assertTrue(outContent.toString().contains("Product already exists."));
      assertTrue(outContent.toString().contains("Product added successfully."));
    }

    @Test
    void testAddProduct_ExistingName() {
      when(mockInventory.findProductById(anyInt())).thenReturn(null);
      when(mockInventory.findProductByName("Apple")).thenReturn(new Product(1, "Apple", 5, 2.99));
      when(mockInventory.findProductByName("Orange")).thenReturn(null);

      inContent = new ByteArrayInputStream("3\nApple\nOrange\n15\n0.5\n".getBytes());
      System.setIn(inContent);

      addProduct.execute();

      verify(mockInventory, times(1)).findProductByName("Apple");
      verify(mockInventory, times(1)).findProductByName("Orange");
      verify(mockInventory).addProduct(argThat(new ArgumentMatcher<Product>() {
        @Override
        public boolean matches(Product product) {
          return product.getName().equals("Orange") && product.getQuantity() == 15 && product.getPrice() == 0.5;
        }
      }));
      assertTrue(outContent.toString().contains("Product Name already exists."));
      assertTrue(outContent.toString().contains("Product added successfully."));
    }
  }