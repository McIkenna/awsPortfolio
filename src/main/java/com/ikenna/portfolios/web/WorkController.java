package com.ikenna.portfolios.web;

import com.ikenna.portfolios.infos.Work;
import com.ikenna.portfolios.services.MapErrorService;
import com.ikenna.portfolios.services.WorkService;
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
public class WorkController {

    @Autowired
    private WorkService workService;

    @Autowired
    private MapErrorService mapErrorService;

    @PostMapping("/admin/work")
    public ResponseEntity<?> addWorkExperience(@RequestParam MultipartFile file, Work work, BindingResult result){
     ResponseEntity<?> errorMap = mapErrorService.MapErrorService(result);
     if(errorMap != null) return errorMap;
     Work workFile = workService.save(file, work);
     return new ResponseEntity<Work>(work, HttpStatus.CREATED);
    }

    @GetMapping("/api/work/{workId}")
    public Work getWorkByIdentifier(@PathVariable String workId){
     return workService.findByWorkId(workId);
    }

    @GetMapping("/api/work/all")
    public Iterable<Work> getAllWorks(){
     return workService.findAll();
    }

    @DeleteMapping("/admin/work/{workId}")
    public ResponseEntity<?> deleteWork(@PathVariable String workId){
     workService.deleteWork(workId);
        return new ResponseEntity<String>("Work with company name '" + workId + "' was deleted", HttpStatus.OK);
    }

    @PutMapping( "/admin/work")
    public String updateWork(@RequestParam(value = "file") MultipartFile file, Work work){
        return workService.updateWork(file,work);
    }
}
