package com.develop.perlasoft.model;

public class ServerResponse {
    String message;

    public ServerResponse(String message) {
        this.message = message;
    }

    public ServerResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
