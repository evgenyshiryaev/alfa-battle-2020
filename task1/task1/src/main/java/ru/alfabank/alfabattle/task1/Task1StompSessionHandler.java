package ru.alfabank.alfabattle.task1;

import java.lang.reflect.Type;
import java.math.BigDecimal;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import lombok.extern.slf4j.Slf4j;
import ru.alfabank.alfabattle.task1.modeltask.WebSocketRequest;
import ru.alfabank.alfabattle.task1.modeltask.WebSocketResponse;


@Slf4j
public class Task1StompSessionHandler implements StompSessionHandler {

    private StompSession session;


    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        log.info("WebSocket is connected for {}", session);
        this.session = session;
        session.subscribe("/topic/messages", this);
    }


    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        log.info("Got frame {}", payload);
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


    public BigDecimal getAtmAlfik(int deviceId) {
        session.send("/app/hello", new WebSocketRequest(deviceId));
        // TODO: wait
        return null;
    }

}
