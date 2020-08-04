package com.brunober.slackspringdocker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "issue not found")
public class IssueNotFoundException extends RuntimeException {
}
