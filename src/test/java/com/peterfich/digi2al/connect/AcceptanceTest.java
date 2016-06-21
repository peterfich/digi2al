package com.peterfich.digi2al.connect;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.Ignore;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

@Ignore
public class AcceptanceTest {

    @Test
    public void startGame() {
        Parameters parameters = new Parameters();
        parameters.setPlayer1("Red");
        parameters.setPlayer2("Blue");
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{\"player1\": \"Red\", \"player2\": \"Blue\" }")
                .accept(ContentType.JSON)
                .post("/connect4")
                .andReturn();

        response.then().statusCode(201);
        response.then().contentType(ContentType.JSON);
        Game createdGame = response.as(Game.class);

        String location = response.header("Location");
        assertThat(location).isEqualTo("http://localhost:8080/connect4/" + createdGame.getId());

        assertThat(createdGame.getState()).isEqualTo(GameState.player1ToMove);
        assertThat(createdGame.getPlayer1Colour()).isEqualTo("Red");
        assertThat(createdGame.getPlayer2Colour()).isEqualTo("Blue");
    }

    @Test
    public void checkGameState() {
        String location = startNewGame();

        Game retrievedGame = get(location).as(Game.class);

        assertThat(retrievedGame.getState()).isEqualTo(GameState.player1ToMove);
        assertThat(retrievedGame.getPlayer1Colour()).isEqualTo("Red");
        assertThat(retrievedGame.getPlayer2Colour()).isEqualTo("Blue");
    }

    @Test
    public void makeAValidMove() {
        String location = startNewGame();

        Response firstMoveResponse = placeDisc(location, "Red", 0);

        firstMoveResponse.then().statusCode(200);
    }

    @Test
    public void makeAOutOfTurnMove() {
        String location = startNewGame();

        Response firstMoveResponse = placeDisc(location, "Blue", 0);

        firstMoveResponse.then().statusCode(409);
        firstMoveResponse.then().body("message", equalTo("Illegal move. Expected Red to move, was Blue."));
    }

    @Test
    public void makeAOutOfBounceMove() {
        String location = startNewGame();

        Response firstMoveResponse = placeDisc(location, "Red", 10);

        firstMoveResponse.then().statusCode(409);
        firstMoveResponse.then().body("message", equalTo("Column index must be between 0 and 6, was 10."));
    }

    @Test
    public void overfillColumn() {
        String location = startNewGame();

        placeDisc(location, "Red", 0);
        placeDisc(location, "Blue", 0);
        placeDisc(location, "Red", 0);
        placeDisc(location, "Blue", 0);
        placeDisc(location, "Red", 0);
        placeDisc(location, "Blue", 0);

        Response lastMoveResponse = placeDisc(location, "Red", 0);

        lastMoveResponse.then().statusCode(409);
        lastMoveResponse.then().body("message", equalTo("Column 0 is all ready full."));
    }

    @Test
    public void player1ToWin() {
        String location = startNewGame();

        placeDisc(location, "Red", 0);
        placeDisc(location, "Blue", 1);
        placeDisc(location, "Red", 0);
        placeDisc(location, "Blue", 1);
        placeDisc(location, "Red", 0);
        placeDisc(location, "Blue", 1);

        Response lastMoveResponse = placeDisc(location, "Red", 0);

        lastMoveResponse.then().statusCode(200);
        lastMoveResponse.then().body("state", equalTo("player1Won"));
    }

    @Test
    public void player2ToWin() {
        String location = startNewGame();

        placeDisc(location, "Red", 0);
        placeDisc(location, "Blue", 1);
        placeDisc(location, "Red", 0);
        placeDisc(location, "Blue", 1);
        placeDisc(location, "Red", 2);
        placeDisc(location, "Blue", 1);
        placeDisc(location, "Red", 2);

        Response lastMoveResponse = placeDisc(location, "Blue", 1);

        lastMoveResponse.then().statusCode(200);
        lastMoveResponse.then().body("state", equalTo("player2Won"));
    }

    @Test
    public void player2HasAllReadyWon() {
        String location = startNewGame();

        placeDisc(location, "Red", 0);
        placeDisc(location, "Blue", 1);
        placeDisc(location, "Red", 0);
        placeDisc(location, "Blue", 1);
        placeDisc(location, "Red", 2);
        placeDisc(location, "Blue", 1);
        placeDisc(location, "Red", 2);
        placeDisc(location, "Blue", 1);

        Response lastMoveResponse = placeDisc(location, "Red", 1);

        lastMoveResponse.then().statusCode(409);
        lastMoveResponse.then().body("code", equalTo(409));
        lastMoveResponse.then().body("message", equalTo("Game has all ready ended, Blue won."));
    }

