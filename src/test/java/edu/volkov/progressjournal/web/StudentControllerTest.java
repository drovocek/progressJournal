package edu.volkov.progressjournal.web;

import edu.volkov.progressjournal.model.Student;
import edu.volkov.progressjournal.repository.StudentCrudRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static edu.volkov.progressjournal.util.exception.ErrorType.DATA_NOT_FOUND;
import static edu.volkov.progressjournal.util.exception.ErrorType.VALIDATION_ERROR;
import static edu.volkov.util.data.StudentTestData.*;
import static edu.volkov.util.data.TestUtil.readFromJson;
import static edu.volkov.util.data.json.JsonUtil.writeValue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StudentControllerTest extends AbstractControllerTest {

    private static final String REST_URL = StudentController.REST_URL + '/';

    @Autowired
    private StudentCrudRepository repository;

    @Test
    void create() throws Exception {
        Student newStudent = getNew();
        ResultActions action = perform(post(REST_URL)
                .contentType(APPLICATION_JSON)
                .content(writeValue(newStudent)))
                .andDo(print());

        Student created = readFromJson(action, Student.class);
        int newId = created.id();
        newStudent.setId(newId);
        STUDENT_MATCHER.assertMatch(created, newStudent);
        STUDENT_MATCHER.assertMatch(repository.findById(newId).orElse(null), newStudent);
    }

    @Test
    void createInvalid() throws Exception {
        Student studentWithBadField = new Student();
        studentWithBadField.setFirstName("");
        studentWithBadField.setLastName("");
        studentWithBadField.setPatronymic("");

        perform(post(REST_URL)
                .contentType(APPLICATION_JSON)
                .content(writeValue(studentWithBadField)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void update() throws Exception {
        Student updated = getUpdated();
        perform(put(REST_URL + HARRY_ID).contentType(APPLICATION_JSON)
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        STUDENT_MATCHER.assertMatch(repository.findById(HARRY_ID).orElse(null), updated);
    }

    @Test
    void updateInvalid() throws Exception {
        Student updatedWithBadFields = new Student();
        updatedWithBadFields.setId(HARRY_ID);
        updatedWithBadFields.setFirstName("");
        updatedWithBadFields.setLastName("");
        updatedWithBadFields.setPatronymic("");

        perform(put(REST_URL + HARRY_ID).contentType(APPLICATION_JSON)
                .content(writeValue(updatedWithBadFields)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void updateHtmlUnsafe() throws Exception {
        Student updated = new Student(HARRY);
        updated.setFirstName("<script>alert(123)</script>");

        perform(put(REST_URL + HARRY_ID)
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void deleteGood() throws Exception {
        perform(delete(REST_URL + HARRY_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        System.out.println(repository.findById(HARRY_ID));

        assertNull(repository.findById(HARRY_ID).orElse(null));
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
        perform(get(REST_URL + HARRY_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(STUDENT_MATCHER.contentJson(HARRY));
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
                .andExpect(STUDENT_MATCHER.contentJson(students));
    }
}