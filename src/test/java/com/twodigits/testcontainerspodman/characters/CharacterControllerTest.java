package com.twodigits.testcontainerspodman.characters;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CharacterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CharacterRepository repository;

    @Test
    void adds_entry_to_database() throws Exception {
        String characterAsJson = """
                {"name": "Gandalf", "race": "Istari", "age": 3500, "weapon": "Staff"}
                """;

        mockMvc.perform(
                    post("/characters")
                            .contentType("application/json")
                            .content(characterAsJson)
                )
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("Character Gandalf created")))
                .andExpect(header().exists("location"));

        List<Character> characters = (List<Character>) repository.findAll();

        assertEquals(1, characters.size());
    }

}