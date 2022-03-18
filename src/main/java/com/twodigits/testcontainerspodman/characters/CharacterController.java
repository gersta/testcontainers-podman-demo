package com.twodigits.testcontainerspodman.characters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    private CharacterRepository repository;

    @Autowired
    public CharacterController(CharacterRepository repository) {
        this.repository = repository;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createCharacter(@RequestBody CharacterDTO character) {
        Character characterDao = Character.builder()
                .name(character.getName())
                .race(character.getRace())
                .age(character.getAge())
                .weapon(character.getWeapon())
                .build();

        Character persistedCharacter = repository.save(characterDao);

        return ResponseEntity.created(
                URI.create(String.valueOf(persistedCharacter.getId()))
        ).body("Character %s created".formatted(persistedCharacter.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCharacter(@PathVariable("id") Long id) {
        repository.deleteById(id);

        return ResponseEntity.ok("Character with ID %s deleted".formatted(id));
    }

}
