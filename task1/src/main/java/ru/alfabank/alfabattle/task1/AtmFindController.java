package ru.alfabank.alfabattle.task1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController("/")
public class AtmFindController {

    @GetMapping
    public String getNearestAtm() {
        return "go to hell";
    }

}
