package edu.volkov.progressjournal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "journal_entry")
public class Rating extends AbstractBaseEntity {

    @Column(name = "mark", nullable = false)
    @NotNull
    @Range(min = 0, max = 10)
    private Integer mark;

    @Column(name = "mark_date", nullable = false, columnDefinition = "data")
    @NotNull
    private LocalDate markDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Subject subject;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Student student;

}
