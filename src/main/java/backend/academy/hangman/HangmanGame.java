package backend.academy.hangman;

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
        this.wordRepository = new WordRepository();
        this.hangman = new HangmanDisplay(output);
    }

    public void start() {
        Scanner scanner = new Scanner(input);
        Random random = new Random();

        String selectedCategory = selectCategory(scanner, random);
        String selectedDifficulty = selectDifficulty(scanner, random);

        Word word = wordRepository.getWord(selectedCategory, selectedDifficulty);
        int wrongGuesses = 0;
        output.println("У Вас есть 6 попыток! Игра началась!");

        while (wrongGuesses < MAX_ATTEMPTS && !word.isWordGuessed()) {
            hangman.displayHangman(wrongGuesses);
            output.println("Текущее состояние слова: " + word.getCurrentState());
            output.println("У Вас осталось " + (MAX_ATTEMPTS - wrongGuesses) + " неправильных попыток");

            if (hintUsed) {
                output.println("Подсказка:" + word.getHint());
            } else {
                output.println("Для появления подсказки вместо буквы нажмите Enter" + word.getHint());
            }

            output.print("Введите букву или нажмите Enter для появления подсказки: ");
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
                case "":
                    return wordCategories[random.nextInt(wordCategories.length)];
                default:
                    output.println("Некорректный ввод. Пожалуйста, введите 1, 2, 3 или оставьте строку пустой.");
            }
        }
    }

    private String selectDifficulty(Scanner scanner, Random random) {
        output.println("Введите цифру, которая соответствует выбранной сложности "
            + DifficultyLevel.EASY + " (1), "
            + DifficultyLevel.MEDIUM + " (2), "
            + DifficultyLevel.HARD + " (3). Чтобы сделать выбор случайным нажмите Enter");

        while (true) {
            String selectedDifficulty = scanner.nextLine().trim();
            DifficultyLevel[] complexity = DifficultyLevel.values();

            switch (selectedDifficulty) {
                case "1":
                    return DifficultyLevel.EASY.name();
                case "2":
                    return DifficultyLevel.MEDIUM.name();
                case "3":
                    return DifficultyLevel.HARD.name();
                case "":
                    return complexity[random.nextInt(complexity.length)].name();
                default:
                    output.println("Некорректный ввод. Пожалуйста, введите 1, 2, 3 или оставьте строку пустой.");
            }
        }
    }
}
