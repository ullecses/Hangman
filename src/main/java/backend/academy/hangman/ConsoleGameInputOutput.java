package backend.academy.hangman;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class ConsoleGameInputOutput implements GameInputOutput {
    private final PrintStream output;
    private final Scanner scanner;
    private final Random random = new Random();
    private final HangmanDisplay hangman;

    public ConsoleGameInputOutput(InputStream input, PrintStream output) {
        this.output = output;
        this.scanner = new Scanner(input);
        this.hangman = new HangmanDisplay(output);
    }

    @Override
    public void displayMessage(String message) {
        output.println(message);
    }

    @Override
    public void displayHangman(int wrongGuesses) {
        hangman.displayHangman(wrongGuesses);
    }

    @Override
    public String getUserInput() {
        return scanner.nextLine().trim().toLowerCase();
    }

    @Override
    public String selectCategory(String[] categories) {
        displayMessage("Select category: ");
        Map<String, String> categoryMap = new HashMap<>();
        for (int i = 0; i < categories.length; i++) {
            categoryMap.put(String.valueOf(i + 1), categories[i]);
            displayMessage((i + 1) + ": " + categories[i]);
        }
        categoryMap.put("", categories[random.nextInt(categories.length)]); // случайный выбор

        String userInput = getUserInput();
        String selectedCategory = categoryMap.get(userInput);

        if (selectedCategory != null) {
            return selectedCategory;
        } else {
            displayMessage("Incorrect input. Please enter ");
            for (int i = 0; i < categories.length; i++) {
                displayMessage((i + 1) + ": " + categories[i]);
            }
            displayMessage(" or leave the line blank.");
            return selectCategory(categories); // повторный вызов для исправления ввода
        }
    }

    @Override
    public DifficultyLevel selectDifficulty() {
        displayMessage("Select difficulty level (1 - EASY, 2 - MEDIUM, 3 - HARD): ");
        while (true) {
            String choice = getUserInput();
            switch (choice) {
                case "1":
                    return DifficultyLevel.EASY;
                case "2":
                    return DifficultyLevel.MEDIUM;
                case "3":
                    return DifficultyLevel.HARD;
                case "":
                    return DifficultyLevel.values()[random.nextInt(DifficultyLevel.values().length)];
                default:
                    displayMessage("Incorrect input. Please, enter 1, 2, 3 or leave the line blank.");
            }
        }
    }
}
