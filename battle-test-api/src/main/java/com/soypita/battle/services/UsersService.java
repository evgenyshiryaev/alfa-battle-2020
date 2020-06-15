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
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final ContestUsersRepository contestUsersRepository;
    private final UserResultConverter userResultConverter;
    private final ContestUserConverter contestUserConverter;

    @SneakyThrows
    public String getUserHost(String loginId) {
        return getUser(loginId).getHost();
    }

    @SneakyThrows
    public void updateUserResults(String loginId, int taskId, float result) {
        var user = getUser(loginId);

        var currentResults = user.getTestResults();

        if (currentResults == null) {
            currentResults = new HashMap<>();
        }
        currentResults.put(taskId, result);

        contestUsersRepository.save(user);
    }

    public List<UserResult> getAllResults() {
        var results = contestUsersRepository.findAll();
        return results.stream().map(userResultConverter::convert).collect(Collectors.toList());
    }

    @SneakyThrows
    public UserResult getUserResults(String loginId) {
        return userResultConverter.convert(getUser(loginId));
    }

    public void addUsers(List<AddUserRequest> request) {
        var contestUsers = request
                .stream()
                .map(contestUserConverter::convert)
                .collect(Collectors.toList());
        contestUsersRepository.saveAll(contestUsers);
    }

    public void deleteAllUsers() {
        contestUsersRepository.deleteAll();
    }

    public void deleteUser(String loginId) {
        contestUsersRepository.deleteByLoginId(loginId);
    }

    private ContestUser getUser(String loginId) {
        var userOpt = contestUsersRepository.findByLoginId(loginId);
        if (userOpt.isEmpty()) {
            throw new UserNotFoundException(String.format("user with login %s not found in repository", loginId));
        }
        return userOpt.get();
    }
}
