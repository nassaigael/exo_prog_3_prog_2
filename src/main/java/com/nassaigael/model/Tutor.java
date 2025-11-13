package com.nassaigael.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Tutor extends User{
    private final String description;

    public Tutor(int id, String firstname, String lastname, LocalDate birthdate, String email, String phoneNumber, String description) {
        super(id, firstname, lastname, birthdate, email, phoneNumber);
        this.description = description;
    }
}
