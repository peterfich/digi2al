package com.peterfich.digi2al.connect;

import com.peterfich.digi2al.connect.id.IdGenerator;
import com.peterfich.digi2al.connect.repository.GameRepository;
import com.peterfich.digi2al.connect.stateupdaters.StateUpdater;
import com.peterfich.digi2al.connect.validators.move.MoveValidator;
import com.peterfich.digi2al.connect.validators.state.StateValidator;

import java.util.List;

public class ConnectService {

    private final IdGenerator idGenerator;
    private final GameRepository repository;
    private final List<StateValidator> stateValidators;
    private final List<MoveValidator> moveValidators;
    private final List<StateUpdater> stateUpdaters;
    private final int columnCount;
    private final int rowCount;

    public ConnectService(
            IdGenerator idGenerator,
            GameRepository repository,
            List<StateValidator> stateValidators,
            List<MoveValidator> moveValidators,
            List<StateUpdater> stateUpdaters,
            int columnCount,
            int rowCount) {

        this.idGenerator = idGenerator;
        this.repository = repository;
        this.stateValidators = stateValidators;
        this.moveValidators = moveValidators;
        this.stateUpdaters = stateUpdaters;
        this.columnCount = columnCount;
        this.rowCount = rowCount;
    }

    public Game startGame(String player1Colour, String player2Colour) {
        Game game = new Game();
        game.setId(idGenerator.nextId());
        game.setPlayer1Colour(player1Colour);
        game.setPlayer2Colour(player2Colour);
        game.setState(GameState.player1ToMove);
        game.setBoard(new Board(columnCount, rowCount));
        repository.saveGame(game);

        return game;
    }

    public Game getGame(int gameId) {
        return repository.retrieveGame(gameId);
    }

    public Game makeMove(int gameId, String playerColour, int columnIndex) {
        Game game = repository.retrieveGame(gameId);

        for (StateValidator validator : stateValidators) {
            validator.validate(game);
        }

        for (MoveValidator validator : moveValidators) {
            validator.validate(game, playerColour);
        }

        int rowIndex = game.getBoard().move(playerColour, columnIndex);

        for (StateUpdater stateUpdater : stateUpdaters) {
            stateUpdater.update(game, columnIndex, rowIndex);
        }

        repository.saveGame(game);
        return game;
    }
}
