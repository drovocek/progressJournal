package edu.volkov.progressjournal.repository;

import edu.volkov.progressjournal.model.JournalEntry;
import edu.volkov.progressjournal.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface JournalEntryCrudRepository extends JpaRepository<JournalEntry, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM JournalEntry je WHERE je.id=:id")
    int delete(int id);
}
