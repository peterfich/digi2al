package com.peterfich.digi2al.connect.stateupdaters;

import com.peterfich.digi2al.connect.Board;
import com.peterfich.digi2al.connect.Game;
import com.peterfich.digi2al.connect.GameState;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DrawTest {

    private Board board = mock(Board.class);
    private Game game = mock(Game.class);
    private StateUpdater stateUpdater;

    @Before
    public void setUp() throws Exception {
        when(game.getPlayer1Colour()).thenReturn("Red");
        when(game.getBoard()).thenReturn(board);

        stateUpdater = new Draw();
    }

    @Test
    public void topRowInBoardIsFull() {
        Board board = mock(Board.class);
        when(board.valueAt(0, 5)).thenReturn("Red");
        when(board.valueAt(1, 5)).thenReturn("Red");
        when(board.valueAt(2, 5)).thenReturn("Red");
        when(board.valueAt(3, 5)).thenReturn("Red");
        when(board.valueAt(4, 5)).thenReturn("Red");
        when(board.valueAt(5, 5)).thenReturn("Red");
        when(board.valueAt(6, 5)).thenReturn("Red");

        when(board.getRowCount()).thenReturn(6);
        when(board.getColumnCount()).thenReturn(7);

        when(game.getBoard()).thenReturn(board);

        stateUpdater.update(game, 6, 5);

        verify(game).setState(GameState.draw);
    }

    @Test
    public void stillRoomOnBoard() {
        Board board = mock(Board.class);
        when(board.valueAt(0, 5)).thenReturn(null);
        when(board.valueAt(1, 5)).thenReturn("Red");
        when(board.valueAt(2, 5)).thenReturn("Red");
        when(board.valueAt(3, 5)).thenReturn("Red");
        when(board.valueAt(4, 5)).thenReturn("Red");
        when(board.valueAt(5, 5)).thenReturn("Red");
        when(board.valueAt(6, 5)).thenReturn("Red");

        when(board.getRowCount()).thenReturn(6);
        when(board.getColumnCount()).thenReturn(7);

        when(game.getBoard()).thenReturn(board);

        stateUpdater.update(game, 6, 5);

        verify(game, never()).setState(any());
    }
}
