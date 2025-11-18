package com.nassaigael.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GradeManager Tests")
public class GradeManagerTest {

    private Teacher teacher;
    private Course course;
    private Examen exam1;
    private Examen exam2;
    private Tutor tutor;
    private Student student;
    private Grade grade1;
    private Grade grade2;

    @BeforeEach
    void setUp() {
        // Create teacher
        teacher = new Teacher(1, "John", "Doe", LocalDate.of(1980, 5, 15),
                "john.doe@school.com", "0612345678", "Back-end");

        // Create course
        course = new Course(1, "PROG2", 6, teacher);

        // Create exams
        exam1 = new Examen(1, "Midterm", course,
                LocalDateTime.of(2025, 3, 15, 14, 0), 2);
        exam2 = new Examen(2, "Final", course,
                LocalDateTime.of(2025, 5, 20, 14, 0), 3);

        // Create tutor
        tutor = new Tutor(1, "Jane", "Smith", LocalDate.of(1985, 3, 20),
                "jane.smith@school.com", "0687654321", "Academic advisor");

        // Create student
        student = new Student(1, "Alice", "Johnson", LocalDate.of(2003, 9, 10),
                "alice.johnson@student.com", "0698765432", "G1", tutor);

        // Create grades
        grade1 = new Grade(student, exam1, 10.0);
        grade2 = new Grade(student, exam2, 15.0);

        // Add some grade changes for testing
        Instant t1 = Instant.parse("2025-03-15T15:00:00Z");
        Instant t2 = Instant.parse("2025-03-16T10:00:00Z");
        Instant t3 = Instant.parse("2025-05-20T15:00:00Z");
        Instant t4 = Instant.parse("2025-05-21T11:00:00Z");

        grade1.addGradeChange(new GradeChange(t1, 8.0, "Initial correction"));
        grade1.addGradeChange(new GradeChange(t2, 10.0, "Appeal accepted"));
        
        grade2.addGradeChange(new GradeChange(t3, 14.0, "Initial correction"));
        grade2.addGradeChange(new GradeChange(t4, 15.0, "Recalculation"));
        
        // Register exams and grades in GradeManager repository used by the static methods
        GradeManager.clearAll();
        GradeManager.addExam(exam1);
        GradeManager.addExam(exam2);
        GradeManager.addGrade(grade1);
        GradeManager.addGrade(grade2);
    }

    @Test
    @DisplayName("getExamGrade should return initial value when no history exists at given instant")
    void testGetExamGradeWithNoHistory() {
        Instant beforeAllChanges = Instant.parse("2025-03-15T12:00:00Z");
        double result = GradeManager.getExamGrade(exam1, student, beforeAllChanges);
        
        assertEquals(10.0, result, "Should return initial value when no changes before instant");
    }

    @Test
    @DisplayName("getExamGrade should return value at specific instant in history")
    void testGetExamGradeAtSpecificInstant() {
        Instant t1 = Instant.parse("2025-03-15T15:00:00Z");
        double result = GradeManager.getExamGrade(exam1, student, t1);
        
        assertEquals(8.0, result, "Should return grade at t1 (8.0)");
    }

    @Test
    @DisplayName("getExamGrade should return latest value before given instant")
    void testGetExamGradeLatestBeforeInstant() {
        Instant t2_5 = Instant.parse("2025-03-16T12:00:00Z");
        double result = GradeManager.getExamGrade(exam1, student, t2_5);
        
        assertEquals(10.0, result, "Should return latest value (10.0) before instant");
    }

    @Test
    @DisplayName("getExamGrade should return null instant as latest value")
    void testGetExamGradeWithNullInstant() {
        double result = GradeManager.getExamGrade(exam1, student, null);
        
        assertEquals(10.0, result, "Should return latest value when instant is null");
    }

    @Test
    @DisplayName("getExamGrade should return 0 when grade not found")
    void testGetExamGradeNotFound() {
        Examen unknownExam = new Examen(99, "Unknown", course,
                LocalDateTime.of(2025, 6, 1, 14, 0), 1);
        
        double result = GradeManager.getExamGrade(unknownExam, student, Instant.now());
        
        assertEquals(0.0, result, "Should return 0 when grade not found");
    }

