package edu.volkov.progressjournal;

import edu.volkov.progressjournal.model.Student;

import java.util.Arrays;
import java.util.List;

public class StudentTestData {
    public static final TestMatcher<Student> STUDENT_MATCHER = TestMatcher.usingEqualsComparator(Student.class);

    public static final int NOT_FOUND_ID = 10;
    public static final int HARRY_ID = 0;
    public static final int HERMIONE_ID = 1;
    public static final int RONALD_ID = 2;

    public static final Student HARRY = new Student(HARRY_ID, "Harry", "Potter", "Jamesovich");
    public static final Student HERMIONE = new Student(HERMIONE_ID, "Hermione", "Granger", "Hogusovna");
    public static final Student RONALD = new Student(RONALD_ID, "Ronald", "Weasley", "Arturovich");
    public static final List<Student> students = Arrays.asList(HARRY, HERMIONE, RONALD);

    public static Student getNew() {
        return new Student(null, "Draco", "Malfoy", "Luciusovich");
    }

    public static Student getUpdated() {
        return new Student(HARRY_ID, "updatedHarry", "updatedPotter", "updatedJamesovich");
    }
}
