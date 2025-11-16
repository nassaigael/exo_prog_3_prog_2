package com.nassaigael.model;


import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Student extends User{
    private final String group;
    private final Tutor tutor;

    public Student(int id, String firstname, String lastname, LocalDate birthdate, String email, String phoneNumber, String group, Tutor tutor) {
        super(id, firstname, lastname, birthdate, email, phoneNumber);
        this.group = group;
        this.tutor = tutor;
    }
}
