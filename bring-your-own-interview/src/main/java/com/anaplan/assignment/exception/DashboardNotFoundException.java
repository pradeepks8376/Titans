package com.anaplan.assignment.exception;

public class DashboardNotFoundException extends RuntimeException {
    public DashboardNotFoundException(String message) {
        super(message);
    }
}
