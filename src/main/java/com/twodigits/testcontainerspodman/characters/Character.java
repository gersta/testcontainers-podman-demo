package com.twodigits.testcontainerspodman.characters;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Character")
public class Character {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String race;
    private int age;
    private String weapon;
}