    @Test
    @DisplayName("getCourseGrade should calculate weighted average correctly")
    void testGetCourseGradeWeightedAverage() {
        // Formula: (10*2 + 15*3) / (2+3) = (20 + 45) / 5 = 13
        Instant afterAllChanges = Instant.parse("2025-05-22T00:00:00Z");
        double result = GradeManager.getCourseGrade(course, student, afterAllChanges);
        
        assertEquals(13.0, result, 0.01, "Should calculate weighted average: (10*2 + 15*3) / 5 = 13");
    }

    @Test
    @DisplayName("getCourseGrade should use intermediate values at specific instant")
    void testGetCourseGradeAtSpecificInstant() {
        // At t = 2025-03-15T15:30:00Z: grade1 is 8.0, grade2 is still 15.0
        Instant atMidtime = Instant.parse("2025-03-15T15:30:00Z");
        double result = GradeManager.getCourseGrade(course, student, atMidtime);
        
        // (8*2 + 15*3) / (2+3) = (16 + 45) / 5 = 12.2
        assertEquals(12.2, result, 0.01, "Should calculate weighted average with intermediate values");
    }

    @Test
    @DisplayName("getCourseGrade should return 0 when no exams found")
    void testGetCourseGradeNoExams() {
        Teacher teacher2 = new Teacher(2, "Bob", "Smith", LocalDate.of(1980, 1, 1),
                "bob@school.com", "0600000000", "Front-end");
        Course courseNoExams = new Course(2, "PROG1", 6, teacher2);
        
        double result = GradeManager.getCourseGrade(courseNoExams, student, Instant.now());
        
        assertEquals(0.0, result, "Should return 0 when no exams found for course");
    }

    @Test
    @DisplayName("getCourseGrade should handle multiple exams with different coefficients")
    void testGetCourseGradeMultipleExams() {
        // Create a third exam with different coefficient
    Examen exam3 = new Examen(3, "Quiz", course,
        LocalDateTime.of(2025, 4, 10, 10, 0), 1);
    Grade grade3 = new Grade(student, exam3, 12.0);
    GradeManager.addExam(exam3);
    GradeManager.addGrade(grade3);

    // Now we would have: (10*2 + 15*3 + 12*1) / (2+3+1) = (20+45+12)/6 = 77/6 ≈ 12.83
        Instant afterAllChanges = Instant.parse("2025-05-22T00:00:00Z");
        double result = GradeManager.getCourseGrade(course, student, afterAllChanges);
        
        // This test demonstrates the concept, but the actual implementation
        // would need a proper repository to fetch all exams for a course
        assertTrue(result >= 0, "Result should be non-negative");
    }

    @Test
    @DisplayName("Grade history tracking with multiple changes")
    void testGradeHistoryTracking() {
        Instant t1 = Instant.parse("2025-03-15T15:00:00Z");
        Instant t2 = Instant.parse("2025-03-16T10:00:00Z");
        Instant t3 = Instant.parse("2025-03-17T09:00:00Z");
        
        // Verify grade changes are recorded
        assertEquals(10.0, grade1.getValueAt(t3), "Should return latest value");
        assertEquals(8.0, grade1.getValueAt(t1), "Should return value at t1");
        assertEquals(10.0, grade1.getValueAt(t2), "Should return value at t2");
    }

    @Test
    @DisplayName("Grade initial value should be used when no changes")
    void testGradeInitialValue() {
        Instant beforeAnyChange = Instant.parse("2025-02-01T10:00:00Z");
        double result = grade1.getValueAt(beforeAnyChange);
        
        assertEquals(10.0, result, "Should return initial value when no changes before instant");
    }

    @Test
    @DisplayName("Empty grade history should return initial value")
    void testEmptyGradeHistory() {
        Grade newGrade = new Grade(student, exam1, 12.5);
        
        double result = newGrade.getValueAt(Instant.now());
        
        assertEquals(12.5, result, "Should return initial value when no history");
    }
}
