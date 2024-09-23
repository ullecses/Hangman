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
        word = new Word();
        word.setWord("hello");
        word.setHint("A greeting");
    }

    @Test
    void testGetWord() {
        assertEquals("hello", word.getWord());
    }

    @Test
    void testGetHint() {
        assertEquals("A greeting", word.getHint());
    }

    @Test
    void testGuessLetter_CorrectGuess() {
        assertTrue(word.guessLetter('h'));
        assertEquals("h____", word.getCurrentState());
    }

    @Test
    void testGuessLetter_IncorrectGuess() {
        assertFalse(word.guessLetter('x'));
        assertEquals("_____", word.getCurrentState());
    }

    @Test
    void testIsWordGuessed_False() {
        word.guessLetter('h');
        assertFalse(word.isWordGuessed());
    }

    @Test
    void testIsWordGuessed_True() {
        word.guessLetter('h');
        word.guessLetter('e');
        word.guessLetter('l');
        word.guessLetter('o');
        assertTrue(word.isWordGuessed());
    }

    @Test
    void testGetCurrentState() {
        word.guessLetter('e');
        assertEquals("_e___", word.getCurrentState());
    }
}
