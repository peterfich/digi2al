package com.peterfich.digi2al.connect.stateupdaters;

import com.peterfich.digi2al.connect.Game;
import com.peterfich.digi2al.connect.GameState;

public class TurnUpdater implements StateUpdater {

    @Override
    public void update(Game game, int columnIndex, int rowIndex) {
        if (game.getState() == GameState.player1ToMove) {
            game.setState(GameState.player2ToMove);
        } else if (game.getState() == GameState.player2ToMove) {
            game.setState(GameState.player1ToMove);
        }
    }
}
