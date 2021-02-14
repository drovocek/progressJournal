package edu.volkov.progressjournal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import edu.volkov.progressjournal.View;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "journal_entry")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class JournalEntry extends AbstractBaseEntity {

    @Column(name = "mark", nullable = false)
    @NotNull
    @Range(min = 1, max = 10)
    private Integer mark;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "mark_date", nullable = false)
    private LocalDate markDate;

    @NotNull(groups = View.Persist.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Student student;

    @NotNull(groups = View.Persist.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Subject subject;

    public JournalEntry(Integer id, Integer mark, LocalDate markDate) {
        super(id);
        this.mark = mark;
        this.markDate = markDate;
    }

    public JournalEntry(Integer id, Integer mark, LocalDate markDate, Student student, Subject subject) {
        super(id);
        this.mark = mark;
        this.markDate = markDate;
        this.student = student;
        this.subject = subject;
    }

    public JournalEntry(JournalEntry journalEntry) {
        super(journalEntry.getId());
        this.mark = journalEntry.getMark();
        this.markDate = journalEntry.getMarkDate();
        this.student = journalEntry.getStudent();
        this.subject = journalEntry.getSubject();
    }

    @Override
    public String toString() {
        return "JournalEntry{" +
                "id=" + id +
                ", mark=" + mark +
                ", markDate=" + markDate +
                ", student=" + student +
                ", subject=" + subject +
                '}';
    }
}
