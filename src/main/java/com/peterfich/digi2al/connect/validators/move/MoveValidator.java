package com.peterfich.digi2al.connect.validators.move;

import com.peterfich.digi2al.connect.Game;

public interface MoveValidator {

    void validate(Game game, String playerColour);
}
