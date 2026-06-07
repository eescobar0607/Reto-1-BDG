package com.cun.reto1.models;

public class RegisterUserWithoutPasswordRequest {

    private final String email;

    public RegisterUserWithoutPasswordRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
