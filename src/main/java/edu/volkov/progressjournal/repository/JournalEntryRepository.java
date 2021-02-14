package edu.volkov.progressjournal.repository;

import edu.volkov.progressjournal.model.JournalEntry;
import edu.volkov.progressjournal.to.JournalEntryTo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JournalEntryRepository {

    private final JournalEntryCrudRepository journalEntryCrudRepository;
    private final StudentCrudRepository studentCrudRepository;
    private final SubjectCrudRepository subjectCrudRepository;

    @Transactional
    public JournalEntry save(JournalEntryTo journalEntryTo) {
        if (!journalEntryTo.isNew() && get(journalEntryTo.getId()) == null) {
            return null;
        }
        return journalEntryCrudRepository.save(constructFromTo(journalEntryTo));
    }

    public boolean delete(int id) {
        return journalEntryCrudRepository.delete(id) != 0;
    }

    public JournalEntry get(int id) {
        return journalEntryCrudRepository.findById(id).orElse(null);
    }

    public List<JournalEntry> getAll() {
        return journalEntryCrudRepository.findAll();
    }

    @Transactional
    public JournalEntry constructFromTo(JournalEntryTo journalEntryTo) {
        return new JournalEntry(
                journalEntryTo.getId(),
                journalEntryTo.getMark(),
                journalEntryTo.getMarkDate(),
                studentCrudRepository.getOne(journalEntryTo.getStudentId()),
                subjectCrudRepository.getOne(journalEntryTo.getSubjectId())
        );
    }
}
