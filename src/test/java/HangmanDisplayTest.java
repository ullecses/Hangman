import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import backend.academy.hangman.HangmanDisplay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HangmanDisplayTest {
    private ByteArrayOutputStream outputStream;
    private HangmanDisplay hangmanDisplay;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);
        hangmanDisplay = new HangmanDisplay(output);
    }

    @Test
    void testDisplayHangman_0WrongGuesses() {
        // Arrange
        int wrongGuesses = 0;
        String expectedOutput = """
           -----
           |   |
               |
               |
               |
               |
               |
        =========""";

        // Act
        hangmanDisplay.displayHangman(wrongGuesses);

        // Assert
        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }

    @Test
    void testDisplayHangman_1WrongGuess() {
        // Arrange
        int wrongGuesses = 1;
        String expectedOutput = """
           -----
           |   |
           O   |
               |
               |
               |
               |
        =========""";

        // Act
        hangmanDisplay.displayHangman(wrongGuesses);

        // Assert
        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }

    @Test
    void testDisplayHangman_2WrongGuesses() {
        // Arrange
        int wrongGuesses = 2;
        String expectedOutput = """
           -----
           |   |
           O   |
           |   |
               |
               |
               |
        =========""";

        // Act
        hangmanDisplay.displayHangman(wrongGuesses);

        // Assert
        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }

    @Test
    void testDisplayHangman_3WrongGuesses() {
        // Arrange
        int wrongGuesses = 3;
        String expectedOutput = """
           -----
           |   |
           O   |
          /|   |
               |
               |
               |
        =========""";

        // Act
        hangmanDisplay.displayHangman(wrongGuesses);

        // Assert
        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }

    @Test
    void testDisplayHangman_4WrongGuesses() {
        // Arrange
        int wrongGuesses = 4;
        String expectedOutput = """
           -----
           |   |
           O   |
          /|\\  |
               |
               |
               |
        =========""";

        // Act
        hangmanDisplay.displayHangman(wrongGuesses);

        // Assert
        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }

    @Test
    void testDisplayHangman_5WrongGuesses() {
        // Arrange
        int wrongGuesses = 5;
        String expectedOutput = """
           -----
           |   |
           O   |
          /|\\  |
          /    |
               |
               |
        =========""";

        // Act
        hangmanDisplay.displayHangman(wrongGuesses);

        // Assert
        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }

    @Test
    void testDisplayHangman_6WrongGuesses() {
        // Arrange
        int wrongGuesses = 6;
        String expectedOutput = """
           -----
           |   |
           O   |
          /|\\  |
          / \\  |
               |
               |
        =========""";

        // Act
        hangmanDisplay.displayHangman(wrongGuesses);

        // Assert
        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }
}
