package com.peterfich.digi2al.connect.stateupdaters;

import com.peterfich.digi2al.connect.Game;

public interface StateUpdater {

    void update(Game game, int columnIndex, int rowIndex);
}
