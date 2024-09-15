package backend.academy.hangman;

public class Word {
    private final String word;
    private final String hint;
    private StringBuilder currentState;

    public Word(String word, String hint) {
        this.word = word;
        this.hint = hint;
        this.currentState = new StringBuilder("_".repeat(word.length()));
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
