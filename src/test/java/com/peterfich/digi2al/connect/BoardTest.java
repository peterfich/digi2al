package com.peterfich.digi2al.connect;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardTest {

    private int columnCount = 7;
    private int rowCount = 6;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void canPlaceADisc() {
        Board board = new Board(columnCount, rowCount);

        int rowIndex = board.move("Red", 0);

        assertThat(rowIndex).isEqualTo(0);
    }

    @Test
    public void throwsInvalidColumnExceptionIfColumnIndexIsTooSmall() {
        expectedException.expect(InvalidColumnException.class);
        expectedException.expectMessage("Column index must be between 0 and 6, was -1.");

        Board board = new Board(columnCount, rowCount);

        board.move("Red", -1);
    }

    @Test
    public void throwsInvalidColumnExceptionIfColumnIndexIsTooLarge() {
        expectedException.expect(InvalidColumnException.class);
        expectedException.expectMessage("Column index must be between 0 and 6, was 7.");

        Board board = new Board(columnCount, rowCount);

        board.move("Red", 7);
    }

    @Test
    public void throwsColumnAllReadyFullExceptionIfColumnIsAllReadyFull() {
        expectedException.expect(ColumnAllReadyFullException.class);
        expectedException.expectMessage("Column 0 is all ready full.");

        Board board = new Board(columnCount, rowCount);

        board.move("Red", 0);
        board.move("Blue", 0);
        board.move("Red", 0);
        board.move("Blue", 0);
        board.move("Red", 0);
        board.move("Blue", 0);
        board.move("Red", 0);
    }
}
