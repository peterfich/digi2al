package com.peterfich.digi2al.connect;

public class Board {

    private final int columnCount;
    private final int rowCount;

    private final String[][] board;

    public Board(int columnCount, int rowCount) {
        this.columnCount = columnCount;
        this.rowCount = rowCount;

        board = new String[columnCount][rowCount];
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int move(String playerColour, int columnIndex) {
        if (columnIndex < 0 || columnIndex >= columnCount) {
            throw new InvalidColumnException(columnCount - 1, columnIndex);
        }

        String[] column = board[columnIndex];

        for (int i = 0; i < column.length; i++) {
            String square = column[i];
            if (square == null) {
                column[i] = playerColour;
                return i;
            }
        }
        throw new ColumnAllReadyFullException(columnIndex);
    }

    public String valueAt(int columnIndex, int rowIndex) {
        return board[columnIndex][rowIndex];
    }
}
