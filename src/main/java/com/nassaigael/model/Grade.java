package com.nassaigael.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Getter
public class Grade {
    private final Student student;
    private final Examen examen;
    private final double initValue;
    private final List<GradeChange> listGradeHistoricalChange = new ArrayList<>();

    public void addGradeChange(GradeChange gradeChange) {
        listGradeHistoricalChange.add(gradeChange);
    }

    public double getValueAt(Instant instant) {
        if (instant == null) {
            // if no instant provided, return latest value
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
