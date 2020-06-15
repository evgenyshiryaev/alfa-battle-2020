package com.soypita.battle.converters;

import com.soypita.battle.dto.TaskFailure;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskFailureConverter {
    @Mapping(source = "testIdentifier.displayName", target = "taskName")
    @Mapping(source = "exception.message", target = "failureMessage")
    TaskFailure convert(TestExecutionSummary.Failure failure);
}
