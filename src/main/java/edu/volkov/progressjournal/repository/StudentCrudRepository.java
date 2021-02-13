package edu.volkov.progressjournal.repository;

import edu.volkov.progressjournal.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface StudentCrudRepository extends JpaRepository<Student, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Student s WHERE s.id=:id")
    int delete(int id);
}
