package backend.academy.hangman;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WordRepository {
    private Map<String, Map<DifficultyLevel, List<Word>>> wordCategories;
    String[] categoryOfWord;

    public WordRepository() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("words.json")) {
            if (inputStream == null) {
                throw new IOException("File words.json not found in classpath");
            }
            wordCategories = objectMapper.readValue(
                inputStream,
                new TypeReference<>() {
                }
            );
        } catch (IOException e) {
            System.err.println("Input/output error: " + e.getMessage());
            wordCategories = Collections.emptyMap();
        }
        categoryOfWord = wordCategories.keySet().toArray(new String[0]);
    }

    public void setWordCategories(Map<String, Map<DifficultyLevel, List<Word>>> wordCategories) {
        this.wordCategories = wordCategories;
        this.categoryOfWord = wordCategories.keySet().toArray(new String[0]);
    }

    public Word getWord(String category, DifficultyLevel difficulty) {
        return getRandomWord(wordCategories.getOrDefault(category, Collections.emptyMap())
            .getOrDefault(difficulty, Collections.emptyList()));
    }

    public static Word getRandomWord(List<Word> words) {
        if (words == null || words.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int index = random.nextInt(words.size()); // Генерируем случайный индекс
        return words.get(index); // Возвращаем слово по случайному индексу
    }

    public String[] getCategories() {
        if (wordCategories == null || wordCategories.isEmpty()) {
            return new String[0];  // Возвращаем пустой массив, если нет категорий
        }
        return categoryOfWord;
    }
}