    @Test
    public void draw() {
        String location = startNewGame();

        placeDisc(location, "Red", 0);
        placeDisc(location, "Blue", 1);

        placeDisc(location, "Red", 0);
        placeDisc(location, "Blue", 1);

        placeDisc(location, "Red", 1);
        placeDisc(location, "Blue", 0);

        placeDisc(location, "Red", 0);
        placeDisc(location, "Blue", 1);

        placeDisc(location, "Red", 0);
        placeDisc(location, "Blue", 1);

        placeDisc(location, "Red", 1);
        placeDisc(location, "Blue", 0);

        placeDisc(location, "Red", 2);
        placeDisc(location, "Blue", 3);

        placeDisc(location, "Red", 2);
        placeDisc(location, "Blue", 3);

        placeDisc(location, "Red", 3);
        placeDisc(location, "Blue", 2);

        placeDisc(location, "Red", 2);
        placeDisc(location, "Blue", 3);

        placeDisc(location, "Red", 2);
        placeDisc(location, "Blue", 3);

        placeDisc(location, "Red", 3);
        placeDisc(location, "Blue", 2);

        placeDisc(location, "Red", 4);
        placeDisc(location, "Blue", 5);

        placeDisc(location, "Red", 4);
        placeDisc(location, "Blue", 5);

        placeDisc(location, "Red", 5);
        placeDisc(location, "Blue", 4);

        placeDisc(location, "Red", 4);
        placeDisc(location, "Blue", 5);

        placeDisc(location, "Red", 4);
        placeDisc(location, "Blue", 5);

        placeDisc(location, "Red", 5);
        placeDisc(location, "Blue", 4);

        placeDisc(location, "Red", 6);
        placeDisc(location, "Blue", 6);

        placeDisc(location, "Red", 6);
        placeDisc(location, "Blue", 6);

        placeDisc(location, "Red", 6);

        Response lastMoveResponse = placeDisc(location, "Blue", 6);

        lastMoveResponse.then().statusCode(200);
        lastMoveResponse.then().body("state", equalTo("draw"));
    }

    @Test
    public void allReadyADraw() {
        String location = startNewGame();

        placeDisc(location, "Red", 0);
        placeDisc(location, "Blue", 1);

        placeDisc(location, "Red", 0);
        placeDisc(location, "Blue", 1);

        placeDisc(location, "Red", 1);
        placeDisc(location, "Blue", 0);

        placeDisc(location, "Red", 0);
        placeDisc(location, "Blue", 1);

        placeDisc(location, "Red", 0);
        placeDisc(location, "Blue", 1);

        placeDisc(location, "Red", 1);
        placeDisc(location, "Blue", 0);

        placeDisc(location, "Red", 2);
        placeDisc(location, "Blue", 3);

        placeDisc(location, "Red", 2);
        placeDisc(location, "Blue", 3);

        placeDisc(location, "Red", 3);
        placeDisc(location, "Blue", 2);

        placeDisc(location, "Red", 2);
        placeDisc(location, "Blue", 3);

        placeDisc(location, "Red", 2);
        placeDisc(location, "Blue", 3);

        placeDisc(location, "Red", 3);
        placeDisc(location, "Blue", 2);

        placeDisc(location, "Red", 4);
        placeDisc(location, "Blue", 5);

        placeDisc(location, "Red", 4);
        placeDisc(location, "Blue", 5);

        placeDisc(location, "Red", 5);
        placeDisc(location, "Blue", 4);

        placeDisc(location, "Red", 4);
        placeDisc(location, "Blue", 5);

        placeDisc(location, "Red", 4);
        placeDisc(location, "Blue", 5);

        placeDisc(location, "Red", 5);
        placeDisc(location, "Blue", 4);

        placeDisc(location, "Red", 6);
        placeDisc(location, "Blue", 6);

        placeDisc(location, "Red", 6);
        placeDisc(location, "Blue", 6);

        placeDisc(location, "Red", 6);
        placeDisc(location, "Blue", 6);

        Response lastMoveResponse = placeDisc(location, "Red", 6);

        lastMoveResponse.then().statusCode(409);
        lastMoveResponse.then().body("code", equalTo(409));
        lastMoveResponse.then().body("message", equalTo("Game has all ready ended, it was a draw."));
    }

    @Test
    public void unknownPlayer() {
        String location = startNewGame();

        Response response = placeDisc(location, "Green", 0);

        response.then().statusCode(409);
        response.then().body("code", equalTo(409));
        response.then().body("message", equalTo("Illegal colour. Allowed colours are Red and Blue, was Green."));
    }

    private String startNewGame() {
        Parameters parameters = new Parameters();
        parameters.setPlayer1("Red");
        parameters.setPlayer2("Blue");
        Response response = given()
                .contentType(ContentType.JSON)
                .body(parameters)
                .accept(ContentType.JSON)
                .post("/connect4")
                .andReturn();

        return response.header("Location");
    }

    private Response placeDisc(String location, String player, int column) {
        Move move = new Move();
        move.setPlayer(player);
        move.setColumn(column);

        return given()
                .contentType(ContentType.JSON)
                .body(move)
                .post(location)
                .andReturn();

    }
}
