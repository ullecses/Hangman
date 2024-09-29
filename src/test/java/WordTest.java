import backend.academy.hangman.Word;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WordTest {
    private Word word;

    @BeforeEach
    void setUp() {
        // Arrange
        word = new Word();
        word.setWord("hello");
        word.setHint("A greeting");
    }

    @Test
    void testGetWord() {
        // Act
        String result = word.getWord();

        // Assert
        assertEquals("hello", result);
    }

    @Test
    void testGetHint() {
        // Act
        String result = word.getHint();

        // Assert
        assertEquals("A greeting", result);
    }

    @Test
    void testGuessLetter_CorrectGuess() {
        // Act
        boolean correctGuess = word.guessLetter('h');

        // Assert
        assertTrue(correctGuess);
        assertEquals("h____", word.getCurrentState());
    }

    @Test
    void testGuessLetter_IncorrectGuess() {
        // Act
        boolean incorrectGuess = word.guessLetter('x');

        // Assert
        assertFalse(incorrectGuess);
        assertEquals("_____", word.getCurrentState());
    }

    @Test
    void testIsWordGuessed_False() {
        // Act
        word.guessLetter('h');
        boolean isGuessed = word.isWordGuessed();

        // Assert
        assertFalse(isGuessed);
    }

    @Test
    void testIsWordGuessed_True() {
        // Act
        word.guessLetter('h');
        word.guessLetter('e');
        word.guessLetter('l');
        word.guessLetter('o');
        boolean isGuessed = word.isWordGuessed();

        // Assert
        assertTrue(isGuessed);
    }

    @Test
    void testGetCurrentState() {
        // Act
        word.guessLetter('e');
        String currentState = word.getCurrentState();

        // Assert
        assertEquals("_e___", currentState);
    }
}
