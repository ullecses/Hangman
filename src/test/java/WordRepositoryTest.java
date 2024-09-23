import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import backend.academy.hangman.DifficultyLevel;
import backend.academy.hangman.Word;
import backend.academy.hangman.WordRepository;
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
    void testGetRandomWord_EmptyList() {
        Word result = WordRepository.getRandomWord(Collections.emptyList());
        assertNull(result);
    }

    @Test
    void testGetCategories() {
        String[] categories = wordRepository.getCategories();
        assertNotNull(categories);
        assertTrue(categories.length > 0);
    }
}
