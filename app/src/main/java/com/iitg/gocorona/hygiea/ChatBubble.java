package com.iitg.gocorona.hygiea;

public class ChatBubble {
    private boolean myMessage;
    private String content;
    public ChatBubble(String content, boolean myMessage) {
        this.content = content;
        this.myMessage = myMessage;
    }

    public String getContent() {
        return content;
    }

    public boolean myMessage() {
        return myMessage;
    }
}
