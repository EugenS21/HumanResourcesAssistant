package com.humanresources.assistant.backend.model;

import com.humanresources.assistant.backend.enums.RequestType;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Request {

    private final String firstName;
    private final String lastName;
    private final LocalDate firstDate;
    private final LocalDate lastDate;
    private final RequestType requestType;
}
