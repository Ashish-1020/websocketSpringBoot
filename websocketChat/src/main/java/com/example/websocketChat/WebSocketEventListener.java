package com.example.websocketChat;


import com.example.websocketChat.chat.ChatMessage;
import com.example.websocketChat.chat.MessageType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component

public class WebSocketEventListener {

    @Autowired
    private SimpMessageSendingOperations messageSendingOperations;


    private static final Logger log = LoggerFactory.getLogger(WebSocketEventListener.class);

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if (username != null ) {
            log.info("User disconnected: {}", username);

           var chatMessage=ChatMessage.builder()
                           .type(MessageType.LEAVE)
                           .sender(username)
                           .build();


            messageSendingOperations.convertAndSend("/topic/public", chatMessage);
        }
    }
}
