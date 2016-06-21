package com.peterfich.digi2al.connect.repository;

import com.peterfich.digi2al.connect.Game;

public interface GameRepository {

    void saveGame(Game game);

    Game retrieveGame(int id);
}
