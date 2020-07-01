package com.soypita.battle.services;

import com.soypita.battle.config.TasksProperties;
import com.soypita.battle.converters.TaskFailureConverter;
import com.soypita.battle.dto.RunTestRequest;
import com.soypita.battle.dto.TestResultResponse;
import com.soypita.battle.exceptions.TaskNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;


@Slf4j
@Service
@RequiredArgsConstructor
public class TestRunnerService {

    private static final String HOST_TEST_PARAM = "test_host";

    private final TasksProperties properties;
    private final TaskFailureConverter taskFailureConverter;
    private final UsersService usersService;

    @Value("${coefficient}")
    private Float totalCoeff;


    @SneakyThrows
    public TestResultResponse executeTest(RunTestRequest req) {
        var taskId = req.getTaskId();
        var loginId = req.getLoginId();

        log.info("Run test {} for user {} ", taskId, loginId);

        var summary = getExecutionSummary(taskId, loginId);
        var result = (totalCoeff / summary.getTestsFoundCount()) * summary.getTestsSucceededCount();

        log.info("Finish test {} for user {} with result {} ", taskId, loginId, result);

        usersService.updateUserResults(loginId, taskId, result);

        var taskFailureList = summary.getFailures()
                .stream()
                .map(taskFailureConverter::convert)
                .collect(Collectors.toList());

        log.info("List of failed test cases for taskID {} for user {} : {}", taskId, loginId, taskFailureList);

        return TestResultResponse
                .builder()
                .result(result)
                .totalTestsCount(summary.getTestsFoundCount())
                .failedTestsCount(summary.getTestsFailedCount())
                .succeededTestsCount(summary.getTestsSucceededCount())
                .failuresList(taskFailureList)
                .build();
    }


    private TestExecutionSummary getExecutionSummary(int taskId, String loginId) {
        var testClassName = properties.getTaskNames().get(taskId);

        if (Strings.isBlank(testClassName)) {
            log.error("Test case for taskID {} doesn't exist", taskId);
            throw new TaskNotFoundException(String.format("task with id %s not found", taskId));
        }

        var userHost = usersService.getUserHost(loginId);
        var testUrl = String.format("http://%s:%s", userHost, properties.getTaskPorts().get(taskId));

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
                .request()
                .selectors(selectClass(testClassName))
                .configurationParameter(HOST_TEST_PARAM, testUrl)
                .build();

        var launcher = LauncherFactory.create();
        var listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);

        launcher.execute(request);
        return listener.getSummary();
    }

}
