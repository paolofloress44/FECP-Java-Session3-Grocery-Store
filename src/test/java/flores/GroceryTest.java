package flores;

//source: studentGradeAnalyzer file

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
class GroceryTest {
    private static final String CSV_FILE = "test-results.csv";

    @BeforeAll
    static void setupOnce() throws IOException {
        Files.deleteIfExists(Paths.get(CSV_FILE));
    }
    //Add Products Scenarios
    @Test
    void addNewProductValidQuantity() throws Exception{
        String testName = "addNewProductValidQuantity";
        String input = String.join("\n",
                "2", "Banana", "30",
                "3", "Banana",
                "6"
        ) + "\n";

        String expectedOutputFragment = "Banana added!\n" +
                "Banana is in stock: 30 pcs" +
                "Exiting System...";

        String actualOutput = runGroceryInventoryWithInput(input);

        boolean passed = actualOutput.contains("Banana added!")
                && actualOutput.contains("Banana is in stock: 30 pcs")
                && actualOutput.contains("Exiting System...");

        logToCSV(testName, input, expectedOutputFragment, actualOutput, passed ? "PASS" : "FAIL");
        assertTrue(passed);
    }
    @Test
    void addProductWithZeroQuantity() throws Exception{
        String testName = "addProductWithZeroQuantity";
        String input = String.join("\n",
                "2", "Mango", "0",
                "6"
        ) + "\n";

        String expectedOutputFragment = "Mango added!\n" +
                "Exiting System...";

        String actualOutput = runGroceryInventoryWithInput(input);

        boolean passed = actualOutput.contains("Mango added!")
                && actualOutput.contains("Exiting System...");

        logToCSV(testName, input, expectedOutputFragment, actualOutput, passed ? "PASS" : "FAIL");
        assertTrue(passed);
    }
    @Test
    void addProductThatExists() throws Exception{
        String testName = "addProductThatExists";
        String input = String.join("\n",
                "2", "Milk", "12",
                "2", "Milk", "50",
                "6"
        ) + "\n";

        String expectedOutputFragment = "Milk added!\n" +
                "Milk added\n" +
                "Exiting System...";

        String actualOutput = runGroceryInventoryWithInput(input);

        boolean passed = actualOutput.contains("Milk added!")
                && actualOutput.contains("Milk added!")
                && actualOutput.contains("Exiting System...");

        logToCSV(testName, input, expectedOutputFragment, actualOutput, passed ? "PASS" : "FAIL");
        assertTrue(passed);
    }

    //Check Product Scenarios
    @Test
    void checkForExistingProduct() throws Exception{
        String testName = "checkForExistingProduct";
        String input = String.join("\n",
                "2", "Milk", "20",
                "3", "Milk",
                "6"
        ) + "\n";

        String expectedOutputFragment = "Milk added!\n" +
                "Milk is in stock: 20 pcs" +
                "Exiting System...";

        String actualOutput = runGroceryInventoryWithInput(input);

        boolean passed = actualOutput.contains("Milk added!") &&
                actualOutput.contains("Milk is in stock: 20 pcs") &&
                actualOutput.contains("Exiting System...");

        logToCSV(testName, input, expectedOutputFragment, actualOutput, passed ? "PASS" : "FAIL");
        assertTrue(passed);
    }
    @Test
    void checkProductNonExistent() throws Exception {
        String testName = "checkProductNonExistent";
        String input = String.join("\n",
                "3",
                "Ice Cream",
                "6"
        ) + "\n";

        String expectedOutputFragment = "Ice Cream is not found in the inventory.\n" +
                "Exiting System...";

        String actualOutput = runGroceryInventoryWithInput(input);

        boolean passed = actualOutput.contains("Ice Cream is not found in the inventory.") &&
                actualOutput.contains("Exiting System...");

        logToCSV(testName, input, expectedOutputFragment, actualOutput, passed ? "PASS" : "FAIL");
        assertTrue(passed);
    }

