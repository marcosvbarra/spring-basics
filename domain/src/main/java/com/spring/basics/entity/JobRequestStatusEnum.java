package com.spring.basics.entity;

public enum JobRequestStatusEnum {
    PENDING("Sent"),
    FAILED("Failed"),
    FAILED_ON_RETRY("Failed on retry"),
    WORKER_NOT_FOUND("Worker not found"),
    WAITING_WORKERS("Waiting workers"),
    FINISHED("Finished");

    JobRequestStatusEnum(String description) {
    }
}
