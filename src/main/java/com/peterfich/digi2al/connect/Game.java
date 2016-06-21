package com.peterfich.digi2al.connect;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Game {

    private int id;
    private GameState state;
    private String player1Colour;
    private String player2Colour;
    @JsonIgnore
    private Board board;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public String getPlayer1Colour() {
        return player1Colour;
    }

    public void setPlayer1Colour(String player1Colour) {
        this.player1Colour = player1Colour;
    }

    public String getPlayer2Colour() {
        return player2Colour;
    }

    public void setPlayer2Colour(String player2Colour) {
        this.player2Colour = player2Colour;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
