package com.peterfich.digi2al.connect;

public class IllegalColourException extends RuntimeException {

    public IllegalColourException(String playerColour, String player1Colour, String player2Colour) {
        super(String.format("Illegal colour. Allowed colours are %s and %s, was %s.", player1Colour, player2Colour, playerColour));
    }
}
