package ru.alfabank.alfabattle.task1.websocket;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import ru.alfabank.alfabattle.task1.websocket.model.WebSocketRequest;
import ru.alfabank.alfabattle.task1.websocket.model.WebSocketResponse;


@Slf4j
@Controller
public class AtmWebsocketController {

    private Map<Integer, WebSocketResponse> data;


    @PostConstruct
    void init() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data.json");
        String dataJson = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines().collect(Collectors.joining("\n"));

        ObjectMapper objectMapper = new ObjectMapper();
        List<WebSocketResponse> dataList = objectMapper.readValue(
                dataJson, new TypeReference<List<WebSocketResponse>>(){});

        data = dataList.stream()
                .collect(Collectors.toMap(WebSocketResponse::getDeviceId, d -> d));
        log.info("{} entries loaded", data.size());
    }


    @MessageMapping("/")
    @SendTo("/topic/afik")
    public WebSocketResponse greeting(WebSocketRequest request) throws Exception {
        log.info("Got {}", request);
        return data.get(request.getDeviceId());
    }

}
