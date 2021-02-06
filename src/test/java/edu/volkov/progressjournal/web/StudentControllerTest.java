package edu.volkov.progressjournal.web;

import edu.volkov.progressjournal.repository.StudentCrudRepository;
import edu.volkov.progressjournal.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class StudentControllerTest extends AbstractControllerTest {

    private static final String REST_URL = StudentController.REST_URL + '/';

    private StudentCrudRepository repository;

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
        perform(MockMvcRequestBuilders.delete(REST_URL + HARRY_ID))
                .andExpect(status().isNoContent());

        assertThrows(NotFoundException.class, () -> repository.findById(HARRY_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND_ID))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + HARRY_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(STUDENT_MATCHER.contentJson(HARRY));
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND_ID))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getAll() {
    }
}