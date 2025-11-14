package com.nassaigael.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class Teacher extends User{
    private final  String speciality;
}
