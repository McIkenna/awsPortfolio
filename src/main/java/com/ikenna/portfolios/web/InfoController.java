package com.ikenna.portfolios.web;

import com.ikenna.portfolios.infos.Info;
import com.ikenna.portfolios.services.InfoService;
import com.ikenna.portfolios.services.MapErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/info")
@CrossOrigin
public class InfoController {
    @Autowired
    private InfoService infoService;

    @Autowired
    private MapErrorService mapErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewInfo(@Valid @RequestBody Info info, BindingResult result){

       ResponseEntity<?> errorMap = mapErrorService.MapErrorService(result);
       if(errorMap != null) return errorMap;

        Info info1 = infoService.saveOrUpdateInfo(info);
        return new ResponseEntity<Info>(info, HttpStatus.CREATED);
    }


    @GetMapping("/{phoneNo}")
    public ResponseEntity<?> getInfoPhoneNo(@PathVariable String phoneNo){

        Info info = infoService.findInfoByPhoneNumber(phoneNo);
        return new ResponseEntity<Info>(info, HttpStatus.OK);
    }


    @GetMapping("/all")
    public Iterable<Info> getAllInfos(){
        return infoService.findAllInfos();
    }

    @DeleteMapping("/{phoneNo}")
    public ResponseEntity<?> deleteInfo(@PathVariable String phoneNo){
        infoService.deleteInfoByPhoneNo(phoneNo);

        return new ResponseEntity<String>("User with ID: '" + phoneNo + "' was deleted", HttpStatus.OK);
    }
}
