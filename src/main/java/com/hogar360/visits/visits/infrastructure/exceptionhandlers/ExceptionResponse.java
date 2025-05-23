package com.hogar360.visits.visits.infrastructure.exceptionhandlers;

import java.time.LocalDateTime;

public record ExceptionResponse(String message, LocalDateTime timeStamp) {
}
