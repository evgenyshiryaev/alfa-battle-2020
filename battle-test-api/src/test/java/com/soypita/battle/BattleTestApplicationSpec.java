package com.soypita.battle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soypita.battle.repository.ContestUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(
        classes = {BattleTestApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BattleTestApplicationSpec {
    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public ObjectMapper objectMapper;

    @Autowired
    public ContestUsersRepository contestUsersRepository;

    public static final String BASE_LOGIN_ID = "login_id";
    public static final String BASE_LOGIN_ID_2 = "login_id_2";
    public static final String BASE_LOGIN_ID_3 = "login_id_3";
    public static final String BASE_LOGIN_ID_4 = "login_id_4";
    public static final String UNKNOWN_LOGIN_ID = "unknown";
    public static final String BASE_USER_HOST = "127.0.0.1";
    public static final Integer TASK_ID = 1;
    public static final Integer UNKNOWN_TASK = -1;
    public static final Float RESULT = 80f;
}