    //Update stock scenarios
    @Test
    void updateExistingValidQuantity() throws Exception{
        String testName = "updateExistingValidQuantity";
        String input = String.join("\n",
                "2", "Bread", "15",
                "4", "Bread", "25",
                "3", "Bread",
                "6"
        ) + "\n";

        String expectedOutputFragment = "Bread added!\n" +
                "Bread stock updated to: 25 pcs" +
                "Bread is in stock: 25 pcs" +
                "Exiting System...";

        String actualOutput = runGroceryInventoryWithInput(input);

        boolean passed = actualOutput.contains("Bread added!") &&
                actualOutput.contains("Bread stock updated to: 25 pcs") &&
                actualOutput.contains("Bread is in stock: 25 pcs") &&
                actualOutput.contains("Exiting System...");

        logToCSV(testName, input, expectedOutputFragment, actualOutput, passed ? "PASS" : "FAIL");
        assertTrue(passed);
    }
    @Test
    void updateStockNonExistent() throws Exception{
        String testName = "updateStockNonExistent";
        String input = String.join("\n",
                "4",
                "Tofu",
                "6"
        ) + "\n";

        String expectedOutputFragment = "Tofu is not found in the inventory.\n" +
                "Exiting System...";

        String actualOutput = runGroceryInventoryWithInput(input);

        boolean passed = actualOutput.contains("Tofu is not found in the inventory.") &&
                actualOutput.contains("Exiting System...");

        logToCSV(testName, input, expectedOutputFragment, actualOutput, passed ? "PASS" : "FAIL");
        assertTrue(passed);
    }

    //Remove product scenarios
    @Test
    void removeProduct() throws Exception{
        String testName = "removeProduct";
        String input = String.join("\n",
                "2", "Eggs", "12",
                "5", "Eggs",
                "3", "Eggs",
                "6"
        ) + "\n";

        String expectedOutputFragment = "Eggs added!\n" +
                "Eggs removed from inventory." +
                "Eggs is not found in the inventory." +
                "Exiting System...";

        String actualOutput = runGroceryInventoryWithInput(input);

        boolean passed = actualOutput.contains("Eggs added!")
                && actualOutput.contains("Eggs removed from inventory.")
                && actualOutput.contains("Eggs is not found in the inventory.")
                && actualOutput.contains("Exiting System...");

        logToCSV(testName, input, expectedOutputFragment, actualOutput, passed ? "PASS" : "FAIL");
        assertTrue(passed, "Test Failed: Expected product to be added and checked successfully.");
    }
    @Test
    void removeProductNonExistent() throws Exception {
        String testName = "removeProductNonExistent";
        String input = String.join("\n",
                "5", "Pizza",
                "6"
        ) + "\n";

        String expectedOutputFragment = "Pizza is not found in the inventory.\n" +
                "Exiting System...";

        String actualOutput = runGroceryInventoryWithInput(input);

        boolean passed = actualOutput.contains("Pizza is not found in the inventory.") &&
                actualOutput.contains("Exiting System...");

        logToCSV(testName, input, expectedOutputFragment, actualOutput, passed ? "PASS" : "FAIL");
        assertTrue(passed, "Test Failed: Non-existent product not handled during remove.");
    }

