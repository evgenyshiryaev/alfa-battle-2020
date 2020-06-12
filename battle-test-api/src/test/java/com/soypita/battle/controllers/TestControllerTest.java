package com.soypita.battle.controllers;

import com.soypita.battle.BattleTestApplicationSpec;
import com.soypita.battle.dto.RunTestRequest;
import com.soypita.battle.entities.ContestUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class TestControllerTest extends BattleTestApplicationSpec {
    @Before
    public void init() {
        // init user repository
        var user = new ContestUser();
        user.setLoginId(BASE_LOGIN_ID);
        user.setHost(BASE_USER_HOST);
        contestUsersRepository.save(user);
    }

    @After
    public void cleanup() {
        contestUsersRepository.deleteAll();
    }

    @Test
    public void successfullyPerformTestCase() throws Exception {
        // given
        RunTestRequest req = RunTestRequest.builder()
                .loginId(BASE_LOGIN_ID)
                .taskId(TASK_ID)
                .build();

        // expect
        mockMvc.perform(post("/test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(req)))
                .andExpect(status().isOk());

        var user = contestUsersRepository.findByLoginId(BASE_LOGIN_ID);
        assertFalse(user.isEmpty());
    }

    @Test
    public void failedWhenRequestUserNotFound() throws Exception {
        // given
        RunTestRequest req = RunTestRequest.builder()
                .loginId(UNKNOWN_LOGIN_ID)
                .taskId(TASK_ID)
                .build();

        // expect
        mockMvc.perform(post("/test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void failedWhenRequestTaskNotFound() throws Exception {
        // given
        RunTestRequest req = RunTestRequest.builder()
                .loginId(BASE_LOGIN_ID)
                .taskId(UNKNOWN_TASK)
                .build();

        // expect
        mockMvc.perform(post("/test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void failedWhenRequestLoginIdIsEmpty() throws Exception {
        // given
        RunTestRequest req = RunTestRequest.builder()
                .taskId(UNKNOWN_TASK)
                .build();

        // expect
        mockMvc.perform(post("/test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void failedWhenRequestTaskIdIsEmpty() throws Exception {
        // given
        RunTestRequest req = RunTestRequest.builder()
                .loginId(BASE_LOGIN_ID)
                .build();

        // expect
        mockMvc.perform(post("/test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(req)))
                .andExpect(status().isBadRequest());
    }
}
