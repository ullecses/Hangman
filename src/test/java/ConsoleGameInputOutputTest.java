import java.io.*;

import backend.academy.hangman.ConsoleGameInputOutput;
import backend.academy.hangman.DifficultyLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConsoleGameInputOutputTest {

    private ByteArrayOutputStream outputStream;
    private PrintStream output;
    private InputStream input;
    private ConsoleGameInputOutput consoleGameInputOutput;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        output = new PrintStream(outputStream);
        input = new ByteArrayInputStream("test input\n".getBytes());
        consoleGameInputOutput = new ConsoleGameInputOutput(input, output);
    }

    @Test
    void testDisplayMessage() {
        // Arrange
        String message = "Hello, World!";

        // Act
        consoleGameInputOutput.displayMessage(message);

        // Assert
        assertEquals(message + System.lineSeparator(), outputStream.toString());
    }


    @Test
    void testGetUserInput() {
        // Arrange
        input = new ByteArrayInputStream("test input\n".getBytes());
        consoleGameInputOutput = new ConsoleGameInputOutput(input, output);

        // Act
        String result = consoleGameInputOutput.getUserInput();

        // Assert
        assertEquals("test input", result);
    }

    @Test
    void testSelectCategory_ValidInput() {
        // Arrange
        String[] categories = {"Animals", "Fruits", "Vehicles"};
        input = new ByteArrayInputStream("1\n".getBytes());
        consoleGameInputOutput = new ConsoleGameInputOutput(input, output);

        // Act
        String selectedCategory = consoleGameInputOutput.selectCategory(categories);

        // Assert
        assertEquals("Animals", selectedCategory);
    }

    @Test
    void testSelectCategory_InvalidInput() {
        // Arrange
        String[] categories = {"Animals", "Fruits", "Vehicles"};
        input = new ByteArrayInputStream("4\n1\n".getBytes());
        consoleGameInputOutput = new ConsoleGameInputOutput(input, output);

        // Act
        String selectedCategory = consoleGameInputOutput.selectCategory(categories);

        // Assert
        assertEquals("Animals", selectedCategory);
        String outputString = outputStream.toString();
        assertTrue(outputString.contains("Incorrect input."));
    }

    @Test
    void testSelectDifficulty_ValidInput() {
        // Arrange
        input = new ByteArrayInputStream("1\n".getBytes());
        consoleGameInputOutput = new ConsoleGameInputOutput(input, output);

        // Act
        DifficultyLevel result = consoleGameInputOutput.selectDifficulty();

        // Assert
        assertEquals(DifficultyLevel.EASY, result);
    }

    @Test
    void testSelectDifficulty_InvalidInput() {
        // Arrange
        input = new ByteArrayInputStream("4\n2\n".getBytes());
        consoleGameInputOutput = new ConsoleGameInputOutput(input, output);

        // Act
        DifficultyLevel result = consoleGameInputOutput.selectDifficulty();

        // Assert
        assertEquals(DifficultyLevel.MEDIUM, result);
        String outputString = outputStream.toString();
        assertTrue(outputString.contains("Incorrect input."));
    }
}
