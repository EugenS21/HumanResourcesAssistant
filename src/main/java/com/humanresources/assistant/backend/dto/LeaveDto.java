package com.humanresources.assistant.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.humanresources.assistant.backend.enums.LeaveTypeEnum;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class LeaveDto {

    @JsonProperty ("id")
    private Long id;

    @JsonProperty ("name")
    private String name;

    @JsonProperty ("surName")
    private String surName;

    @JsonProperty ("leaveType")
    private LeaveTypeEnum leaveType;

    @JsonProperty ("startDate")
    private LocalDate startDate;

    @JsonProperty ("endDate")
    private LocalDate endDate;

    @JsonProperty ("description")
    private String description;

    @JsonProperty ("creationDate")
    private LocalDate creationDate;

    @JsonProperty ("createdBy")
    private String createdBy;

    @JsonProperty ("approvedDate")
    private LocalDate approvedDate;

    @JsonProperty ("approvedBy")
    private String approvedBy;

    @JsonProperty ("isApproved")
    private Boolean isApproved;

    @JsonProperty ("employees")
    private UserDto user;

}
