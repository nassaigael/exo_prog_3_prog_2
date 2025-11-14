package com.nassaigael.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
@AllArgsConstructor
@Getter
public class Student extends User{
    private final String group;
    private final Tutor tutor;
}
