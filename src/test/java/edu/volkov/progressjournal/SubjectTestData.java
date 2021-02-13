package edu.volkov.progressjournal;

import edu.volkov.progressjournal.model.Subject;

import java.util.Arrays;
import java.util.List;


public class SubjectTestData {
    public static final TestMatcher<Subject> SUBJECT_MATCHER = TestMatcher.usingEqualsComparator(Subject.class);

    public static final int NOT_FOUND_ID = 10;
    public static final int ASTRONOMY_ID = 0;

    public static final Subject ASTRONOMY = new Subject(ASTRONOMY_ID, "Astronomy");
    public static final Subject CHARMS = new Subject(ASTRONOMY_ID + 1, "Charms");
    public static final Subject HERBOLOGY = new Subject(ASTRONOMY_ID + 2, "Herbology");
    public static final Subject ARITHMANCY = new Subject(ASTRONOMY_ID + 3, "Arithmancy");
    public static final Subject MAGICAL_THEORY = new Subject(ASTRONOMY_ID + 4, "Magical Theory");
    public static final List<Subject> subjects = Arrays.asList(ASTRONOMY, CHARMS, HERBOLOGY, ARITHMANCY, MAGICAL_THEORY);

    public static Subject getNew() {
        return new Subject(null, "Alcohologia");
    }

    public static Subject getUpdated() {
        return new Subject(ASTRONOMY_ID, "updatedAstronomy");
    }
}
