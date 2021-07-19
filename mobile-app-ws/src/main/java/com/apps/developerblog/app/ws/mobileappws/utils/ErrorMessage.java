package com.apps.developerblog.app.ws.mobileappws.utils;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder(builderMethodName = "create")
public class ErrorMessage {
    private LocalDateTime timestamp;
    private String message;
}
