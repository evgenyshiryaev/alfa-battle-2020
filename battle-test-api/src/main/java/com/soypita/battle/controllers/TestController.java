package com.soypita.battle.controllers;

import com.soypita.battle.dto.RunTestRequest;
import com.soypita.battle.dto.TestResultResponse;
import com.soypita.battle.services.TestRunnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final TestRunnerService testRunnerService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TestResultResponse processTest(@Valid @RequestBody RunTestRequest req) {
        return testRunnerService.executeTest(req);
    }
}
