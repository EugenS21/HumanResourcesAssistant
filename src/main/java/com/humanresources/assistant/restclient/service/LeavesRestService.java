package com.humanresources.assistant.restclient.service;

import com.humanresources.assistant.backend.dto.LeaveDto;
import com.humanresources.assistant.restclient.CommonService;
import org.springframework.stereotype.Service;

@Service
public class LeavesRestService extends CommonService<LeaveDto> {

    public static final String LEAVE = "leave";
    public static final String LEAVE_BY_ID = "leave_by_id";

    @Override
    public String getURI() {
        return LEAVE;
    }

    @Override
    public String getById() {
        return LEAVE_BY_ID;
    }

    @Override
    public Class<LeaveDto> getResponseClass() {
        return LeaveDto.class;
    }
}
