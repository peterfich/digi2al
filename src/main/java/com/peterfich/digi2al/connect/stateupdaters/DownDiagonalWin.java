package com.peterfich.digi2al.connect.stateupdaters;

import com.peterfich.digi2al.connect.Board;
import com.peterfich.digi2al.connect.Game;

public class DownDiagonalWin extends AbstractWin {

    public DownDiagonalWin(int connectCount) {
        super(connectCount);
    }

    @Override
    protected void update(Game game, int columnIndex, int rowIndex, int offset) {
        if (columnIndex - offset < 0) {
            return;
        }
        if (columnIndex + (connectCount - 1 - offset) > game.getBoard().getColumnCount() - 1) {
            return;
        }

        if (rowIndex - (connectCount - 1 - offset) < 0) {
            return;
        }
        if (rowIndex + offset > game.getBoard().getRowCount() - 1) {
            return;
        }

        Board board = game.getBoard();
        String lastDisc = board.valueAt(columnIndex, rowIndex);

        if (connected(lastDisc, board, columnIndex - offset, rowIndex + offset)) {
            setWinner(game, lastDisc);
        }
    }

    private boolean connected(String lastDisc, Board board, int columnIndex, int rowIndex) {
        for (int i = 0; i < connectCount; i++) {
            if (!lastDisc.equals(board.valueAt(columnIndex + i, rowIndex - i))) {
                return false;
            }
        }
        return true;
    }
}
