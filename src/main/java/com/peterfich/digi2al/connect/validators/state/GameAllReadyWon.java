package com.peterfich.digi2al.connect.validators.state;

import com.peterfich.digi2al.connect.Game;
import com.peterfich.digi2al.connect.GameAllReadyEndedException;
import com.peterfich.digi2al.connect.GameState;

public class GameAllReadyWon implements StateValidator {

    public void validate(Game game) {
        if (game.getState().equals(GameState.player1Won)) {
            throw new GameAllReadyEndedException(String.format("Game has all ready ended, %s won.", game.getPlayer1Colour()));
        }
        if (game.getState().equals(GameState.player2Won)) {
            throw new GameAllReadyEndedException(String.format("Game has all ready ended, %s won.", game.getPlayer2Colour()));
        }
    }
}
