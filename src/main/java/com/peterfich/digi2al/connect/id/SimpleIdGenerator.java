package com.peterfich.digi2al.connect.id;

public class SimpleIdGenerator implements IdGenerator {

    private int lastId = 0;

    @Override
    public synchronized int nextId() {
        return ++lastId;
    }
}
