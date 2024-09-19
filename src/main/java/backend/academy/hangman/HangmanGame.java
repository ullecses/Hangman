package backend.academy.hangman;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

public class HangmanGame {
    public static final int MAX_ATTEMPTS = 6;
    private final InputStream input;
    private final PrintStream output;
    private final WordRepository wordRepository;
    private final HangmanDisplay hangman;
    private boolean hintUsed = false;

    public HangmanGame(InputStream input, PrintStream output) {
        this.input = input;
        this.output = output;
        try {
            this.wordRepository = new WordRepository();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.hangman = new HangmanDisplay(output);
    }

    public void start() {
        Scanner scanner = new Scanner(input);
        Random random = new Random();

        String selectedCategory = selectCategory(scanner, random);
        DifficultyLevel selectedDifficulty = selectDifficulty(scanner, random);

        Word word = wordRepository.getWord(selectedCategory, selectedDifficulty);
        int wrongGuesses = 0;
        output.println("У Вас есть 6 попыток! Игра началась!");

        while (wrongGuesses < MAX_ATTEMPTS && !word.isWordGuessed()) {
            hangman.displayHangman(wrongGuesses);
            output.println("Текущее состояние слова: " + word.getCurrentState());
            output.println("У Вас осталось " + (MAX_ATTEMPTS - wrongGuesses) + " неправильных попыток");

            if (hintUsed) {
                output.println("Подсказка: " + word.getHint());
                output.print("Введите букву: ");
            } else {
                output.print("Введите букву или для появления подсказки нажмите Enter: ");
            }

            String guess = scanner.nextLine().toLowerCase();

            if (guess.isEmpty()) {
                output.println("Подсказка: " + word.getHint());
                hintUsed = true;
                continue;
            } else if (guess.length() != 1 || !Character.isLetter(guess.charAt(0))) {
                output.println("Некорректный ввод! Введите одну букву.");
                continue;
            } else if (!(guess.charAt(0) >= 'a' && guess.charAt(0) <= 'z')) {
                output.println("Введите одну английскую букву.");
                continue;
            }

            char letter = guess.charAt(0);
            if (!word.guessLetter(letter)) {
                wrongGuesses++;
            }
        }

        if (word.isWordGuessed()) {
            output.println("Поздравляем! Вы отгадали слово: " + word.getWord());
        } else {
            hangman.displayHangman(wrongGuesses);
            output.println("Вы проиграли! Загаданное слово было: " + word.getWord());
        }
    }

    private String selectCategory(Scanner scanner, Random random) {
        String[] wordCategories = wordRepository.getCategories();
        output.println("Введите цифру, которая соответствует выбранной категории "
            + wordCategories[0] + "(1), "
            + wordCategories[1] + "(2) "
            + wordCategories[2] + " (3). Чтобы сделать выбор случайным нажмите Enter");

        while (true) {
            String selectedCategory = scanner.nextLine().trim();

            switch (selectedCategory) {
                case "1":
                    return wordCategories[0];
                case "2":
                    return wordCategories[1];
                case "3":
                    return wordCategories[2];
                case "":
                    return wordCategories[random.nextInt(wordCategories.length)];
                default:
                    output.println("Некорректный ввод. Пожалуйста, введите 1, 2, 3 или оставьте строку пустой.");
            }
        }
    }

    private DifficultyLevel selectDifficulty(Scanner scanner, Random random) {
        output.println("Введите цифру, которая соответствует выбранной сложности "
            + DifficultyLevel.EASY + " (1), "
            + DifficultyLevel.MEDIUM + " (2), "
            + DifficultyLevel.HARD + " (3). Чтобы сделать выбор случайным нажмите Enter");

        while (true) {
            String selectedDifficulty = scanner.nextLine().trim();
            //DifficultyLevel[] complexity = DifficultyLevel.values();

            switch (selectedDifficulty) {
                case "1":
                    return DifficultyLevel.EASY;
                case "2":
                    return DifficultyLevel.MEDIUM;
                case "3":
                    return DifficultyLevel.HARD;
                case "":
                    return DifficultyLevel.values()[random.nextInt(DifficultyLevel.values().length)];
                default:
                    output.println("Некорректный ввод. Пожалуйста, введите 1, 2, 3 или оставьте строку пустой.");
            }
        }
    }
}
