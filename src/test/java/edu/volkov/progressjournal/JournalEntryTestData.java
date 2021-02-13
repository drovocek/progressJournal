package edu.volkov.progressjournal;

import edu.volkov.progressjournal.model.JournalEntry;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static edu.volkov.progressjournal.StudentTestData.*;
import static edu.volkov.progressjournal.SubjectTestData.*;
import static java.time.temporal.ChronoUnit.DAYS;

public class JournalEntryTestData {
    public static final TestMatcher<JournalEntry> JOURNAL_ENTRY_MATCHER =
            TestMatcher.usingEqualsComparator(JournalEntry.class);

    public static final LocalDate TODAY = LocalDate.of(1992, 9, 27);
    public static final LocalDate TOMORROW = TODAY.plus(1, DAYS);
    public static final int NOT_FOUND_ID = 10;
    public static final int ENTRY_1_ID = 0;

    public static final JournalEntry ENTRY_1 = new JournalEntry(ENTRY_1_ID, 7, TODAY, HARRY, ASTRONOMY);
    public static final JournalEntry ENTRY_2 = new JournalEntry(ENTRY_1_ID + 1, 8, TOMORROW, HARRY, ARITHMANCY);
    public static final JournalEntry ENTRY_3 = new JournalEntry(ENTRY_1_ID + 2, 9, TODAY, HARRY, CHARMS);
    public static final JournalEntry ENTRY_4 = new JournalEntry(ENTRY_1_ID + 3, 10, TOMORROW, HERMIONE, HERBOLOGY);
    public static final JournalEntry ENTRY_5 = new JournalEntry(ENTRY_1_ID + 4, 7, TODAY, HERMIONE, HERBOLOGY);
    public static final JournalEntry ENTRY_6 = new JournalEntry(ENTRY_1_ID + 5, 8, TOMORROW, HERMIONE, MAGICAL_THEORY);


    public static final List<JournalEntry> journalEntryes = Arrays.asList(ENTRY_1, ENTRY_2, ENTRY_3, ENTRY_4, ENTRY_5, ENTRY_6);

    public static JournalEntry getNew() {
        return new JournalEntry(null, 10, LocalDate.now(), RONALD, ARITHMANCY);
    }

    public static JournalEntry getUpdated() {
        return new JournalEntry(ENTRY_1_ID, 5, TOMORROW, HERMIONE, HERBOLOGY);
    }
}
