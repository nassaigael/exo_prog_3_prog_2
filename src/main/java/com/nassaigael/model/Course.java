package com.nassaigael.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Course {
    private final int id;
    private final String label;
    private final int credits;
    private final Teacher teacher;
    private List<Examen> examens = new ArrayList<>();
    private Map<GradeKey, Grade> grades = new HashMap<>();

    public void addExam(Examen examen){
        examens.add(examen);
        examen.setCourse(this);
    }

    public void addGrade(Grade grade){
        GradeKey key = new GradeKey(grade.getStudent().getId(), grade.getExamen().getId());
    }
}
