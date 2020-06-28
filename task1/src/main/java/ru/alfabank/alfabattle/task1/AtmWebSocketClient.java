package ru.alfabank.alfabattle.task1;

import java.lang.reflect.Type;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import lombok.extern.slf4j.Slf4j;
import ru.alfabank.alfabattle.task1.modeltask.WebSocketRequest;
import ru.alfabank.alfabattle.task1.modeltask.WebSocketResponse;


@Slf4j
@Service
public class AtmWebSocketClient implements StompSessionHandler {

    @Autowired
    private Task1Properties properties;


    private WebSocketStompClient stompClient;

    private StompSession session;

    private final BlockingQueue<Integer> receivedAlfik = new ArrayBlockingQueue<>(1);


    @PostConstruct
    void init() {
        log.info("Connecting WebSocket {}...", properties.getWebSocketPath());

        WebSocketClient client = new StandardWebSocketClient();

        stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        ListenableFuture<StompSession> future = stompClient.connect(properties.getWebSocketPath(), this);
        try {
            future.get();
            log.info("WebSocket is connected");
        } catch (Exception e) {
            log.error("Cannot connect to WebSocket");
        }
    }


    @PreDestroy
    void destroy() {
        if (stompClient != null) {
            stompClient.stop();
        }
    }

    
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        log.info("WebSocket is connected for {}", session);
        this.session = session;
        session.subscribe("/topic/alfik", this);
    }


    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        log.info("Got frame {}", payload);
        receivedAlfik.add(((WebSocketResponse)payload).getAlfik());
    }


    @Override
    public Type getPayloadType(StompHeaders headers) {
        return WebSocketResponse.class;
    }


    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload,
            Throwable exception) {
        log.error("Got exception for " + session, exception);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        log.error("Got transport error for " + session, exception);
    }


    public int getAtmAlfik(int deviceId) {
        session.send("/", new WebSocketRequest(deviceId));
        try {
            return receivedAlfik.take();
        } catch (InterruptedException e) {
            // no way
            return Integer.MAX_VALUE;
        }
    }

}
