package com.ikenna.portfolios.web;

import com.ikenna.portfolios.infos.Doc;
import com.ikenna.portfolios.services.DocStorageService;
import com.ikenna.portfolios.services.MapErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file")
@CrossOrigin
public class DocController {
    @Autowired
    private DocStorageService docStorageService;

    @Autowired
    private MapErrorService mapErrorService;

    @PostMapping("")
    public ResponseEntity<?> addWorkExperience(@RequestParam MultipartFile file, Doc doc, BindingResult result){
        ResponseEntity<?> errorMap = mapErrorService.MapErrorService(result);
        if(errorMap != null) return errorMap;
        Doc docFile = docStorageService.save(file, doc);
        return new ResponseEntity<Doc>(docFile, HttpStatus.CREATED);
    }

    @GetMapping("/{docId}")
    public ResponseEntity<?> getWorkByIdentifier(@PathVariable String docId){

        Doc doc = docStorageService.findByDocId(docId);
        return new ResponseEntity<Doc>(doc, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Doc> getAllWorks(){
        return docStorageService.findAll();
    }

    @DeleteMapping("/{docId}")
    public ResponseEntity<?> deleteWork(@PathVariable String docId){
        docStorageService.deleteDoc(docId);
        return new ResponseEntity<String>("Work with company name '" + docId + "' was deleted", HttpStatus.OK);
    }

    @PutMapping("")
    public String updateInfo(@RequestParam(value = "file") MultipartFile file, Doc doc){
        return docStorageService.updateDoc(file,doc);
    }
}
