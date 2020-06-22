package com.soypita.battle.services;

import com.soypita.battle.config.TasksProperties;
import com.soypita.battle.converters.TaskFailureConverter;
import com.soypita.battle.dto.RunTestRequest;
import com.soypita.battle.dto.TestResultResponse;
import com.soypita.battle.entities.ContestUser;
import com.soypita.battle.exceptions.TaskNotFoundException;
import com.soypita.battle.exceptions.UserNotFoundException;
import com.soypita.battle.repository.ContestUsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestRunnerService {
    private final Launcher launcher;
    private final SummaryGeneratingListener listener;
    private final TasksProperties properties;
    private final TaskFailureConverter taskFailureConverter;
    private final UsersService usersService;

    @Value("${coefficient}")
    private Float totalCoeff;

    private static final String HOST_TEST_PARAM = "test_host";

    @SneakyThrows
    public TestResultResponse executeTest(RunTestRequest req) {
        var taskId = req.getTaskId();
        var loginId = req.getLoginId();

        log.info("Run test {} for user {} ", taskId, loginId);

        var testClassName = properties.getTaskNames().get(taskId);

        if (Strings.isBlank(testClassName)) {
            log.error("Test case for taskID {} doesn't exist", taskId);
            throw new TaskNotFoundException(String.format("task with id %s not found", taskId));
        }

        var userHost = usersService.getUserHost(loginId);

        var testUrl = String.format("http://%s:%s", userHost, properties.getTaskPorts().get(req.getTaskId()));

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
                .request()
                .selectors(selectClass(testClassName))
                .configurationParameter(HOST_TEST_PARAM, testUrl)
                .build();

        launcher.execute(request);

        var summary = listener.getSummary();


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
}
