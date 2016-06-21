package com.peterfich.digi2al.connect.repository;

import com.peterfich.digi2al.connect.Game;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryGameRepositoryTest {

    @Test
    public void shouldSaveAndRetrieveGame() {
        GameRepository repository = new InMemoryGameRepository();

        Game game = new Game();
        int id = 1;
        game.setId(id);

        repository.saveGame(game);
        Game retrievedGame = repository.retrieveGame(id);

        assertThat(retrievedGame).isSameAs(game);
    }
}