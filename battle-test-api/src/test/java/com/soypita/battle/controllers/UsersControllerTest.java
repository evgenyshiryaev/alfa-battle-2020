package com.soypita.battle.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.soypita.battle.BattleTestApplicationSpec;
import com.soypita.battle.dto.AddUserRequest;
import com.soypita.battle.dto.UserResult;
import com.soypita.battle.entities.ContestUser;
import org.apache.logging.log4j.util.Strings;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class UsersControllerTest extends BattleTestApplicationSpec {
    @Before
    public void init() {
        // init user repository
        var user1 = new ContestUser();
        user1.setLoginId(BASE_LOGIN_ID);
        user1.setHost(BASE_USER_HOST);
        user1.setTestResults(Map.of(TASK_ID, RESULT));

        var user2 = new ContestUser();
        user2.setLoginId(BASE_LOGIN_ID_2);
        user2.setHost(BASE_USER_HOST);
        user2.setTestResults(Map.of(TASK_ID, RESULT));

        contestUsersRepository.saveAll(List.of(user1, user2));
    }

    @After
    public void cleanup() {
        contestUsersRepository.deleteAll();
    }

    @Test
    public void successfullyGetAllResults() throws Exception {
        // when
        var response = mockMvc.perform(get("/users/results"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsByteArray();

        // then
        var userResults = objectMapper.readValue(response, new TypeReference<List<UserResult>>() {
        });
        assertFalse(userResults.isEmpty());
    }

    @Test
    public void successfullyGetResultsByLoginId() throws Exception {
        // when
        var response = mockMvc.perform(get("/users/results/" + BASE_LOGIN_ID))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsByteArray();

        // then
        var userResult = objectMapper.readValue(response, UserResult.class);
        assertNotNull(userResult);
        assertTrue(Strings.isNotBlank(userResult.getLoginId()));
    }

    @Test
    public void failedWhenLoginIdNotFound() throws Exception {
        mockMvc.perform(get("/users/results/" + UNKNOWN_LOGIN_ID))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void successfullyAddUsers() throws Exception {
        // given
        var reqUser1 = new AddUserRequest();
        reqUser1.setLoginId(BASE_LOGIN_ID_3);
        reqUser1.setHost(BASE_USER_HOST);

        var reqUser2 = new AddUserRequest();
        reqUser2.setLoginId(BASE_LOGIN_ID_4);
        reqUser2.setHost(BASE_USER_HOST);

        // expect
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(List.of(reqUser1, reqUser2))))
                .andExpect(status().isOk());
    }

    @Test
    public void successfullyDeleteUserByLogin() throws Exception {
        mockMvc.perform(delete("/users/" + BASE_LOGIN_ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        var res = contestUsersRepository.findByLoginId(BASE_LOGIN_ID);
        assertTrue(res.isEmpty());
    }

    @Test
    public void successfullyDeleteAllUsers() throws Exception {
        mockMvc.perform(delete("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }


}