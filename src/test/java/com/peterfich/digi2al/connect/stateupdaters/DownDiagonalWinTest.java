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

public class DownDiagonalWinTest {

    private Board board = mock(Board.class);
    private Game game = mock(Game.class);
    private StateUpdater stateUpdater;

    @Before
    public void setUp() throws Exception {
        when(board.valueAt(3, 0)).thenReturn("Red");
        when(board.valueAt(2, 1)).thenReturn("Red");
        when(board.valueAt(1, 2)).thenReturn("Red");
        when(board.valueAt(0, 3)).thenReturn("Red");
        when(board.getColumnCount()).thenReturn(7);
        when(board.getRowCount()).thenReturn(6);

        when(game.getPlayer1Colour()).thenReturn("Red");
        when(game.getBoard()).thenReturn(board);

        stateUpdater = new DownDiagonalWin(4);
    }

    @Test
    public void player1ToWinOnOffset0() {
        stateUpdater.update(game, 3, 0);

        verify(game).setState(GameState.player1Won);
    }

    @Test
    public void player1ToWinOnOffset1() {
        stateUpdater.update(game, 2, 1);

        verify(game).setState(GameState.player1Won);
    }

    @Test
    public void player1ToWinOnOffset2() {
        stateUpdater.update(game, 1, 2);

        verify(game).setState(GameState.player1Won);
    }

    @Test
    public void player1ToWinOnOffset3() {
        stateUpdater.update(game, 0, 3);

        verify(game).setState(GameState.player1Won);
    }

    @Test
    public void lastDiscTooLow() {
        Board board = mock(Board.class);
        when(board.valueAt(5, 0)).thenReturn("Red");
        when(board.valueAt(4, 1)).thenReturn("Red");
        when(board.valueAt(3, 2)).thenReturn("Red");

        when(game.getBoard()).thenReturn(board);

        stateUpdater.update(game, 5, 0);

        verify(game, never()).setState(any());
    }

    @Test
    public void lastDiscTooHigh() {
        Board board = mock(Board.class);
        when(board.valueAt(5, 3)).thenReturn("Red");
        when(board.valueAt(4, 4)).thenReturn("Red");
        when(board.valueAt(3, 5)).thenReturn("Red");

        when(game.getBoard()).thenReturn(board);

        stateUpdater.update(game, 3, 5);

        verify(game, never()).setState(any());
    }

    @Test
    public void lastDiscTooLeft() {
        Board board = mock(Board.class);
        when(board.valueAt(0, 6)).thenReturn("Red");
        when(board.valueAt(1, 5)).thenReturn("Red");
        when(board.valueAt(2, 4)).thenReturn("Red");

        when(game.getBoard()).thenReturn(board);

        stateUpdater.update(game, 1, 5);

        verify(game, never()).setState(any());
    }

    @Test
    public void lastDiscTooRight() {
        Board board = mock(Board.class);
        when(board.valueAt(4, 4)).thenReturn("Red");
        when(board.valueAt(5, 3)).thenReturn("Red");
        when(board.valueAt(6, 2)).thenReturn("Red");

        when(game.getBoard()).thenReturn(board);

        stateUpdater.update(game, 4, 4);

        verify(game, never()).setState(any());
    }
}
