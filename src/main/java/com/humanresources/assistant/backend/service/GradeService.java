package com.humanresources.assistant.backend.service;

import static com.google.common.collect.Lists.newArrayList;

import com.humanresources.assistant.backend.converters.GradeConverters;
import com.humanresources.assistant.backend.dto.GradeDto;
import com.humanresources.assistant.backend.repository.GradeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GradeService {

    @Autowired
    GradeRepository gradeRepository;

    @Autowired
    GradeConverters gradeConverters;

    public List<GradeDto> getAllGrades() {
        return
            gradeConverters.convertGradeEntityListToDto.apply(newArrayList(gradeRepository.findAll()));
    }

    public void saveGrade(GradeDto grade) {
        gradeRepository.save(gradeConverters.convertGradeDtoToEntity.apply(grade));
    }

    public void deleteGrade(Integer id) {
        gradeRepository.deleteById(id);
    }
}