    //spacer
    //old test cases
    /*
    @Test
    void testInvalidOptionInMenu() throws Exception {
        String testName = "testInvalidOptionInMenu";
        String input = String.join("\n",
                "7",
                "6"
        ) + "\n";

        String expectedOutputFragment = "Invalid option. Please try again.\n" +
                "Exiting System...";

        String actualOutput = runGroceryInventoryWithInput(input);

        boolean passed = actualOutput.contains("Invalid option. Please try again.") &&
                actualOutput.contains("Exiting System...");

        logToCSV(testName, input, expectedOutputFragment, actualOutput, passed ? "PASS" : "FAIL");
        assertTrue(passed, "Test Failed: Invalid menu option not handled correctly.");
    }
    @Test
    void testGetUserChoiceInvalidInput() throws Exception {
        String testName = "testGetUserChoiceInvalid";
        String input = String.join("\n",
                "BABY",
                "6"
        ) + "\n";

        String expectedOutputFragment = "Invalid input. Please enter a number from 1-6.\n" +
                "Choose option: \n" +
                "Exiting System...";

        String actualOutput = runGroceryInventoryWithInput(input);

        boolean passed = actualOutput.contains("Invalid input. Please enter a number from 1-6.") &&
                actualOutput.contains("Choose option: ") &&
                actualOutput.contains("Exiting System...");

        logToCSV(testName, input, expectedOutputFragment, actualOutput, passed ? "PASS" : "FAIL");
        assertTrue(passed, "Test Failed: getUserChoice did not handle invalid input then valid input correctly.");
    }
    @Test
    void testAddProductEmptyName() throws Exception {
        String testName = "testAddProductEmptyName";
        String input = String.join("\n",
                "2",
                "",
                "6"
        ) + "\n";

        String expectedOutputFragment = "Product name cannot be empty.\n" +
                "Exiting System...";

        String actualOutput = runGroceryInventoryWithInput(input);

        boolean passed = actualOutput.contains("Product name cannot be empty.") &&
                actualOutput.contains("Exiting System...");

        logToCSV(testName, input, expectedOutputFragment, actualOutput, passed ? "PASS" : "FAIL");
        assertTrue(passed, "Test Failed: Empty product name not handled during add.");
    }
    @Test
    void testAddProductNegativeQuantity() throws Exception {
        String testName = "testAddProductNegativeQuantity";
        String input = String.join("\n",
                "2",
                "TestProduct",
                "-5", // Invalid quantity
                "10", // Valid quantity
                "6"
        ) + "\n";

        String expectedOutputFragment = "Quantity cannot be negative. Please enter a positive number.\n" +
                "TestProduct added!\n" +
                "Exiting System...";

        String actualOutput = runGroceryInventoryWithInput(input);

        boolean passed = actualOutput.contains("Quantity cannot be negative. Please enter a positive number.") &&
                actualOutput.contains("TestProduct added!") &&
                actualOutput.contains("Exiting System...");

        logToCSV(testName, input, expectedOutputFragment, actualOutput, passed ? "PASS" : "FAIL");
        assertTrue(passed, "Test Failed: Negative quantity not handled during add.");
    }
    @Test
    void testAddProductInvalidQuantity() throws Exception {
        String testName = "testAddProductInvalidQuantity";
        String input = String.join("\n",
                "2",
                "TestProduct",
                "YES",
                "10",
                "6"
        ) + "\n";

        String expectedOutputFragment = "Invalid input. Please enter a valid number for quantity.\n" +
                "TestProduct added!\n" +
                "Exiting System...";

        String actualOutput = runGroceryInventoryWithInput(input);

        boolean passed = actualOutput.contains("Invalid input. Please enter a valid number for quantity.") &&
                actualOutput.contains("TestProduct added!") &&
                actualOutput.contains("Exiting System...");

        logToCSV(testName, input, expectedOutputFragment, actualOutput, passed ? "PASS" : "FAIL");
        assertTrue(passed, "Test Failed: Negative quantity not handled during add.");
    }
    @Test
    void testCheckProductEmptyName() throws Exception {
        String testName = "testCheckProductEmptyName";
        String input = String.join("\n",
                "3",
                "",
                "6"
        ) + "\n";

        String expectedOutputFragment = "Product name cannot be empty.\n" +
                "Exiting System...";

        String actualOutput = runGroceryInventoryWithInput(input);

        boolean passed = actualOutput.contains("Product name cannot be empty.") &&
                actualOutput.contains("Exiting System...");

        logToCSV(testName, input, expectedOutputFragment, actualOutput, passed ? "PASS" : "FAIL");
        assertTrue(passed, "Test Failed: Empty product name not handled during check.");
    }
    @Test
    void testUpdateStockEmptyName() throws Exception {
        String testName = "testUpdateStockEmptyName";
        String input = String.join("\n",
                "4",
                "",
                "6"
        ) + "\n";

        String expectedOutputFragment = "Product name cannot be empty.\n" +
                "Exiting System...";

        String actualOutput = runGroceryInventoryWithInput(input);

        boolean passed = actualOutput.contains("Product name cannot be empty.") &&
                actualOutput.contains("Exiting System...");

        logToCSV(testName, input, expectedOutputFragment, actualOutput, passed ? "PASS" : "FAIL");
        assertTrue(passed, "Test Failed: Empty product name not handled during update.");
    }
    @Test
    void testUpdateStockNegativeQuantity() throws Exception {
        String testName = "testUpdateStockNegativeQuantity";
        String input = String.join("\n",
                "2", "TestUpdate", "10", // Add product first so it exists for update
                "4",
                "TestUpdate",
                "-5", // Invalid quantity
                "20", // Valid quantity
                "6"
        ) + "\n";

        String expectedOutputFragment = "TestUpdate added!\n" + // From initial add
                "Quantity cannot be negative. Please enter a positive number.\n" +
                "TestUpdate stock updated to: 20 pcs\n" +
                "Exiting System...";

        String actualOutput = runGroceryInventoryWithInput(input);

        boolean passed = actualOutput.contains("TestUpdate added!") &&
                actualOutput.contains("Quantity cannot be negative. Please enter a positive number.") &&
                actualOutput.contains("TestUpdate stock updated to: 20 pcs") &&
                actualOutput.contains("Exiting System...");

        logToCSV(testName, input, expectedOutputFragment, actualOutput, passed ? "PASS" : "FAIL");
        assertTrue(passed, "Test Failed: Negative new quantity not handled during update.");
    }
    @Test
    void testUpdateStockInvalidQuantity() throws Exception {
        String testName = "testUpdateStockInvalidQuantity";
        String input = String.join("\n",
                "2", "TestUpdate", "11", // Add product first so it exists for update
                "4",
                "TestUpdate",
                "YES", // Invalid quantity
                "20", // Valid quantity
                "6"
        ) + "\n";

        String expectedOutputFragment = "TestUpdate added!\n" + // From initial add
                "Invalid input. Please enter a valid number for quantity.\n" +
                "TestUpdate stock updated to: 20 pcs\n" +
                "Exiting System...";

        String actualOutput = runGroceryInventoryWithInput(input);

        boolean passed = actualOutput.contains("TestUpdate added!") &&
                actualOutput.contains("Invalid input. Please enter a valid number for quantity.") &&
                actualOutput.contains("TestUpdate stock updated to: 20 pcs") &&
                actualOutput.contains("Exiting System...");

        logToCSV(testName, input, expectedOutputFragment, actualOutput, passed ? "PASS" : "FAIL");
        assertTrue(passed, "Test Failed: Negative new quantity not handled during update.");
    }
    @Test
    void testRemoveProductEmptyName() throws Exception {
        String testName = "testRemoveProductEmptyName";
        String input = String.join("\n",
                "5",
                "",
                "6"
        ) + "\n";

        String expectedOutputFragment = "Product name cannot be empty.\n" +
                "Exiting System...";

        String actualOutput = runGroceryInventoryWithInput(input);

        boolean passed = actualOutput.contains("Product name cannot be empty.") &&
                actualOutput.contains("Exiting System...");

        logToCSV(testName, input, expectedOutputFragment, actualOutput, passed ? "PASS" : "FAIL");
        assertTrue(passed, "Test Failed: Empty product name not handled during remove.");
    }
    @Test
    void testViewInventoryEmpty() throws Exception {
        String testName = "testViewInventoryEmpty";
        String input = String.join("\n",
                "1",
                "6"
        ) + "\n";

        String expectedOutputFragment = "Inventory is empty. Add some products first!\n" +
                "Exiting System...";

        String actualOutput = runGroceryInventoryWithInput(input);

        boolean passed = actualOutput.contains("Inventory is empty. Add some products first!") &&
                actualOutput.contains("Exiting System...");

        logToCSV(testName, input, expectedOutputFragment, actualOutput, passed ? "PASS" : "FAIL");
        assertTrue(passed, "Test Failed: Empty inventory view not correct.");
    }
    @Test
    void runAllFunctionsWithMultipleProducts() throws Exception{
        String testName = "runAllFunctionsWithMultipleProducts";
        String input = String.join("\n",
                "2", "Beer", "54", //Add product 1
                "2", "Cigarettes", "500", //Add product 2
                "2", "Lemon", "24", //Add product 3
                "1",    //check inventory
                "3", "Cigarettes", //check cigarettes
                "4", "Lemon", "59", //update lemon stock to 59
                "3", "Lemon",   //check lemon after updating it
                "5", "Beer",    //remove beer :(
                "1",        //check inventory after all changes
                "6"        // Exit
        ) + "\n";

        String expectedOutputFragment = "Beer added!\n" +
                "Cigarettes added!\n" +
                "Lemon added!\n" +
                "--- Current Inventory ---\n" +
                "Cigarettes - 500 pcs\n" +
                "Lemon - 24 pcs\n" +
                "Beer - 54 pcs\n" +
                "Cigarettes is in stock: 500 pcs\n" +
                "Lemon stock updated to: 59 pcs\n" +
                "Lemon is in stock: 59 pcs\n" +
                "Beer removed from inventory.\n" +
                "--- Current Inventory ---\n" +
                "Cigarettes - 500 pcs\n" +
                "Lemon - 59 pcs\n" +
                "Exiting System...";

        String actualOutput = runGroceryInventoryWithInput(input);

        boolean passed = actualOutput.contains("Cigarettes added!")
                && actualOutput.contains("Lemon added!")
                && actualOutput.contains("--- Current Inventory ---")
                && actualOutput.contains("Cigarettes - 500 pcs")
                && actualOutput.contains("Lemon - 24 pcs")
                && actualOutput.contains("Beer - 54 pcs")
                && actualOutput.contains("Cigarettes is in stock: 500 pcs")
                && actualOutput.contains("Lemon stock updated to: 59 pcs")
                && actualOutput.contains("Lemon is in stock: 59 pcs")
                && actualOutput.contains("Beer removed from inventory.")
                && actualOutput.contains("--- Current Inventory ---")
                && actualOutput.contains("Cigarettes - 500 pcs")
                && actualOutput.contains("Lemon - 59 pcs")
                && actualOutput.contains("Exiting System...");

        logToCSV(testName, input, expectedOutputFragment, actualOutput, passed ? "PASS" : "FAIL");
        assertTrue(passed, "Test Failed: Expected product to be added and checked successfully.");
    }
    */

