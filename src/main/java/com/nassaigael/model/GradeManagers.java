package com.nassaigael.model;

import java.time.Instant;

public class GradeManagers {
    public double getExamGrade(Examen examen, Student student, Instant t){
        Course course = examen.getCourse();
        if (course == null) {
            return 0.0;
        }
        Grade grade = null;
        for (Grade g : course.getGrades().values()) {
            if (g.getExamen().equals(examen) && g.getStudent().equals(student)) {
                grade = g;
                break;
            }
        }
        return grade != null ? grade.getValueAt(t) : 0.0;
    }

    public double getCourseGrade(Course course, Student student, Instant t){
        double totalWeightedGrade = 0.0;
        double totalCoefficient = 0.0;

        for (Examen exam : course.getExamens()) {
            double grade = getExamGrade(exam, student, t);
            double coefficient = exam.getCoefficient();

            totalWeightedGrade += grade * coefficient;
            totalCoefficient += coefficient;
        }

        if (totalCoefficient == 0.0) {
            return 0.0;
        }

        return totalWeightedGrade / totalCoefficient;
    }
}
