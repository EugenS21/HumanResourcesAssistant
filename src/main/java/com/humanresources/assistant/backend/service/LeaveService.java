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
    public LeaveDto updateLeave(Long id, LeaveDto leaveDto) {
        final LeaveEntity foundLeaveEntity = leaveRepository.findById(id).orElseThrow(LeaveNotFound::new);
        leaveRepository.delete(foundLeaveEntity);
        leaveRepository.save(leaveConverters.convertLeaveDtoToEntity.apply(leaveDto));
        return leaveDto;
    }

    public List<LeaveDto> getAllLeavesByUserId(Long id) {
        return leaveConverters.convertLeaveEntityListToDto.apply(leaveRepository.findAllByUserId(id));
    }

    public void deleteLeave(Long id) {
        leaveRepository.deleteById(id);
    }
}
