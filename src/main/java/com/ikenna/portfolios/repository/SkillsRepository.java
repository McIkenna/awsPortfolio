package com.ikenna.portfolios.repository;

import com.ikenna.portfolios.infos.Skill;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface SkillsRepository {

    Skill findBySkillId(String skillId);
    Skill save(MultipartFile multipartFile, Skill skill);
    String deleteSkill(String skillId);
    String updateSkill(MultipartFile multipartFile, Skill skill);


    Iterable<Skill> findAll();

}
