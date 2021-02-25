package edu.volkov.progressjournal.web;

import edu.volkov.progressjournal.View;
import edu.volkov.progressjournal.model.JournalEntry;
import edu.volkov.progressjournal.repository.JournalEntryRepository;
import edu.volkov.progressjournal.to.JournalEntryTo;
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
@RequestMapping(value = JournalEntryController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class JournalEntryController {

    static final String REST_URL = "/psjournal/entryes";
    private final JournalEntryRepository repository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JournalEntry> create(@Validated(View.Web.class) @RequestBody JournalEntryTo journalEntryTo) {
        log.info("create {}", journalEntryTo);
        checkNew(journalEntryTo);
        JournalEntry created = repository.save(journalEntryTo);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody JournalEntryTo journalEntryTo, @PathVariable int id) {
        log.info("update {} with id {}", journalEntryTo, id);
        assureIdConsistent(journalEntryTo, id);
        checkNotFoundWithId(repository.save(journalEntryTo), journalEntryTo.id());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete journalEntryes with id={}", id);
        checkNotFoundWithId(repository.delete(id), id);
    }

    @GetMapping("/{id}")
    public JournalEntry get(@PathVariable int id) {
        log.info("get journalEntrye with id={}", id);
        return checkNotFoundWithId(repository.get(id), id);
    }

    @GetMapping
    public List<JournalEntry> getAll() {
        log.info("getAll journalEntryes");
        return repository.getAll();
    }
}
