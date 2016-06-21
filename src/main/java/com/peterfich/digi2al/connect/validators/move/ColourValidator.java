package com.peterfich.digi2al.connect.validators.move;

import com.peterfich.digi2al.connect.Game;
import com.peterfich.digi2al.connect.IllegalColourException;

public class ColourValidator implements MoveValidator {

    @Override
    public void validate(Game game, String playerColour) {
        if (!game.getPlayer1Colour().equals(playerColour) && !game.getPlayer2Colour().equals(playerColour)) {
            throw new IllegalColourException(playerColour, game.getPlayer1Colour(), game.getPlayer2Colour());
        }
    }
}
