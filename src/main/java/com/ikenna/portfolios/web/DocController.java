package com.ikenna.portfolios.web;

import com.ikenna.portfolios.infos.Doc;
import com.ikenna.portfolios.infos.Response;
import com.ikenna.portfolios.services.DocStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/file")
@CrossOrigin
public class DocController {
    @Autowired
    private DocStorageService docStorageService;

    @GetMapping("/")
    public String get(Model model) {
        List<Doc> docs = docStorageService.getFiles();
        model.addAttribute("docs", docs);
        return "doc";
    }

    @PostMapping("/uploadFile")
        public Response docFileUpload(@RequestParam("file") MultipartFile file,
                                      Doc doc){

            Doc docFile = docStorageService.saveFile(file, doc);
            Response response = new Response();
            if(docFile != null){
                String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/downloadFile/")
                        .path(docFile.getDocName())
                        .toUriString();
                response.setFileDownloadUri(downloadUri);
                response.setFileName(docFile.getDocName());
                response.setFileType(docFile.getDocType());
                response.setSize(file.getSize());
                response.setMessage("File Uploaded Successfully!");
                return response;
            }
           response.setMessage("Sorry there was an error somewhere");
            return response;
    }

    @PostMapping("/uploadFiles")
    public String uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, Doc[] docs) {
        for (MultipartFile file: files) {
            for(Doc doc: docs){
                docStorageService.saveFile(file, doc);
            }
            

        }
        return "redirect:/";
    }
    @GetMapping("/downloadFile/{title}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String title){
        Doc doc = docStorageService.getFile(title);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+doc.getDocName()+"\"")
                .body(new ByteArrayResource(doc.getData()));
    }


    @GetMapping("/uploads/all")
    public Iterable<Doc> findAllTasks(){
        return docStorageService.getFiles();
    }

    @GetMapping("/upload/{title}")
    public ResponseEntity<?> findTAskById(@PathVariable String title){
       Doc doc = docStorageService.getFile(title);
        return new ResponseEntity<Doc>(doc, HttpStatus.OK);
    }


    @DeleteMapping("/upload/{title}")
    public ResponseEntity<?> deleteWork(@PathVariable String title){
        docStorageService.DeleteWorkByTitle(title);
        return new ResponseEntity<String>("Work with company name '" + title + "' was deleted", HttpStatus.OK);
    }
}
