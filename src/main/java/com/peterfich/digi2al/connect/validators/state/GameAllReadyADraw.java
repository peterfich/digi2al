package com.peterfich.digi2al.connect.validators.state;

import com.peterfich.digi2al.connect.Game;
import com.peterfich.digi2al.connect.GameAllReadyEndedException;
import com.peterfich.digi2al.connect.GameState;

public class GameAllReadyADraw implements StateValidator {

    public void validate(Game game) {
        if (game.getState().equals(GameState.draw)) {
            throw new GameAllReadyEndedException("Game has all ready ended, it was a draw.");
        }
    }
}
