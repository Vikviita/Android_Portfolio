package com.vikvita.javaprojext;

/** Class which contains info about each message*/


public class Message {
    private String message;
    private LR side;

    public Message(String message, LR side) {
        setMessage(message);
        setSide(side);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LR getSide() {
        return side;
    }

    public void setSide(LR side) {
        this.side = side;
    }
}
