package org.psota.taskmanagementbe.exception;

import lombok.Builder;

@Builder
public class ErrorResponse {
    private String message;
}
