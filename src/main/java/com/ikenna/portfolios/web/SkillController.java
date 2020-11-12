package com.ikenna.portfolios.web;

import com.ikenna.portfolios.infos.Skills;
import com.ikenna.portfolios.services.MapErrorService;
import com.ikenna.portfolios.services.SkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    @Autowired
    SkillsService skillsService;

    @Autowired
    MapErrorService mapErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewInfo(@Valid @RequestBody Skills skills, BindingResult result){

        ResponseEntity<?> errorMap = mapErrorService.MapErrorService(result);
        if(errorMap != null) return errorMap;

        Skills skill1 = skillsService.saveOrUpdateSkills(skills);
        return new ResponseEntity<Skills>(skills, HttpStatus.CREATED);
    }

    @GetMapping("/{skillname}")
    public ResponseEntity<?> getInfoPhoneNo(@PathVariable String skillname){

        Skills skills = skillsService.findBySkillName(skillname);
        return new ResponseEntity<Skills>(skills, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Skills> findAllSkills(){
        return skillsService.findAllSkills();
    }

    @DeleteMapping("/{skillname}")
    public ResponseEntity<?> deleteSkill(@PathVariable String skillname){
        skillsService.deleteBySkillName(skillname);

        return new ResponseEntity<String>("User with ID: '" + skillname + "' was deleted", HttpStatus.OK);
    }
}
