package com.soypita.battle.controllers;

import com.soypita.battle.dto.AddUserRequest;
import com.soypita.battle.dto.UserResult;
import com.soypita.battle.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;

    @GetMapping(value = "/results", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserResult> getAllResults() {
        return usersService.getAllResults();
    }

    @GetMapping(value = "/results/{loginId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResult getUserResults(@NotNull @PathVariable String loginId) {
        return usersService.getUserResults(loginId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void addUsers(@Valid @RequestBody List<AddUserRequest> request) {
        usersService.addUsers(request);
    }

//    @DeleteMapping
//    public void deleteAllUsers() {
//        usersService.deleteAllUsers();
//    }

    @DeleteMapping("/{loginId}")
    public void deleteUser(@NotNull @PathVariable String loginId) {
        usersService.deleteUser(loginId);
    }
}
