package com.example.pms.exceptions;

public class PlantNotFoundException extends Exception{
    private String message;

    public PlantNotFoundException(String message) {
        super(message);
    }
}
