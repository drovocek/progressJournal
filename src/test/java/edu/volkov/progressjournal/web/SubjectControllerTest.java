package edu.volkov.progressjournal.web;

import edu.volkov.progressjournal.model.Subject;
import edu.volkov.progressjournal.repository.SubjectCrudRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static edu.volkov.progressjournal.util.JsonUtil.writeValue;
import static edu.volkov.progressjournal.SubjectTestData.*;
import static edu.volkov.progressjournal.TestUtil.readFromJson;
import static edu.volkov.progressjournal.util.exception.ErrorType.DATA_NOT_FOUND;
import static edu.volkov.progressjournal.util.exception.ErrorType.VALIDATION_ERROR;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SubjectControllerTest extends AbstractControllerTest {

    private static final String REST_URL = SubjectController.REST_URL + '/';

    @Autowired
    private SubjectCrudRepository repository;

    @Test
    void create() throws Exception {
        Subject newSubject = getNew();

        ResultActions action = perform(post(REST_URL)
                .contentType(APPLICATION_JSON)
                .content(writeValue(newSubject)))
                .andDo(print());

        Subject created = readFromJson(action, Subject.class);
        int newId = created.id();
        newSubject.setId(newId);

        SUBJECT_MATCHER.assertMatch(created, newSubject);
        SUBJECT_MATCHER.assertMatch(repository.findById(newId).orElse(null), newSubject);
    }

    @Test
    void createInvalid() throws Exception {
        Subject invalid = new Subject();
        invalid.setName("");

        perform(post(REST_URL)
                .contentType(APPLICATION_JSON)
                .content(writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void update() throws Exception {
        Subject updated = getUpdated();

        perform(put(REST_URL + ASTRONOMY_ID).contentType(APPLICATION_JSON)
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        SUBJECT_MATCHER.assertMatch(repository.findById(ASTRONOMY_ID).orElse(null), updated);
    }

    @Test
    void updateInvalid() throws Exception {
        Subject invalid = new Subject();
        invalid.setId(ASTRONOMY_ID);
        invalid.setName("");

        perform(put(REST_URL + ASTRONOMY_ID).contentType(APPLICATION_JSON)
                .content(writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void updateHtmlUnsafe() throws Exception {
        Subject updated = new Subject(ASTRONOMY);
        updated.setName("<script>alert(123)</script>");

        perform(put(REST_URL + ASTRONOMY_ID)
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void deleteGood() throws Exception {
        perform(delete(REST_URL + ASTRONOMY_ID))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertNull(repository.findById(ASTRONOMY_ID).orElse(null));
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
        perform(get(REST_URL + ASTRONOMY_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(SUBJECT_MATCHER.contentJson(ASTRONOMY));
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
                .andExpect(SUBJECT_MATCHER.contentJson(subjects));
    }
}