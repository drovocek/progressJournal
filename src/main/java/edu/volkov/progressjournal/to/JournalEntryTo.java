package edu.volkov.progressjournal.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.volkov.progressjournal.HasId;
import lombok.Data;

import java.time.LocalDate;

@Data
public class JournalEntryTo implements HasId {

    protected Integer id;

    private int mark;

    private LocalDate markDate;

    private int studentId;

    private int subjectId;

    @JsonIgnore
    public boolean isNew() {
        return id == null;
    }

}
