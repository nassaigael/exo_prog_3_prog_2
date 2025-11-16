package com.nassaigael.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Course {
    private final int id;
    private final String label;
    private final int credits;
    private final Teacher teacher;
}
