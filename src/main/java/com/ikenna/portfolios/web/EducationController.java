package com.ikenna.portfolios.web;

import com.ikenna.portfolios.infos.Education;
import com.ikenna.portfolios.services.EducationService;
import com.ikenna.portfolios.services.MapErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@SpringBootApplication
@RequestMapping("/api/education")
@CrossOrigin
public class EducationController {

    @Autowired
    EducationService educationService;

    @Autowired
    MapErrorService mapErrorService;

    @PostMapping("")

    public ResponseEntity<?> addWorkEducation(@RequestParam(value = "file") MultipartFile file, Education education, BindingResult result){

        ResponseEntity<?> errorMap = mapErrorService.MapErrorService(result);
        if(errorMap != null) return errorMap;

        Education education1 = educationService.save(file, education);
        return new ResponseEntity<Education>(education, HttpStatus.CREATED);
    }

    @GetMapping("/{eduId}")
    public Education getEducationBySchoolName(@PathVariable String eduId){
        return educationService.findByEduId(eduId);
    }

    @GetMapping("/all")
    public Iterable<Education> getAllEducation(){
        return educationService.findAll();
    }

    @DeleteMapping("/{eduId}")
    public String deleteEducation(@PathVariable String eduId){

        return educationService.deleteEdu(eduId);
    }

    @PutMapping("")
    public String updateEducation(@RequestParam(value = "file") MultipartFile file, Education education){
        return educationService.updateEdu(file, education);
    }
}
