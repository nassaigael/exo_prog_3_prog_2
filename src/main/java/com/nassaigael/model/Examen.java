package com.nassaigael.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Examen {
    private final int id;
    private final String title;
    private Course course;
    private final LocalDateTime dateTime;
    private final double coefficient;

    public void setCourse(Course course){
        this.course = course;
    }
}
