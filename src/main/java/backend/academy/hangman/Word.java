package backend.academy.hangman;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Word {
    public String word;
    public String hint;
    private StringBuilder currentState;

    @JsonProperty("word")
    public void setWord(String word) {
        this.word = word;
        this.currentState = new StringBuilder("_".repeat(word.length()));
    }

    @JsonProperty("hint")
    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getWord() {
        return word;
    }

    public String getHint() {
        return hint;
    }

    public boolean guessLetter(char letter) {
        boolean correct = false;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                currentState.setCharAt(i, letter);
                correct = true;
            }
        }
        return correct;
    }

    public boolean isWordGuessed() {
        return currentState.indexOf("_") == -1;
    }

    public String getCurrentState() {
        return currentState.toString();
    }
}
