package com.ikenna.portfolios.components;

import com.ikenna.portfolios.infos.Education;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface IEducationService {

    public Education saveOrUpdateEducation(MultipartFile file, Education education);
    public Education findEducationBySchoolName(String schoolName);
}
