package com.peterfich.digi2al.connect.validators.move;

import com.peterfich.digi2al.connect.Game;
import com.peterfich.digi2al.connect.GameState;
import com.peterfich.digi2al.connect.IllegalMoveException;

public class TurnValidator implements MoveValidator {

    @Override
    public void validate(Game game, String playerColour) {
        if (game.getState() == GameState.player1ToMove && !game.getPlayer1Colour().equals(playerColour)) {
            throw new IllegalMoveException(playerColour, game.getPlayer1Colour());
        }
        if (game.getState() == GameState.player2ToMove && !game.getPlayer2Colour().equals(playerColour)) {
            throw new IllegalMoveException(playerColour, game.getPlayer2Colour());
        }
    }
}
