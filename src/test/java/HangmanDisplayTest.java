import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import backend.academy.hangman.HangmanDisplay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HangmanDisplayTest {
    private ByteArrayOutputStream outputStream;
    private PrintStream output;
    private HangmanDisplay hangmanDisplay;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        output = new PrintStream(outputStream);
        hangmanDisplay = new HangmanDisplay(output);
    }

    @Test
    void testDisplayHangman_0WrongGuesses() {
        hangmanDisplay.displayHangman(0);
        String expectedOutput = """
           -----
           |   |
               |
               |
               |
               |
               |
        =========""";
        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }

    @Test
    void testDisplayHangman_1WrongGuess() {
        hangmanDisplay.displayHangman(1);
        String expectedOutput = """
           -----
           |   |
           O   |
               |
               |
               |
               |
        =========""";
        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }

    @Test
    void testDisplayHangman_2WrongGuesses() {
        hangmanDisplay.displayHangman(2);
        String expectedOutput = """
           -----
           |   |
           O   |
           |   |
               |
               |
               |
        =========""";
        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }

    @Test
    void testDisplayHangman_3WrongGuesses() {
        hangmanDisplay.displayHangman(3);
        String expectedOutput = """
           -----
           |   |
           O   |
          /|   |
               |
               |
               |
        =========""";
        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }

    @Test
    void testDisplayHangman_4WrongGuesses() {
        hangmanDisplay.displayHangman(4);
        String expectedOutput = """
           -----
           |   |
           O   |
          /|\\  |
               |
               |
               |
        =========""";
        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }

    @Test
    void testDisplayHangman_5WrongGuesses() {
        hangmanDisplay.displayHangman(5);
        String expectedOutput = """
           -----
           |   |
           O   |
          /|\\  |
          /    |
               |
               |
        =========""";
        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }

    @Test
    void testDisplayHangman_6WrongGuesses() {
        hangmanDisplay.displayHangman(6);
        String expectedOutput = """
           -----
           |   |
           O   |
          /|\\  |
          / \\  |
               |
               |
        =========""";
        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }
}
