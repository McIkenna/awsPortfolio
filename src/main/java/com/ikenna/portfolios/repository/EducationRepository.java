package com.ikenna.portfolios.repository;


import com.ikenna.portfolios.infos.Education;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface EducationRepository {

    Education findByEduId(String eduId);
    Education save(MultipartFile multipartFile, Education education);
    String deleteEdu(String eduId);
    String updateEdu(MultipartFile multipartFile, Education education);

    Iterable<Education>findAll();

}
