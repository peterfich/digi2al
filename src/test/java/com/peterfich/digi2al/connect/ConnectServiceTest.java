package com.peterfich.digi2al.connect;

import com.peterfich.digi2al.connect.id.IdGenerator;
import com.peterfich.digi2al.connect.repository.GameRepository;
import com.peterfich.digi2al.connect.stateupdaters.HorizontalWin;
import com.peterfich.digi2al.connect.stateupdaters.StateUpdater;
import com.peterfich.digi2al.connect.stateupdaters.TurnUpdater;
import com.peterfich.digi2al.connect.validators.move.ColourValidator;
import com.peterfich.digi2al.connect.validators.move.MoveValidator;
import com.peterfich.digi2al.connect.validators.move.TurnValidator;
import com.peterfich.digi2al.connect.validators.state.GameAllReadyADraw;
import com.peterfich.digi2al.connect.validators.state.GameAllReadyWon;
import com.peterfich.digi2al.connect.validators.state.StateValidator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ConnectServiceTest {

    private int columnCount = 7;
    private int rowCount = 6;

    private IdGenerator idGenerator = mock(IdGenerator.class);
    private GameRepository repository = mock(GameRepository.class);
    private List<StateValidator> stateValidators = new ArrayList<>();
    private List<MoveValidator> moveValidators = new ArrayList<>();
    private List<StateUpdater> stateUpdaters = new ArrayList<>();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void whenStartingAGameThenPlayer1IsNext() {
        ConnectService service = new ConnectService(idGenerator, repository, stateValidators, moveValidators, stateUpdaters, columnCount, rowCount);

        Game game = service.startGame("Red", "Blue");

        assertThat(game.getState()).isEqualTo(GameState.player1ToMove);
    }

    @Test
    public void whenStartingAGameItHasAUniqueId() {
        int nextId = 1;
        when(idGenerator.nextId()).thenReturn(nextId);
        ConnectService service = new ConnectService(idGenerator, repository, stateValidators, moveValidators, stateUpdaters, columnCount, rowCount);

        Game game = service.startGame("Red", "Blue");

        assertThat(game.getId()).isEqualTo(nextId);
    }

    @Test
    public void whenStartingAGameItIsSavedInTheRepository() {
        ConnectService service = new ConnectService(idGenerator, repository, stateValidators, moveValidators, stateUpdaters, columnCount, rowCount);

        Game game = service.startGame("Red", "Blue");

        verify(repository, times(1)).saveGame(game);
    }

    @Test
    public void shouldRememberThePlayersColours() {
        ConnectService service = new ConnectService(idGenerator, repository, stateValidators, moveValidators, stateUpdaters, columnCount, rowCount);

        Game game = service.startGame("Red", "Blue");

        assertThat(game.getPlayer1Colour()).isEqualTo("Red");
        assertThat(game.getPlayer2Colour()).isEqualTo("Blue");
    }

    @Test
    public void shouldGetAnExistingGameFromTheRepository() {
        int gameId = 1;
        Game storedGame = new Game();
        storedGame.setId(gameId);

        when(repository.retrieveGame(gameId)).thenReturn(storedGame);

        ConnectService service = new ConnectService(idGenerator, repository, stateValidators, moveValidators, stateUpdaters, columnCount, rowCount);

        Game retrievedGame = service.getGame(gameId);

        assertThat(retrievedGame).isSameAs(storedGame);
    }

    @Test
    public void shouldGetGameMakeMoveOnGameAndSaveGame() {
        int gameId = 1;
        Game storedGame = mock(Game.class);
        when(storedGame.getId()).thenReturn(gameId);
        Board board = mock(Board.class);
        when(storedGame.getBoard()).thenReturn(board);

        when(repository.retrieveGame(gameId)).thenReturn(storedGame);

        ConnectService service = new ConnectService(idGenerator, repository, stateValidators, moveValidators, stateUpdaters, columnCount, rowCount);

        Game retrievedGame = service.makeMove(gameId, "Red", 0);

        verify(repository).retrieveGame(gameId);
        verify(board).move("Red", 0);
        verify(repository).saveGame(retrievedGame);

        assertThat(retrievedGame).isSameAs(storedGame);
    }

    @Test
    public void throwGameAllReadyEndedExceptionIfGameIsAllReadyADraw() {
        expectedException.expect(GameAllReadyEndedException.class);
        expectedException.expectMessage("Game has all ready ended, it was a draw.");

        int gameId = 1;
        Game game = new Game();
        game.setState(GameState.draw);
        game.setPlayer1Colour("Red");
        game.setPlayer2Colour("Blue");

        when(repository.retrieveGame(gameId)).thenReturn(game);

        stateValidators.add(new GameAllReadyADraw());

        ConnectService service = new ConnectService(idGenerator, repository, stateValidators, moveValidators, stateUpdaters, columnCount, rowCount);

        service.makeMove(gameId, "Blue", 1);
    }

    @Test
    public void throwGameAllReadyEndedExceptionIfPlayer1HasAllReadyWon() {
        expectedException.expect(GameAllReadyEndedException.class);
        expectedException.expectMessage("Game has all ready ended, Red won.");

        int gameId = 1;
        Game game = new Game();
        game.setState(GameState.player1Won);
        game.setPlayer1Colour("Red");
        game.setPlayer2Colour("Blue");

        when(repository.retrieveGame(gameId)).thenReturn(game);

        stateValidators.add(new GameAllReadyWon());

        ConnectService service = new ConnectService(idGenerator, repository, stateValidators, moveValidators, stateUpdaters, columnCount, rowCount);

        service.makeMove(gameId, "Blue", 1);
    }

    @Test
    public void throwGameAllReadyEndedExceptionIfPlayer2HasAllReadyWon() {
        expectedException.expect(GameAllReadyEndedException.class);
        expectedException.expectMessage("Game has all ready ended, Blue won.");

        int gameId = 1;
        Game game = new Game();
        game.setState(GameState.player2Won);
        game.setPlayer1Colour("Red");
        game.setPlayer2Colour("Blue");

        when(repository.retrieveGame(gameId)).thenReturn(game);

        stateValidators.add(new GameAllReadyWon());

        ConnectService service = new ConnectService(idGenerator, repository, stateValidators, moveValidators, stateUpdaters, columnCount, rowCount);

        service.makeMove(gameId, "Blue", 1);
    }

    @Test
    public void throwIllegalMoveExceptionIfPlayer1MakesMoveOutOfTurn() {
        expectedException.expect(IllegalMoveException.class);
        expectedException.expectMessage("Illegal move. Expected Blue to move, was Red.");

        int gameId = 1;
        Game game = new Game();
        game.setState(GameState.player2ToMove);
        game.setPlayer1Colour("Red");
        game.setPlayer2Colour("Blue");

        when(repository.retrieveGame(gameId)).thenReturn(game);

        moveValidators.add(new TurnValidator());

        ConnectService service = new ConnectService(idGenerator, repository, stateValidators, moveValidators, stateUpdaters, columnCount, rowCount);

        service.makeMove(gameId, "Red", 1);
    }

    @Test
    public void throwIllegalMoveExceptionIfPlayer2MakesMoveOutOfTurn() {
        expectedException.expect(IllegalMoveException.class);
        expectedException.expectMessage("Illegal move. Expected Red to move, was Blue.");

        int gameId = 1;
        Game game = new Game();
        game.setState(GameState.player1ToMove);
        game.setPlayer1Colour("Red");
        game.setPlayer2Colour("Blue");

        when(repository.retrieveGame(gameId)).thenReturn(game);

        moveValidators.add(new TurnValidator());

        ConnectService service = new ConnectService(idGenerator, repository, stateValidators, moveValidators, stateUpdaters, columnCount, rowCount);

        service.makeMove(gameId, "Blue", 1);
    }

    @Test
    public void throwIllegalColourExceptionWhenMoveHasInvalidColour() {
        expectedException.expect(IllegalColourException.class);
        expectedException.expectMessage("Illegal colour. Allowed colours are Red and Blue, was Green.");

        int gameId = 1;
        Game game = new Game();
        game.setState(GameState.player1ToMove);
        game.setPlayer1Colour("Red");
        game.setPlayer2Colour("Blue");

        when(repository.retrieveGame(gameId)).thenReturn(game);

        moveValidators.add(new ColourValidator());

        ConnectService service = new ConnectService(idGenerator, repository, stateValidators, moveValidators, stateUpdaters, columnCount, rowCount);

        service.makeMove(gameId, "Green", 1);
    }

    @Test
    public void moveByPlayer1ShouldChangeStateToPlayer2ToMove() {
        Board board = mock(Board.class);

        int gameId = 1;
        Game game = new Game();
        game.setState(GameState.player1ToMove);
        game.setPlayer1Colour("Red");
        game.setPlayer2Colour("Blue");
        game.setBoard(board);

        when(repository.retrieveGame(gameId)).thenReturn(game);

        stateUpdaters.add(new TurnUpdater());

        ConnectService service = new ConnectService(idGenerator, repository, stateValidators, moveValidators, stateUpdaters, columnCount, rowCount);

        service.makeMove(gameId, "Red", 1);

        assertThat(game.getState()).isEqualTo(GameState.player2ToMove);
    }

    @Test
    public void moveByPlayer2ShouldChangeStateToPlayer1ToMove() {
        Board board = mock(Board.class);

        int gameId = 1;
        Game game = new Game();
        game.setState(GameState.player2ToMove);
        game.setPlayer1Colour("Red");
        game.setPlayer2Colour("Blue");
        game.setBoard(board);

        when(repository.retrieveGame(gameId)).thenReturn(game);

        stateUpdaters.add(new TurnUpdater());

        ConnectService service = new ConnectService(idGenerator, repository, stateValidators, moveValidators, stateUpdaters, columnCount, rowCount);

        service.makeMove(gameId, "Blue", 1);

        assertThat(game.getState()).isEqualTo(GameState.player1ToMove);
    }

    @Test
    public void player1Win() {
        Board board = new Board(columnCount, rowCount);

        int gameId = 1;
        Game game = new Game();
        game.setState(GameState.player1ToMove);
        game.setPlayer1Colour("Red");
        game.setPlayer2Colour("Blue");
        game.setBoard(board);

        when(repository.retrieveGame(gameId)).thenReturn(game);

        stateUpdaters.add(new HorizontalWin(4));

        ConnectService service = new ConnectService(idGenerator, repository, stateValidators, moveValidators, stateUpdaters, columnCount, rowCount);

        service.makeMove(gameId, "Red", 3);
        service.makeMove(gameId, "Blue", 6);
        service.makeMove(gameId, "Red", 2);
        service.makeMove(gameId, "Blue", 5);
        service.makeMove(gameId, "Red", 1);
        service.makeMove(gameId, "Blue", 4);
        service.makeMove(gameId, "Red", 0);

        assertThat(game.getState()).isEqualTo(GameState.player1Won);
    }
}
