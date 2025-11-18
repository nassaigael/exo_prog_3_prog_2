package com.nassaigael.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public abstract class User {
    private int id;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private String email;
    private String phoneNumber;
}
