package com.ikenna.portfolios.web;

import com.ikenna.portfolios.infos.Info;
import com.ikenna.portfolios.services.InfoService;
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
@RequestMapping("/api/info")
@CrossOrigin
public class InfoController {
    @Autowired
    private InfoService infoService;

    @Autowired
    private MapErrorService mapErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewInfo(@RequestParam(value = "file") MultipartFile file, Info info, BindingResult result){

       ResponseEntity<?> errorMap = mapErrorService.MapErrorService(result);
       if(errorMap != null) return errorMap;

        Info info1 = infoService.save(file, info);
        return new ResponseEntity<Info>(info, HttpStatus.CREATED);
    }

    @GetMapping("/{infoId}")
    public Info getInfoById(@PathVariable String infoId){
        return infoService.findByInfoId(infoId);
    }

    @GetMapping("/all")
    public Iterable<Info> getAllInfos(){
        return infoService.findAll();
    }

    @DeleteMapping("/{infoId}")
    public String deleteInfo(@PathVariable String infoId){
        return infoService.deleteInfo(infoId);
    }

    @PutMapping("")
    public String updateInfo(@RequestParam(value = "file") MultipartFile file, Info info){
        return infoService.updateInfo(file, info);
    }
}
