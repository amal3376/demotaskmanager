package com.example.demotaskmanager.dtos;

public class ErrorResponse {
    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;


}
