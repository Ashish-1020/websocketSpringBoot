package com.example.websocketChat.chat;


import com.example.websocketChat.WebSocketEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               @DestinationVariable String roomId,
                               SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        headerAccessor.getSessionAttributes().put("roomId", roomId);
        chatMessage.setType(MessageType.JOIN);
        chatMessage.setContent("User added");
        log.info("User added to: {}",  chatMessage.getContent());
        log.info(" from :{}",chatMessage.getSender());
        log.info(" to roomid: {}",chatMessage.getRoomId());
        chatMessage.setRoomId(roomId);

        return chatMessage;
    }

    // Add room-specific message sending
    @MessageMapping("/chat.sendMessage/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public ChatMessage sendRoomMessage(@Payload ChatMessage chatMessage,
                                       @DestinationVariable String roomId) {
        chatMessage.setRoomId(roomId);
        chatMessage.setType(MessageType.CHAT);
        log.info("User sent message to: {}",  chatMessage.getContent());
        log.info(" from :{}",chatMessage.getSender());
        log.info(" to roomid: {}",chatMessage.getRoomId());
        return chatMessage;
    }
}
