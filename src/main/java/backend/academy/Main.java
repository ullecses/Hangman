package backend.academy;

import backend.academy.hangman.ConsoleGameInputOutput;
import backend.academy.hangman.GameInputOutput;
import backend.academy.hangman.HangmanGame;
import backend.academy.hangman.WordRepository;
import java.io.IOException;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UtilityClass
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        GameInputOutput io = new ConsoleGameInputOutput(System.in, System.out);
        WordRepository wordRepository;
        try {
            wordRepository = new WordRepository(); // Обработка возможных исключений
        } catch (IOException e) {
            LOGGER.error("Error initializing WordRepository: {}", e.getMessage());
            return; // Завершить выполнение программы, если не удалось создать WordRepository
        }
        HangmanGame game = new HangmanGame(io, wordRepository);
        game.start();
    }
}
