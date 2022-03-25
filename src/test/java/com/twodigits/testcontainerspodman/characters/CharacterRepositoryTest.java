package com.twodigits.testcontainerspodman.characters;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class CharacterRepositoryTest {

    @Autowired
    private CharacterRepository repository;

    @Test
    void empty_database_on_init() {
        List<Character> characters = (List<Character>) repository.findAll();

        assertTrue(characters.isEmpty());
    }
}