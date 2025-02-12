package com.Airplane.AirplaneApp.Exception;

public class ConcurrentBookingException extends Throwable {
    public ConcurrentBookingException(String message) {
        super(message);
    }
}
