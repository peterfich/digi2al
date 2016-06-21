package com.peterfich.digi2al.connect.stateupdaters;

import com.peterfich.digi2al.connect.Board;
import com.peterfich.digi2al.connect.Game;

public class HorizontalWin extends AbstractWin {

    public HorizontalWin(int connectCount) {
        super(connectCount);
    }

    @Override
    protected void update(Game game, int columnIndex, int rowIndex, int offset) {
        if (columnIndex < offset) {
            return;
        }
        if (columnIndex + (connectCount - 1 - offset) > game.getBoard().getColumnCount() - 1) {
            return;
        }

        Board board = game.getBoard();
        String lastDisc = board.valueAt(columnIndex, rowIndex);

        if (connected(lastDisc, board, columnIndex - offset, rowIndex)) {
            setWinner(game, lastDisc);
        }
    }

    private boolean connected(String lastDisc, Board board, int columnIndex, int rowIndex) {
        for (int i = 0; i < connectCount; i++) {
            if (!lastDisc.equals(board.valueAt(columnIndex + i, rowIndex))) {
                return false;
            }
        }
        return true;
    }
}