    private String runGroceryInventoryWithInput(String simulatedInput) throws Exception {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        ByteArrayInputStream testIn = new ByteArrayInputStream(simulatedInput.getBytes());
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();

        System.setIn(testIn);
        System.setOut(new PrintStream(testOut));

        Grocery tempGroceryInventory = new Grocery(); // Create new instance for each test
        try {
            tempGroceryInventory.run(); // Call the run method of GroceryInventory
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
            // It's good practice to close the scanner within GroceryInventory's run method,
            // which it already does when '6' is chosen.
        }

        // Normalize line endings for consistent comparison across OS
        return testOut.toString().replaceAll("\\r\\n", "\n").trim();
    }

    private void logToCSV(String testName, String input, String expected, String actual,
                          String status) throws IOException {
        File file = new File(CSV_FILE);
        // The file might be deleted by @BeforeAll, so we need to check again if it needs a header.
        // Also, use 'true' for append mode with FileWriter
        boolean isNewOrJustCreated = !file.exists(); // Check if file exists *before* writing

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            if (isNewOrJustCreated) { // Only write header if file was just created (or didn't exist)
                writer.println("Test Name,Input,Expected Output,Actual Output,Status,Timestamp");
            }
            String time = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.printf("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"%n",
                    testName,
                    input.replace("\n", "\\n").replace("\"", "\"\""),
                    expected.replace("\n", "\\n").replace("\"", "\"\""),
                    actual.replace("\n", "\\n").replace("\"", "\"\""),
                    status,
                    time);
        }
    }
}