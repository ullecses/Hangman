package backend.academy.hangman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

public class WordRepository {
    private final Map<String, Map<String, List<Word>>> wordCategories;
    public Set<String> categories;
    String[] categoryOfWord = {"animals", "countries", "cities"};

    public WordRepository() {
        wordCategories = new HashMap<>();
        initWords();
    }

    private void initWords() {
        // Категория "Животные"
        Map<String, List<Word>> animals = new HashMap<>();
        // Категория "Страны"
        Map<String, List<Word>> countries = new HashMap<>();
        // Категория "Города"
        Map<String, List<Word>> cities = new HashMap<>();

        // Легкий уровень
        List<Word> easyAnimals = new ArrayList<>();
        easyAnimals.add(new Word("cat", "A small domestic animal that catches mice"));
        easyAnimals.add(new Word("dog", "A loyal companion, known as man's best friend"));

        //Cредний уровень
        List<Word> mediumAnimals = new ArrayList<>();
        mediumAnimals.add(new Word("eagle", "A large bird of prey known for its sharp vision "
            + "and powerful flight"));
        mediumAnimals.add(new Word("giraffe",
            "An intelligent marine mammal known for its playful behavior and communication skills"));

        // Сложный уровень
        List<Word> hardAnimals = new ArrayList<>();
        hardAnimals.add(new Word("hippopotamus", "A large herbivorous mammal that lives "
            + "near rivers in Africa"));
        hardAnimals.add(
            new Word("kangaroo", " A marsupial from Australia known for its jumping and "
                + "carrying babies in a pouch"));

        animals.put(String.valueOf(DifficultyLevel.EASY), easyAnimals);
        animals.put(String.valueOf(DifficultyLevel.MEDIUM), mediumAnimals);
        animals.put(String.valueOf(DifficultyLevel.HARD), hardAnimals);

        // Легкий уровень
        List<Word> easyCities = new ArrayList<>();
        easyCities.add(new Word("Paris", "The capital city of France, known for the Eiffel Tower"));
        easyCities.add(new Word("Tokyo", "The bustling capital of Japan, famous for its "
            + "modern technology and culture"));
        easyCities.add(new Word("Oslo", "The capital of Norway, known for its beautiful fjords"));
        easyCities.add(new Word("Milan", "A major city in Italy known for its fashion and design"));

        // Средний уровень
        List<Word> mediumCities = new ArrayList<>();
        mediumCities.add(new Word("Berlin", "The capital of Germany, known for its historical significance and vibrant culture"));
        mediumCities.add(new Word("Toronto", "The largest city in Canada, known for its diverse culture and CN Tower"));
        mediumCities.add(new Word("Vienna", "The capital of Austria, famous for its imperial history and classical music"));

        // Сложный уровень
        List<Word> hardCities = new ArrayList<>();
        hardCities.add(new Word("Copenhagen", "The capital of Denmark, known for its historic buildings and modern design"));
        hardCities.add(new Word("Budapest", "The capital of Hungary, known for its thermal baths and historic architecture"));
        hardCities.add(new Word("Philadelphia", "A major city in the United States known for its rich history and cultural landmarks"));

        cities.put(String.valueOf(DifficultyLevel.EASY), easyCities);
        cities.put(String.valueOf(DifficultyLevel.MEDIUM), mediumCities);
        cities.put(String.valueOf(DifficultyLevel.HARD), hardCities);

        // Легкий уровень
        List<Word> easyCountries = new ArrayList<>();
        easyCountries.add(new Word("france", "A country with the Eiffel Tower."));
        easyCountries.add(new Word("china", "The most populous country in the world."));

        // Средний уровень
        List<Word> mediumCountries = new ArrayList<>();
        mediumCountries.add(new Word("canada", "The second largest country by land area, located in North America."));
        mediumCountries.add(
            new Word("japan", "An island nation in East Asia, known for its technology and traditions."));
        mediumCountries.add(
            new Word("brazil", "The largest country in South America, famous for the Amazon rainforest."));
        mediumCountries.add(
            new Word("egypt", "A country in North Africa known for its ancient pyramids and the Nile River."));

        // Сложный уровень
        List<Word> hardCountries = new ArrayList<>();
        hardCountries.add(
            new Word("kazakhstan", "A country in Central Asia, one of the largest in the world by land area."));
        hardCountries.add(
            new Word("madagascar", "An island nation in the Indian Ocean, famous for its unique wildlife."));

        // Добавляем уровни сложности в категорию "Страны"
        countries.put(String.valueOf(DifficultyLevel.EASY), easyCountries);
        countries.put(String.valueOf(DifficultyLevel.MEDIUM), mediumCountries);
        countries.put(String.valueOf(DifficultyLevel.HARD), hardCountries);

        // Добавляем категории в общий список
        wordCategories.put(categoryOfWord[0], animals);
        wordCategories.put(categoryOfWord[1], countries);
        wordCategories.put(categoryOfWord[2], cities);
        categories = wordCategories.keySet();
    }

    public Word getWord(String category, String difficulty) {
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
