package com.peterfich.digi2al.connect.id;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleIdGeneratorTest {

    @Test
    public void generateUniqueIdsStartingFrom1() {
        IdGenerator idGenerator = new SimpleIdGenerator();

        assertThat(idGenerator.nextId()).isEqualTo(1);
        assertThat(idGenerator.nextId()).isEqualTo(2);
        assertThat(idGenerator.nextId()).isEqualTo(3);
        assertThat(idGenerator.nextId()).isEqualTo(4);
    }
}
