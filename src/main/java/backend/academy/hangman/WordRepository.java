package backend.academy.hangman;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WordRepository {
    private Map<String, Map<DifficultyLevel, List<Word>>> wordCategories;
    String[] categoryOfWord = {"animals", "countries", "cities"};

    public WordRepository() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("words.json")) {
            if (inputStream == null) {
                throw new IOException("Файл words.json не найден в classpath");
            }
            wordCategories = objectMapper.readValue(
                inputStream,
                new TypeReference<Map<String, Map<DifficultyLevel, List<Word>>>>() {
                }
            );
        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
            e.printStackTrace();
        }
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
    return categoryOfWord;
}
}
