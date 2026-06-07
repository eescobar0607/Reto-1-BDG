package com.cun.reto1.models;

public class UpdateUserRequest {

    private final String name;
    private final String job;

    public UpdateUserRequest(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }
}
