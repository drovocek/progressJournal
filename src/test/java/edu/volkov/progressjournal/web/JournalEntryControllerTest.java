package edu.volkov.progressjournal.web;

import edu.volkov.progressjournal.model.JournalEntry;
import edu.volkov.progressjournal.repository.JournalEntryRepository;
import edu.volkov.progressjournal.to.JournalEntryTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static edu.volkov.progressjournal.JournalEntryTestData.*;
import static edu.volkov.progressjournal.util.JsonUtil.writeValue;
import static edu.volkov.progressjournal.TestUtil.readFromJson;
import static edu.volkov.progressjournal.util.exception.ErrorType.DATA_NOT_FOUND;
import static edu.volkov.progressjournal.util.exception.ErrorType.VALIDATION_ERROR;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class JournalEntryControllerTest extends AbstractControllerTest {

    private static final String REST_URL = JournalEntryController.REST_URL + '/';

    @Autowired
    private JournalEntryRepository repository;

    @Test
    void create() throws Exception {
        JournalEntry newJEntry = getNew();
        JournalEntryTo newJEntryTo = new JournalEntryTo(newJEntry);

        ResultActions action = perform(post(REST_URL)
                .contentType(APPLICATION_JSON)
                .content(writeValue(newJEntryTo)))
                .andDo(print());

        JournalEntry created = readFromJson(action, JournalEntry.class);
        int newId = created.id();
        newJEntry.setId(newId);

        JOURNAL_ENTRY_MATCHER.assertMatch(created, newJEntry);
        JOURNAL_ENTRY_MATCHER.assertMatch(repository.get(newId), newJEntry);
    }

    @Test
    void createInvalid() throws Exception {
        JournalEntry invalid = new JournalEntry(ENTRY_1);
        JournalEntryTo invalidTo = new JournalEntryTo(invalid);
        invalid.setId(null);
        invalidTo.setMarkDate(null);
        invalidTo.setMark(11);

        perform(post(REST_URL)
                .contentType(APPLICATION_JSON)
                .content(writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void update() throws Exception {
        JournalEntry updated = getUpdated();
        JournalEntryTo updatedTo = new JournalEntryTo(updated);

        perform(put(REST_URL + ENTRY_1_ID).contentType(APPLICATION_JSON)
                .content(writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        JOURNAL_ENTRY_MATCHER.assertMatch(repository.get(ENTRY_1_ID), updated);
    }

    @Test
    void updateInvalid() throws Exception {
        JournalEntryTo invalidTo = new JournalEntryTo();
        invalidTo.setId(ENTRY_1_ID);
        invalidTo.setMarkDate(null);
        invalidTo.setMark(11);

        perform(put(REST_URL + ENTRY_1_ID).contentType(APPLICATION_JSON)
                .content(writeValue(invalidTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void deleteGood() throws Exception {
        perform(delete(REST_URL + ENTRY_1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertNull(repository.get(ENTRY_1_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(delete(REST_URL + NOT_FOUND_ID))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND));
    }

    @Test
    void getGood() throws Exception {
        perform(get(REST_URL + ENTRY_1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(JOURNAL_ENTRY_MATCHER.contentJson(ENTRY_1));
    }

    @Test
    void getNotFound() throws Exception {
        perform(get(REST_URL + NOT_FOUND_ID))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND));
    }

    @Test
    void getAll() throws Exception {
        perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(JOURNAL_ENTRY_MATCHER.contentJson(journalEntryes));
    }
}