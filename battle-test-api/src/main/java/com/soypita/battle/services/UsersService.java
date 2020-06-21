package com.soypita.battle.services;

import com.soypita.battle.converters.ContestUserConverter;
import com.soypita.battle.converters.UserResultConverter;
import com.soypita.battle.dto.AddUserRequest;
import com.soypita.battle.dto.UserResult;
import com.soypita.battle.entities.ContestUser;
import com.soypita.battle.exceptions.UserNotFoundException;
import com.soypita.battle.repository.ContestUsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {

    private final ContestUsersRepository contestUsersRepository;
    private final UserResultConverter userResultConverter;
    private final ContestUserConverter contestUserConverter;


    @SneakyThrows
    public String getUserHost(String loginId) {
        log.info("Get user host for user {}", loginId);
        return getUser(loginId).getHost();
    }


    @SneakyThrows
    public void updateUserResults(String loginId, int taskId, float result) {
        log.info("Start to update results for user {} for taskID {} : {}", loginId, taskId, result);
        var user = getUser(loginId);

        var currentResults = user.getTestResults();

        if (currentResults == null) {
            currentResults = new HashMap<>();
            user.setTestResults(currentResults);
        }

        currentResults.put(taskId, result);

        contestUsersRepository.save(user);
    }


    public List<UserResult> getAllResults() {
        log.info("Get all users results");
        var results = contestUsersRepository.findAll();
        return results.stream().map(userResultConverter::convert).collect(Collectors.toList());
    }


    @SneakyThrows
    public UserResult getUserResults(String loginId) {
        log.info("Get results for user users {}", loginId);
        return userResultConverter.convert(getUser(loginId));
    }


    public void addUsers(List<AddUserRequest> request) {
        log.info("Add new users {}", request);
        var contestUsers = request
                .stream()
                .map(contestUserConverter::convert)
                .collect(Collectors.toList());
        contestUsersRepository.saveAll(contestUsers);
    }


    public void deleteAllUsers() {
        log.info("Delete all users");
        contestUsersRepository.deleteAll();
    }


    public void deleteUser(String loginId) {
        log.info("Delete user {}", loginId);
        contestUsersRepository.deleteByLoginId(loginId);
    }


    private ContestUser getUser(String loginId) {
        log.info("Get user info {}", loginId);
        var userOpt = contestUsersRepository.findByLoginId(loginId);
        if (userOpt.isEmpty()) {
            throw new UserNotFoundException(String.format("user with login %s not found in repository", loginId));
        }
        return userOpt.get();
    }

}
