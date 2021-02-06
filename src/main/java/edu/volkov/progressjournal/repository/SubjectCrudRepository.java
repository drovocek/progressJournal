package edu.volkov.progressjournal.repository;

import edu.volkov.progressjournal.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface SubjectCrudRepository extends JpaRepository<Subject, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Subject s WHERE s.id=:id")
    int delete(int id);
}
