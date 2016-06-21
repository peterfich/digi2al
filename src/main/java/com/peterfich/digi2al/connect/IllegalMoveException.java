package com.peterfich.digi2al.connect;

public class IllegalMoveException extends RuntimeException {

    public IllegalMoveException(String playerColour, String expectedPlayerColour) {
        super(String.format("Illegal move. Expected %s to move, was %s.", expectedPlayerColour, playerColour));
    }
}
