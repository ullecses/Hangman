import backend.academy.hangman.DifficultyLevel;
import backend.academy.hangman.GameInputOutput;
import backend.academy.hangman.HangmanGame;
import backend.academy.hangman.Word;
import backend.academy.hangman.WordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class HangmanGameTest {
    private GameInputOutput io;
    private WordRepository wordRepository;
    private HangmanGame hangmanGame;

    @BeforeEach
    void setUp() {
        io = mock(GameInputOutput.class);
        wordRepository = mock(WordRepository.class);
        hangmanGame = new HangmanGame(io, wordRepository) {};
    }

    @Test
    void testStartGame_SuccessfulGuess() {
        // Arrange
        Word word = new Word();
        word.setWord("test");
        word.setHint("A sample word");

        String[] categories = {"category1", "category2"};

        when(wordRepository.getCategories()).thenReturn(categories);
        when(io.selectCategory(any())).thenReturn("category1");
        when(io.selectDifficulty()).thenReturn(DifficultyLevel.EASY);
        when(wordRepository.getWord("category1", DifficultyLevel.EASY)).thenReturn(word);

        when(io.getUserInput())
            .thenReturn("T")  // Первый ввод
            .thenReturn("e")  // Второй ввод
            .thenReturn("s")  // Третий ввод
            .thenReturn("t"); // Четвертый ввод

        // Act
        hangmanGame.start();

        // Assert
        verify(io).displayMessage("You have 6 attempts! The game has begun!");
        verify(io).displayMessage("Congratulations! You guessed the word: test");
    }

    @Test
    void testStartGame_Loss() {
        // Arrange
        Word word = new Word();
        word.setWord("test");
        word.setHint("A sample word");

        String[] categories = {"category1", "category2"};

        when(wordRepository.getCategories()).thenReturn(categories);
        when(io.selectCategory(any())).thenReturn("category1");
        when(io.selectDifficulty()).thenReturn(DifficultyLevel.EASY);
        when(wordRepository.getWord("category1", DifficultyLevel.EASY)).thenReturn(word);

        when(io.getUserInput())
            .thenReturn("x")  // Неправильный ввод
            .thenReturn("y")  // Неправильный ввод
            .thenReturn("z")  // Неправильный ввод
            .thenReturn("q")  // Неправильный ввод
            .thenReturn("w")  // Неправильный ввод
            .thenReturn("r");  // Неправильный ввод

        // Act
        hangmanGame.start();

        // Assert
        verify(io).displayMessage("You have 6 attempts! The game has begun!");
        verify(io).displayMessage("You lost! The word you were trying to guess was: test");
    }

    @Test
    void testStartGame_UseHint() {
        // Arrange
        Word word = new Word();
        word.setWord("test");
        word.setHint("A sample word");

        String[] categories = {"category1", "category2"};

        when(wordRepository.getCategories()).thenReturn(categories);
        when(io.selectCategory(any())).thenReturn("category1");
        when(io.selectDifficulty()).thenReturn(DifficultyLevel.EASY);
        when(wordRepository.getWord("category1", DifficultyLevel.EASY)).thenReturn(word);

        when(io.getUserInput())
            .thenReturn("")   // Ввод пустой строки для подсказки
            .thenReturn("t")  // Правильный ввод
            .thenReturn("e")  // Правильный ввод
            .thenReturn("s")  // Правильный ввод
            .thenReturn("t"); // Правильный ввод

        // Act
        hangmanGame.start();

        // Assert
        verify(io).displayMessage("Hint: A sample word");
        verify(io).displayMessage("Congratulations! You guessed the word: test");
    }

    @Test
    void testStartGame_InvalidInput() {
        // Arrange
        Word word = new Word();
        word.setWord("test");
        word.setHint("A sample word");

        String[] categories = {"category1", "category2"};

        when(wordRepository.getCategories()).thenReturn(categories);
        when(io.selectCategory(any())).thenReturn("category1");
        when(io.selectDifficulty()).thenReturn(DifficultyLevel.EASY);
        when(wordRepository.getWord("category1", DifficultyLevel.EASY)).thenReturn(word);

        when(io.getUserInput())
            .thenReturn("1")  // Некорректный ввод
            .thenReturn("%")  // Некорректный ввод
            .thenReturn("t")  // Некорректный ввод
            .thenReturn("e")  // Некорректный ввод
            .thenReturn("s")  // Некорректный ввод
            .thenReturn("t"); // Правильный ввод

        // Act
        hangmanGame.start();

        // Assert
        verify(io, times(2)).displayMessage("Incorrect input! Enter one letter.");
    }
}
