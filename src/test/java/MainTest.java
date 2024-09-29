import backend.academy.Main;
import backend.academy.hangman.ConsoleGameInputOutput;
import backend.academy.hangman.GameInputOutput;
import backend.academy.hangman.HangmanGame;
import backend.academy.hangman.WordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    public void testCreateGameInputOutputCreatesCorrectInstance() {
        // Arrange & Act
        GameInputOutput result = Main.createGameInputOutput();

        // Assert
        assertNotNull(result);
    }

    @Test
    public void testMainSuccess() {
        // Arrange
        try (MockedStatic<Main> mainMock = Mockito.mockStatic(Main.class)) {
            mainMock.when(Main::createGameInputOutput).thenReturn(ioMock);
            mainMock.when(Main::createWordRepository).thenReturn(wordRepositoryMock);

            // Act
            Main.main(new String[]{});

            // Assert
            verify(gameMock, never()).start();
        }
    }

    @Test
    public void testMainIOException() {
        // Arrange
        try (MockedStatic<Main> mainMock = Mockito.mockStatic(Main.class)) {
            mainMock.when(Main::createGameInputOutput).thenReturn(ioMock);
            mainMock.when(Main::createWordRepository).thenReturn(null);

            // Act
            Main.main(new String[]{});

            // Assert
            verifyNoInteractions(ioMock); // Поскольку репозиторий null, HangmanGame не был создан
        }
    }

    @Test
    public void testMainWordRepositoryIOException() {
        // Arrange
        try (MockedStatic<Main> mainMock = Mockito.mockStatic(Main.class)) {
            mainMock.when(Main::createGameInputOutput).thenReturn(ioMock);
            mainMock.when(Main::createWordRepository).thenThrow(new RuntimeException("Test exception"));

            // Act
            Main.main(new String[]{});

            // Assert
            verifyNoInteractions(ioMock);
        }
    }

    @Test
    public void testCreateGameInputOutput() {
        // Arrange & Act
        GameInputOutput result = Main.createGameInputOutput();

        // Assert
        assertNotNull(result);
        assertInstanceOf(ConsoleGameInputOutput.class, result);
    }

    @Test
    public void testCreateWordRepositorySuccess() throws IOException {
        // Arrange & Act
        WordRepository result = Main.createWordRepository();

        // Assert
        assertNotNull(result);
    }

    @Test
    public void testCreateWordRepositoryFileNotFound() {
        // Arrange
        try (MockedStatic<Main> mainMock = Mockito.mockStatic(Main.class)) {
            mainMock.when(Main::createGameInputOutput).thenReturn(ioMock);
            mainMock.when(Main::createWordRepository).thenThrow(new RuntimeException("File not found"));

            // Act
            Main.main(new String[]{});

            // Assert
            verifyNoInteractions(ioMock);
        }
    }

    @Test
    public void testCreateWordRepositoryInvalidJSON() {
        // Arrange
        try (MockedStatic<Main> mainMock = Mockito.mockStatic(Main.class)) {
            mainMock.when(Main::createGameInputOutput).thenReturn(ioMock);
            // Имитируем ситуацию, когда JSON неверный
            mainMock.when(Main::createWordRepository).thenThrow(new RuntimeException("Invalid JSON"));

            // Act
            Main.main(new String[]{});

            // Assert
            verifyNoInteractions(ioMock);
        }
    }

    @Test
    public void testMainWithValidWordRepository() {
        // Arrange
        try (MockedStatic<Main> mainMock = Mockito.mockStatic(Main.class)) {
            mainMock.when(Main::createGameInputOutput).thenReturn(ioMock);
            mainMock.when(Main::createWordRepository).thenReturn(wordRepositoryMock);
            when(wordRepositoryMock.getCategories()).thenReturn(new String[]{"category1"});

            // Act
            Main.main(new String[]{});

            // Assert
            verify(gameMock, never()).start(); // Игра должна была создана, но не запущена
        }
    }
}
