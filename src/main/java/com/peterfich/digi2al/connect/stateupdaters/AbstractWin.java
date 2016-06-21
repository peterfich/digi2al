package com.peterfich.digi2al.connect.stateupdaters;

import com.peterfich.digi2al.connect.Game;
import com.peterfich.digi2al.connect.GameState;

public abstract class AbstractWin implements StateUpdater {

    protected final int connectCount;

    protected AbstractWin(int connectCount) {
        this.connectCount = connectCount;
    }

    @Override
    public void update(Game game, int columnIndex, int rowIndex) {
        for (int offset = 0; offset < connectCount; offset++) {
            update(game, columnIndex, rowIndex, offset);
        }
    }

    protected void setWinner(Game game, String lastDisc) {
        if (game.getPlayer1Colour().equals(lastDisc)) {
            game.setState(GameState.player1Won);
            return;
        }
        game.setState(GameState.player2Won);
    }

    protected abstract void update(Game game, int columnIndex, int rowIndex, int offset);
}
