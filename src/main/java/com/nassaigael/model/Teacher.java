package com.nassaigael.model;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Teacher extends User{
    private final  String speciality;

    public Teacher(int id, String firstname, String lastname, LocalDate birthdate, String email, String phoneNumber, String speciality) {
        super(id, firstname, lastname, birthdate, email, phoneNumber);
        this.speciality = speciality;
    }
}
