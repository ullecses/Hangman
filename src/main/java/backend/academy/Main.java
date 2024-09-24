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
        GameInputOutput io = createGameInputOutput();
        WordRepository wordRepository = createWordRepository();
        if (wordRepository == null) {
            return;
        }

        HangmanGame game = new HangmanGame(io, wordRepository);
        game.start();
    }

    public GameInputOutput createGameInputOutput() {
        return new ConsoleGameInputOutput(System.in, System.out);
    }

    public WordRepository createWordRepository() {
        try {
            return new WordRepository();
        } catch (IOException e) {
            LOGGER.error("Error initializing WordRepository: {}", e.getMessage());
            return null;
        }
    }
}
