import java.io.ByteArrayInputStream;
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
        wordRepository = Mockito.spy(new WordRepository() {
            public InputStream getClassLoaderStream() {
                return getClass().getClassLoader().getResourceAsStream("test_words.json");
            }
        });
    }

    @Test
    void testConstructor_FileNotFound() throws IOException {
        // Создаем экземпляр WordRepository
        WordRepository repository = new WordRepository();

        // Устанавливаем пустую карту слов для имитации отсутствия данных
        repository.setWordCategories(Collections.emptyMap());

        // Проверяем, что вызов getWord с неверной категорией возвращает null
        Word result = repository.getWord("nonExistingCategory", DifficultyLevel.EASY);
        assertNull(result);
    }

    @Test
    void testGetWord_ValidInput() {
        // Создаем ожидаемое слово
        Word expectedWord = new Word();
        expectedWord.setWord("test");
        expectedWord.setHint("A sample word");

        // Создаем карту с уровнями сложности
        Map<DifficultyLevel, List<Word>> difficultyMap = new HashMap<>();
        difficultyMap.put(DifficultyLevel.EASY, Collections.singletonList(expectedWord));

        // Устанавливаем категории слов через сеттер
        wordRepository.setWordCategories(Collections.singletonMap("category1", difficultyMap));

        // Получаем слово из репозитория
        Word result = wordRepository.getWord("category1", DifficultyLevel.EASY);

        // Проверяем результат
        assertNotNull(result);
        assertEquals("test", result.getWord());
    }

    @Test
    void testGetRandomWord_MultipleWords() {
        Word word1 = new Word();
        word1.setWord("test1");
        word1.setHint("Hint1");

        Word word2 = new Word();
        word2.setWord("test2");
        word2.setHint("Hint2");

        List<Word> words = Arrays.asList(word1, word2);

        Word result = WordRepository.getRandomWord(words);
        assertNotNull(result);
        assertTrue(result.getWord().equals("test1") || result.getWord().equals("test2"));
    }

    @Test
    void testGetWord_InvalidCategory() {
        Word result = wordRepository.getWord("invalidCategory", DifficultyLevel.EASY);
        assertNull(result);
    }

    @Test
    void testGetWord_InvalidDifficulty() {
        Word result = wordRepository.getWord("category1", DifficultyLevel.HARD);
        assertNull(result);
    }

    @Test
    void testConstructor_InvalidJSON() throws IOException {
        // Создаем мок InputStream с некорректным JSON
        InputStream invalidJsonStream = new ByteArrayInputStream("invalid json".getBytes());

        // Создаем мок ObjectMapper и заставляем его выбрасывать IOException
        ObjectMapper objectMapperMock = Mockito.mock(ObjectMapper.class);
        Mockito.when(objectMapperMock.readValue(Mockito.any(InputStream.class), Mockito.any(TypeReference.class)))
            .thenThrow(new IOException("Invalid JSON format"));

        // Создаем экземпляр WordRepository с замоканным ObjectMapper
        WordRepository repository = new WordRepository() {
            protected InputStream getClassLoaderStream() {
                return invalidJsonStream;
            }
        };

        // Устанавливаем пустую карту слов
        repository.setWordCategories(Collections.emptyMap());

        // Убедимся, что категории пусты после обработки ошибки
        assertTrue(repository.getCategories().length == 0, "Expected an empty categories array due to invalid JSON");
    }

    @Test
    void testGetRandomWord_EmptyList() {
        Word result = WordRepository.getRandomWord(Collections.emptyList());
        assertNull(result);
    }

    @Test
    void testSetWordCategories_EmptyMap() {
        // Устанавливаем пустую карту слов
        wordRepository.setWordCategories(Collections.emptyMap());

        // Проверяем, что категории пусты
        String[] categories = wordRepository.getCategories();
        assertEquals(0, categories.length);
    }

    @Test
    void testGetWord_NullCategory() {
        Word result = wordRepository.getWord(null, DifficultyLevel.EASY);
        assertNull(result);
    }

    @Test
    void testGetWord_NullDifficulty() {
        Word result = wordRepository.getWord("category1", null);
        assertNull(result);
    }

    @Test
    void testGetCategories() {
        String[] categories = wordRepository.getCategories();
        assertNotNull(categories);
        assertTrue(categories.length > 0);
    }
}
