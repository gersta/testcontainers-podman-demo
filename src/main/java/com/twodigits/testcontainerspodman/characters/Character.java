package com.twodigits.testcontainerspodman.characters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Character")
@Builder @NoArgsConstructor @AllArgsConstructor
public class Character {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String race;
    private int age;
    private String weapon;
}
