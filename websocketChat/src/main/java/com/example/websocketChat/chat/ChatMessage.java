package com.example.websocketChat.chat;


public class ChatMessage {
    private String content;
    private String sender;
    private MessageType type;
    private String roomId;

    // constructors, getters, setters



    public ChatMessage() {
    }



    public ChatMessage(String content, String sender, MessageType type,String roomId) {
        this.content = content;
        this.sender = sender;
        this.type = type;
        this.roomId=roomId;
    }


    // Getters
    public String getContent() {
        return content;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getSender() {
        return sender;
    }

    public MessageType getType() {
        return type;
    }

    // Setters
    public void setContent(String content) {
        this.content = content;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    // Manual Builder class
    public static class Builder {
        private String content;
        private String sender;
        private MessageType type;
        private  String roomId;

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder sender(String sender) {
            this.sender = sender;
            return this;
        }

        public Builder type(MessageType type) {
            this.type = type;
            return this;
        }
        public Builder roomId(String roomId) {
            this.roomId = roomId;
            return this;
        }


        public ChatMessage build() {
            return new ChatMessage(content, sender, type, roomId);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}

