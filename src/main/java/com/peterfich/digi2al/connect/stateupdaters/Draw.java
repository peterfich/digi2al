package com.peterfich.digi2al.connect.stateupdaters;

import com.peterfich.digi2al.connect.Board;
import com.peterfich.digi2al.connect.Game;
import com.peterfich.digi2al.connect.GameState;

public class Draw implements StateUpdater {

    @Override
    public void update(Game game, int columnIndex, int rowIndex) {
        Board board = game.getBoard();
        if (rowIndex == board.getRowCount() - 1) {
            for (int i = 0; i < board.getColumnCount(); i ++) {
                if (board.valueAt(i, rowIndex) == null) {
                    return;
                }
            }
            game.setState(GameState.draw);
        }
    }
}
