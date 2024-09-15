package backend.academy;

import backend.academy.hangman.HangmanGame;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        HangmanGame game = new HangmanGame(System.in, System.out);
        game.start();
    }
}
