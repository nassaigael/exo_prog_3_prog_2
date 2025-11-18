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
        if (examens == null) {
            examens = new ArrayList<>();
        }
        examens.add(examen);
        if (examen != null) {
            examen.setCourse(this);
        }
    }

    public void addGrade(Grade grade){
        if (grades == null) {
            grades = new HashMap<>();
        }
        if (grade != null && grade.getStudent() != null && grade.getExamen() != null) {
            GradeKey key = new GradeKey(grade.getStudent().getId(), grade.getExamen().getId());
            grades.put(key, grade);
        }
    }
}
