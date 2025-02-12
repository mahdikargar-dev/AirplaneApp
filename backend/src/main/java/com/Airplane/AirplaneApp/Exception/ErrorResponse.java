package com.Airplane.AirplaneApp.Exception;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private String details;
    private Integer statusCode;
    private String timestamp;

    public ErrorResponse(String message, String details, Integer statusCode) {
        this.message = message;
        this.details = details;
        this.statusCode = statusCode;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }
}