package com.humanresources.assistant.backend.service;

import static com.google.common.collect.Lists.newArrayList;

import com.humanresources.assistant.backend.converters.LeaveConverters;
import com.humanresources.assistant.backend.dto.LeaveDto;
import com.humanresources.assistant.backend.entity.LeaveEntity;
import com.humanresources.assistant.backend.exceptions.LeaveNotFound;
import com.humanresources.assistant.backend.repository.LeaveRepository;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveService {

    @Autowired
    LeaveRepository leaveRepository;

    @Autowired
    LeaveConverters leaveConverters;

    public List<LeaveDto> getAllLeaves() {
        return
            leaveConverters.convertLeaveEntityListToDto.apply(newArrayList(leaveRepository.findAll()));
    }

    public void saveLeave(LeaveDto leave) {
        leaveRepository.save(leaveConverters.convertLeaveDtoToEntity.apply(leave));
    }

    @SneakyThrows
    public LeaveDto updateLeave(Integer id, LeaveDto leaveDto) {
        final LeaveEntity foundLeaveEntity = leaveRepository.findById(id).orElseThrow(LeaveNotFound::new);
        foundLeaveEntity.setLeaveType(leaveDto.getLeaveType());
        leaveRepository.save(foundLeaveEntity);
        return leaveConverters.convertLeaveEntityToDto.apply(foundLeaveEntity);
    }

    public void deleteLeave(Integer id) {
        leaveRepository.deleteById(id);
    }
}
