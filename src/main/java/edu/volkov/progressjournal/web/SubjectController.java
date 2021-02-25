package edu.volkov.progressjournal.web;

import edu.volkov.progressjournal.View;
import edu.volkov.progressjournal.model.Subject;
import edu.volkov.progressjournal.repository.SubjectCrudRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static edu.volkov.progressjournal.util.ValidationUtil.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = SubjectController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class SubjectController {

    static final String REST_URL = "/psjournal/subjects";
    private final SubjectCrudRepository repository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Subject> create(@Validated(View.Web.class) @RequestBody Subject subject) {
        log.info("create {}", subject);
        checkNew(subject);
        Subject created = repository.save(subject);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody Subject subject, @PathVariable int id) {
        log.info("update {} with id {}", subject, id);
        assureIdConsistent(subject, id);
        checkNotFoundWithId(repository.save(subject), subject.id());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete subject with id={}", id);
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    @GetMapping("/{id}")
    public Subject get(@PathVariable int id) {
        log.info("get student with id={}", id);
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    @GetMapping
    public List<Subject> getAll() {
        log.info("getAll students");
        return repository.findAll();
    }
}
