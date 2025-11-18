package com.nassaigael.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GradeManagerTest {

    private Student student;
    private Examen examen;
    private Tutor tutor;
    private Course course;
    private Examen examen1, examen2;
    private Grade grade1, grade2;
    private Instant t1, t3;
    private Teacher teacher;

    @BeforeEach
    void setUp() {
        tutor = new Tutor(1, "Rakoto", "Boto", LocalDate.of(1999, 01, 18), "rakoto@gmail.com", "+261389682194",
                "Parent");
        student = new Student(1, "Gael", "Ramahandrisoa", LocalDate.of(2005, 05, 18), "nassai@gmail.com",
                "+261342837614", "K3", tutor);
        teacher = new Teacher(1, "Toky", "Ramarozaka", LocalDate.of(1975, 05, 18), "ramarozaka@gmail.com",
                "+261326958974", "Java Developer");
        
        course = new Course(1, "PROG2", 6, teacher, null, null);

        LocalDateTime examTime1 = LocalDateTime.of(2025, 11, 1, 10, 0);
        examen1 = new Examen(1, "Midterm", course, examTime1, 2.0);
        LocalDateTime examTime2 = LocalDateTime.of(2025, 11, 15, 10, 0);
        examen2 = new Examen(2, "Final", course, examTime2, 3.0);
        course.addExam(examen1);
        course.addExam(examen2);

        grade1 = new Grade(student, examen1, 12.0);
        grade2 = new Grade(student, examen2, 14.0);
        course.addGrade(grade1);
        course.addGrade(grade2);

        t1 = examTime1.atZone(ZoneId.systemDefault()).toInstant().minusSeconds(3600);
        Instant changeTime = Instant.now().plusSeconds(3600);
        grade1.addGradeChange(new GradeChange(changeTime, 15.0, "Correction"));
        t3 = changeTime.plusSeconds(3600);
    }

    @Test
    void testGetExamGradeInitial(){
        assertEquals(12.0, GradeManagers.getExamGrade(examen1, student, t1)); 
    }

    @Test
    void testGetExameGradeAfterChange(){
        assertEquals(15.0, GradeManagers.getExamGrade(examen1, student, t3));
    }

    @Test
    void getExamenGradeNoGrade(){
        Student othStudent = new Student(2, "Other", "Bob", LocalDate.of(2000, 12, 12), "other@gmail.com", "+261389569586", "N3", tutor);
        assertEquals(00, GradeManagers.getExamGrade(examen1, othStudent, t3));
    }

    @Test
    void testgetCourseGrade(){
        assertEquals(13.2, GradeManagers.getCourseGrade(course, student, t1));

        grade2.addGradeChange(new GradeChange(t3, 16.0, "Bonus"));
        assertEquals(15.6, GradeManagers.getCourseGrade(course, student, t3));
    }
}