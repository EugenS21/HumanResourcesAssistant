package com.humanresources.assistant.backend.converters;

import com.humanresources.assistant.backend.dto.LeaveDto;
import com.humanresources.assistant.backend.entity.LeaveEntity;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LeaveConverters extends Generic<LeaveEntity, LeaveDto> {

    @Autowired
    UserConverters userConverters;

    public Function<List<LeaveEntity>, List<LeaveDto>> convertLeaveEntityListToDto = convertListEntitiesToDto();
    public Function<List<LeaveDto>, List<LeaveEntity>> convertLeaveDtoListToEntity = convertListDtoToEntities();
    public Function<LeaveEntity, LeaveDto> convertLeaveEntityToDto = convertEntityToDto();
    public Function<LeaveDto, LeaveEntity> convertLeaveDtoToEntity = convertDtoToEntity();

    @Override
    protected Function<LeaveEntity, LeaveDto> convertEntityToDto() {
        return leave -> LeaveDto.builder()
            .id(leave.getId())
            .approvedBy(leave.getApprovedBy())
            .description(leave.getDescription())
            .approvedDate(leave.getApprovedDate())
            .startDate(leave.getStartDate())
            .endDate(leave.getEndDate())
            .createdBy(leave.getCreatedBy())
            .creationDate(leave.getCreationDate())
            .isApproved(leave.getIsApproved())
            .name(leave.getLastName())
            .surName(leave.getFirstName())
            .leaveType(leave.getLeaveType())
            .user(userConverters.convertUserEntityToDto.apply(leave.getUser()))
            .build();
    }

    @Override
    protected Function<LeaveDto, LeaveEntity> convertDtoToEntity() {
        return leave -> LeaveEntity.builder()
            .approvedBy(leave.getApprovedBy())
            .firstName(leave.getSurName())
            .lastName(leave.getName())
            .approvedDate(leave.getApprovedDate())
            .description(leave.getDescription())
            .createdBy(leave.getCreatedBy())
            .startDate(leave.getStartDate())
            .endDate(leave.getEndDate())
            .creationDate(leave.getCreationDate())
            .isApproved(leave.getIsApproved())
            .leaveType(leave.getLeaveType())
            .user(userConverters.convertUserDtoToEntity.apply(leave.getUser()))
            .build();
    }

    @Override
    protected Function<List<LeaveEntity>, List<LeaveDto>> convertListEntitiesToDto() {
        return leaves -> leaves.stream()
            .map(leave -> convertEntityToDto().apply(leave))
            .collect(Collectors.toList());
    }

    @Override
    protected Function<List<LeaveDto>, List<LeaveEntity>> convertListDtoToEntities() {
        return leaves -> leaves.stream()
            .map(leave -> convertDtoToEntity().apply(leave))
            .collect(Collectors.toList());
    }

}
