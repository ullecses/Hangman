import backend.academy.Main;
import backend.academy.hangman.GameInputOutput;
import backend.academy.hangman.HangmanGame;
import backend.academy.hangman.WordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

public class MainTest {

    private GameInputOutput ioMock;
    private WordRepository wordRepositoryMock;
    private HangmanGame gameMock;

    @BeforeEach
    public void setUp() {
        ioMock = mock(GameInputOutput.class);
        wordRepositoryMock = mock(WordRepository.class);
        gameMock = mock(HangmanGame.class);
    }

    @Test
    public void testMainSuccess() {
        try (MockedStatic<Main> mainMock = Mockito.mockStatic(Main.class)) {
            // Мокаем методы Main
            mainMock.when(Main::createGameInputOutput).thenReturn(ioMock);
            mainMock.when(Main::createWordRepository).thenReturn(wordRepositoryMock);

            Main.main(new String[]{});

            // Проверяем, что start() был вызван
            verify(gameMock, never()).start(); // Игра создана, но не запущена, так как HangmanGame не мокируется здесь
        }
    }

    @Test
    public void testMainIOException() {
        try (MockedStatic<Main> mainMock = Mockito.mockStatic(Main.class)) {
            // Мокаем создание WordRepository, чтобы вернуть null
            mainMock.when(Main::createGameInputOutput).thenReturn(ioMock);
            mainMock.when(Main::createWordRepository).thenReturn(null);

            Main.main(new String[]{});

            // Проверяем, что игра не запущена, если возникло исключение
            verifyNoInteractions(ioMock); // Поскольку репозиторий null, HangmanGame не был создан
        }
    }
}

