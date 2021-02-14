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

    @GetMapping("/psjournal")
    public String getUsers() {
        log.info("getUsers()");
        return "entryes";
    }
}
