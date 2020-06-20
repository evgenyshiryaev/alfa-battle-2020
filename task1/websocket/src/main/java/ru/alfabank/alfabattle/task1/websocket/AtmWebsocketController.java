package ru.alfabank.alfabattle.task1.websocket;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class AtmWebsocketController {

    private Map<Integer, WebSocketResponse> data;


    @PostConstruct
    void init() throws Exception {
        File dataFile = ResourceUtils.getFile("classpath:data.json");
        String dataJson = Files.readString(dataFile.toPath());

        ObjectMapper objectMapper = new ObjectMapper();
        List<WebSocketResponse> dataList = objectMapper.readValue(
                dataJson, new TypeReference<List<WebSocketResponse>>(){});

        data = dataList.stream()
                .collect(Collectors.toMap(WebSocketResponse::getDeviceId, d -> d));
    }


    @MessageMapping("/hello")
    @SendTo("/topic/messages")
    public WebSocketResponse greeting(WebSocketRequest request) throws Exception {
        log.info("Got {}", request);
        return data.get(request.getDeviceId());
    }

}
