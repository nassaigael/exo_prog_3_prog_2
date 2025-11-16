package com.nassaigael.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Examen {
    private final int id;
    private final String title;
    private final Course course;
    private final LocalDateTime dateTime;
    private final double coefficient;
}
