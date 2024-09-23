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
        String message = "Hello, World!";
        consoleGameInputOutput.displayMessage(message);

        assertEquals(message + System.lineSeparator(), outputStream.toString());
    }

    @Test
    void testGetUserInput() {
        // Имитация ввода пользователя
        input = new ByteArrayInputStream("test input\n".getBytes());
        consoleGameInputOutput = new ConsoleGameInputOutput(input, output);

        String result = consoleGameInputOutput.getUserInput();

        assertEquals("test input", result);
    }

    @Test
    void testSelectCategory_ValidInput() {
        String[] categories = {"Animals", "Fruits", "Vehicles"};
        input = new ByteArrayInputStream("1\n".getBytes());
        consoleGameInputOutput = new ConsoleGameInputOutput(input, output);

        String selectedCategory = consoleGameInputOutput.selectCategory(categories);

        assertEquals("Animals", selectedCategory);
    }

    @Test
    void testSelectCategory_InvalidInput() {
        String[] categories = {"Animals", "Fruits", "Vehicles"};
        input = new ByteArrayInputStream("4\n1\n".getBytes());
        consoleGameInputOutput = new ConsoleGameInputOutput(input, output);

        String selectedCategory = consoleGameInputOutput.selectCategory(categories);

        assertEquals("Animals", selectedCategory);
        String outputString = outputStream.toString();
        assertTrue(outputString.contains("Incorrect input."));
    }

    @Test
    void testSelectDifficulty_ValidInput() {
        input = new ByteArrayInputStream("1\n".getBytes());
        consoleGameInputOutput = new ConsoleGameInputOutput(input, output);

        DifficultyLevel result = consoleGameInputOutput.selectDifficulty();

        assertEquals(DifficultyLevel.EASY, result);
    }

    @Test
    void testSelectDifficulty_InvalidInput() {
        input = new ByteArrayInputStream("4\n2\n".getBytes());
        consoleGameInputOutput = new ConsoleGameInputOutput(input, output);

        DifficultyLevel result = consoleGameInputOutput.selectDifficulty();

        assertEquals(DifficultyLevel.MEDIUM, result);
        String outputString = outputStream.toString();
        assertTrue(outputString.contains("Incorrect input."));
    }
}
