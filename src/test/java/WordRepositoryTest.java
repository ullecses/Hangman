import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import backend.academy.hangman.DifficultyLevel;
import backend.academy.hangman.Word;
import backend.academy.hangman.WordRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WordRepositoryTest {
    private WordRepository wordRepository;

    @BeforeEach
    void setUp() throws IOException {
        wordRepository = Mockito.spy(new WordRepository() {});
    }

    @Test
    void testConstructor_FileNotFound() throws IOException {
        // Arrange
        WordRepository repository = new WordRepository();
        repository.setWordCategories(Collections.emptyMap());

        // Act
        Word result = repository.getWord("nonExistingCategory", DifficultyLevel.EASY);

        // Assert
        assertNull(result);
    }

    @Test
    void testGetWord_ValidInput() {
        // Arrange
        Word expectedWord = new Word();
        expectedWord.setWord("test");
        expectedWord.setHint("A sample word");

        Map<DifficultyLevel, List<Word>> difficultyMap = new HashMap<>();
        difficultyMap.put(DifficultyLevel.EASY, Collections.singletonList(expectedWord));
        wordRepository.setWordCategories(Collections.singletonMap("category1", difficultyMap));

        // Act
        Word result = wordRepository.getWord("category1", DifficultyLevel.EASY);

        // Assert
        assertNotNull(result);
        assertEquals("test", result.getWord());
    }

    @Test
    void testGetRandomWord_MultipleWords() {
        // Arrange
        Word word1 = new Word();
        word1.setWord("test1");
        word1.setHint("Hint1");

        Word word2 = new Word();
        word2.setWord("test2");
        word2.setHint("Hint2");

        List<Word> words = Arrays.asList(word1, word2);

        // Act
        Word result = WordRepository.getRandomWord(words);

        // Assert
        assertNotNull(result);
        assertTrue(result.getWord().equals("test1") || result.getWord().equals("test2"));
    }

    @Test
    void testGetWord_InvalidCategory() {
        // Arrange & Act
        Word result = wordRepository.getWord("invalidCategory", DifficultyLevel.EASY);

        // Assert
        assertNull(result);
    }

    @Test
    void testGetWord_InvalidDifficulty() {
        // Arrange & Act
        Word result = wordRepository.getWord("category1", DifficultyLevel.HARD);

        // Assert
        assertNull(result);
    }

    @Test
    void testConstructor_InvalidJSON() throws IOException {
        // Arrange
        ObjectMapper objectMapperMock = Mockito.mock(ObjectMapper.class);
        Mockito.when(objectMapperMock.readValue(Mockito.any(InputStream.class), Mockito.any(TypeReference.class)))
            .thenThrow(new IOException("Invalid JSON format"));

        WordRepository repository = new WordRepository() {};
        repository.setWordCategories(Collections.emptyMap());

        // Act
        String[] categories = repository.getCategories();

        // Assert
        assertEquals(0, categories.length, "Expected an empty categories array due to invalid JSON");
    }

    @Test
    void testGetRandomWord_EmptyList() {
        // Arrange & Act
        Word result = WordRepository.getRandomWord(Collections.emptyList());

        // Assert
        assertNull(result);
    }

    @Test
    void testSetWordCategories_EmptyMap() {
        // Arrange & Act
        wordRepository.setWordCategories(Collections.emptyMap());

        // Assert
        String[] categories = wordRepository.getCategories();
        assertEquals(0, categories.length);
    }

    @Test
    void testGetWord_NullCategory() {
        // Arrange & Act
        Word result = wordRepository.getWord(null, DifficultyLevel.EASY);

        // Assert
        assertNull(result);
    }

    @Test
    void testGetWord_NullDifficulty() {
        // Arrange & Act
        Word result = wordRepository.getWord("category1", null);

        // Assert
        assertNull(result);
    }

    @Test
    void testGetCategories() {
        // Arrange & Act
        String[] categories = wordRepository.getCategories();

        // Assert
        assertNotNull(categories);
        assertTrue(categories.length > 0);
    }
}
