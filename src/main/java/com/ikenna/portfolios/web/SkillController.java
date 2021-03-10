package com.ikenna.portfolios.web;

import com.ikenna.portfolios.infos.Skill;
import com.ikenna.portfolios.services.MapErrorService;
import com.ikenna.portfolios.services.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@SpringBootApplication
@RequestMapping("")
@CrossOrigin
public class SkillController {

    @Autowired
    private SkillService skillService;

    @Autowired
    private MapErrorService mapErrorService;

    @PostMapping("/admin/skill")
    public ResponseEntity<?> createNewInfo(@RequestParam(value = "file") MultipartFile file, Skill skill, BindingResult result){

        ResponseEntity<?> errorMap = mapErrorService.MapErrorService(result);
        if(errorMap != null) return errorMap;

        Skill skill1 = skillService.save(file, skill);
        return new ResponseEntity<Skill>(skill, HttpStatus.CREATED);
    }

    @GetMapping("/api/skill/{skillId}")

    public Skill getSkillById(@PathVariable String skillId){

        return skillService.findBySkillId(skillId);
    }

    @GetMapping("/api/skill/all")
    public Iterable<Skill> findAllSkill(){
        return skillService.findAll();
    }

    @DeleteMapping("/admin/skill/{skillId}")
    public String  deleteSkill(@PathVariable String skillId){
        return skillService.deleteSkill(skillId);
    }
    @PutMapping("/admin/skill")
    public String updateInfo(@RequestParam(value = "file") MultipartFile file, Skill skill){
        return skillService.updateSkill(file, skill);
    }
}
