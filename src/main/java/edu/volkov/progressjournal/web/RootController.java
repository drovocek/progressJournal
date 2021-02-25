package edu.volkov.progressjournal.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/")
@Slf4j
public class RootController {

    @GetMapping("")
    public String root() {
        log.info("root()");
        return "redirect:/entryes";
    }

    @GetMapping("/entryes")
    public String getEntryesView() {
        log.info("getEntryesView()");
        return "entryes";
    }

    @GetMapping("/students")
    public String getStudentsView() {
        log.info("getStudentsView()");
        return "students";
    }

    @GetMapping("/subjects")
    public String getSubjectsView() {
        log.info("getSubjectsView()");
        return "subjects";
    }

    @GetMapping("/statistic")
    public String getStatisticView() {
        log.info("getStatisticView()");
        return "statistic";
    }
}
