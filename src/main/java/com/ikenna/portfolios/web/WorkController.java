package com.ikenna.portfolios.web;

import com.ikenna.portfolios.infos.Response;
import com.ikenna.portfolios.infos.Work;
import com.ikenna.portfolios.services.MapErrorService;
import com.ikenna.portfolios.services.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/work")
@CrossOrigin
public class WorkController {

    @Autowired
    private WorkService workService;

    @Autowired
    private MapErrorService mapErrorService;

 @PostMapping("")
    public Response addWorkExperience(@RequestParam MultipartFile file, Work work){

     Work workFile = workService.saveOrUpdateWork(file, work);
     Response response = new Response();
     if(workFile != null) {
         String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                 .path("/downloadFile/")
                 .path(workFile.getDocName())
                 .toUriString();

         response.setFileDownloadUri(downloadUri);
         response.setFileName(workFile.getCompanyName());
         response.setFileType(workFile.getDocType());
         response.setSize(file.getSize());
         response.setMessage("File Uploaded Successfully!");
         return response;
     }
     response.setMessage("Sorry there was an error somewhere");
     return response;
    }

    @GetMapping("/{companyName}")
    public ResponseEntity<?> getWorkByIdentifier(@PathVariable String companyName){

     Work work = workService.findWorkByCompanyName(companyName);
     return new ResponseEntity<Work>(work, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Work> getAllWorks(){
     return workService.findAllWorkExperiences();
    }

    @DeleteMapping("/{companyName}")
    public ResponseEntity<?> deleteWork(@PathVariable String companyName){
     workService.DeleteWorkByCompanyName(companyName);
        return new ResponseEntity<String>("Work with company name '" + companyName + "' was deleted", HttpStatus.OK);
    }

}
