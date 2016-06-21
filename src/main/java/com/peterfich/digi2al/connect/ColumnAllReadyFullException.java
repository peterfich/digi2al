package com.peterfich.digi2al.connect;

public class ColumnAllReadyFullException extends RuntimeException {

    public ColumnAllReadyFullException(int columnIndex) {
        super(String.format("Column %s is all ready full.", columnIndex));
    }
}
