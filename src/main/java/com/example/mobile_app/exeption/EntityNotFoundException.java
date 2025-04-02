package com.example.mobile_app.exeption;

    public class NewsLineNotFoundException extends RuntimeException {
    public NewsLineNotFoundException(String message) {
        super(message);
    }
}