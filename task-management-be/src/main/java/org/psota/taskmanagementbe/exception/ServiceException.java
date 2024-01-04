package org.psota.taskmanagementbe.exception;

import lombok.experimental.SuperBuilder;

public class ServiceException extends Exception {
    public ServiceException(String message) {
        super(message);
    }
}
