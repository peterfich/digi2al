package com.peterfich.digi2al.connect.stateupdaters;

import com.peterfich.digi2al.connect.Board;
import com.peterfich.digi2al.connect.Game;

public class VerticalWin extends AbstractWin {

    public VerticalWin(int connectCount) {
        super(connectCount);
    }

    @Override
    public void update(Game game, int columnIndex, int rowIndex, int offset) {
        if (rowIndex < offset) {
            return;
        }
        if (rowIndex + (connectCount - 1 - offset) > game.getBoard().getRowCount() - 1) {
            return;
        }

        Board board = game.getBoard();
        String lastDisc = board.valueAt(columnIndex, rowIndex);

        if (connected(lastDisc, board, columnIndex, rowIndex - offset)) {
            setWinner(game, lastDisc);
        }
    }

    private boolean connected(String lastDisc, Board board, int columnIndex, int rowIndex) {
        for (int i = 0; i < connectCount; i++) {
            if (!lastDisc.equals(board.valueAt(columnIndex, rowIndex + i))) {
                return false;
            }
        }
        return true;
    }
}
