package com.nassaigael.model;

import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
public class Grade {
    private final Student student;
    private final Examen examen;
    private final double initValue;
    private final List<GradeChange> listGradeHistoricalChange;

    public Grade(Student student, Examen examen, double initValue) {
        this.student = student;
        this.examen = examen;
        this.initValue = initValue;
        this.listGradeHistoricalChange = new ArrayList<>();
    }

    public Grade(Student student, Examen examen, double initValue, List<GradeChange> listGradeHistoricalChange) {
        this.student = student;
        this.examen = examen;
        this.initValue = initValue;
        this.listGradeHistoricalChange = listGradeHistoricalChange;
    }

    public void addGradeChange(GradeChange gradeChange) {
        listGradeHistoricalChange.add(gradeChange);
    }

    public double getValueAt(Instant instant) {
        if (instant == null) {
            return listGradeHistoricalChange.stream()
                    .max(Comparator.comparing(GradeChange::getTime))
                    .map(GradeChange::getValue)
                    .orElse(initValue);
        }

        return listGradeHistoricalChange.stream()
                .filter(gc -> !gc.getTime().isAfter(instant))
                .max(Comparator.comparing(GradeChange::getTime))
                .map(GradeChange::getValue)
                .orElse(initValue);
    }
}
