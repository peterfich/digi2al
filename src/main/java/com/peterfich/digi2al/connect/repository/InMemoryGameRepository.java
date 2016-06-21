package com.peterfich.digi2al.connect.repository;


import com.peterfich.digi2al.connect.Game;

import java.util.HashMap;
import java.util.Map;

public class InMemoryGameRepository implements GameRepository{

    private final Map<Integer, Game> games = new HashMap<>();

    @Override
    public void saveGame(Game game) {
        games.put(game.getId(), game);
    }

    @Override
    public Game retrieveGame(int id) {
        return games.get(id);
    }
}
