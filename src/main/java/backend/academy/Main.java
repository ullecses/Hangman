package backend.academy;

import backend.academy.hangman.ConsoleGameInputOutput;
import backend.academy.hangman.GameInputOutput;
import backend.academy.hangman.HangmanGame;
import backend.academy.hangman.WordRepository;
import lombok.experimental.UtilityClass;
import java.io.IOException;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        GameInputOutput io = new ConsoleGameInputOutput(System.in, System.out);
        WordRepository wordRepository;
        try {
            wordRepository = new WordRepository(); // Обработка возможных исключений
        } catch (IOException e) {
            System.out.println("Error initializing WordRepository: " + e.getMessage());
            return; // Завершить выполнение программы, если не удалось создать WordRepository
        }
        HangmanGame game = new HangmanGame(io, wordRepository);
        game.start();
    }
}
