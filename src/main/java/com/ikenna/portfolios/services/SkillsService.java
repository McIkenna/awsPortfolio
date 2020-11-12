package com.ikenna.portfolios.services;

import com.ikenna.portfolios.components.ISkillsService;
import com.ikenna.portfolios.exceptions.InfoException;
import com.ikenna.portfolios.exceptions.SkillException;
import com.ikenna.portfolios.infos.Skills;
import com.ikenna.portfolios.repository.SkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkillsService implements ISkillsService {

    @Autowired
    SkillsRepository skillsRepository;

    public Skills saveOrUpdateSkills(Skills skills) {
       try{
           skills.setSkillname(skills.getSkillname());
               skills = skillsRepository.save(skills);

           return skills;
       }catch(Exception e){
           throw new SkillException("The Skill, '" + skills.getSkillname().toUpperCase() + "' already exist");
       }
    }

    public Skills findBySkillName(String skillname){
        Skills skills = skillsRepository.findBySkillname(skillname.toUpperCase());

        if(skills == null){
            throw new InfoException("The user with phone number '" + skillname.toUpperCase() + "' does not exist");
        }
        return skills;
    }

    public Iterable<Skills> findAllSkills(){
        return skillsRepository.findAll();
    }

    public void deleteBySkillName(String skillname){
        Skills skill = skillsRepository.findBySkillname(skillname);
        if(skill == null){
            throw new InfoException("Cannot delete, '" + skillname + "' does not exist");
        }
        skillsRepository.delete(skill);
    }
}
