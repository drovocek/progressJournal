package edu.volkov.progressjournal.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import edu.volkov.progressjournal.HasId;
import edu.volkov.progressjournal.model.JournalEntry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JournalEntryTo implements HasId {

    protected Integer id;

    @NotNull
    @Range(min = 1, max = 10, message = "Mark must be between 1 and 10")
    private Integer mark;

    @NotNull(message = "MarkDate must be not null")
    @JsonFormat(pattern="yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate markDate;

    @NotNull(message = "Student id must be not null")
    private Integer studentId;

    @NotNull(message = "Subject id must be not null")
    private Integer subjectId;

    @JsonIgnore
    public boolean isNew() {
        return id == null;
    }

    public JournalEntryTo(JournalEntry journalEntry) {
        this.id = journalEntry.getId();
        this.mark = journalEntry.getMark();
        this.markDate = journalEntry.getMarkDate();
        this.studentId = journalEntry.getStudent().getId();
        this.subjectId = journalEntry.getSubject().getId();
    }
}
