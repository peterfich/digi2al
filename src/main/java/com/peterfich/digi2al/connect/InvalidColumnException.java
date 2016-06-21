package com.peterfich.digi2al.connect;

public class InvalidColumnException extends RuntimeException {

    public InvalidColumnException(int maxIndex, int index) {
        super(String.format("Column index must be between 0 and %s, was %s.", maxIndex, index));
    }
}
