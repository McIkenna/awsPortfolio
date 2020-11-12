package com.ikenna.portfolios.web;

import com.ikenna.portfolios.infos.DatabaseFile;
import com.ikenna.portfolios.infos.Response;
import com.ikenna.portfolios.services.DatabaseFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class FileUploadController {

    @Autowired
    private DatabaseFileService fileStorageService;

    @PostMapping("/uploadFile")
    public Response uploadFile(@RequestParam("file") MultipartFile file) {
        DatabaseFile fileName = fileStorageService.storeFile(file);
        Response response = new Response();
        if(fileName != null){
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(fileName.getFileName())
                    .toUriString();
            response.setFileType(fileName.getFileType());
            response.setFileName(fileName.getFileName());
            response.setFileDownloadUri(fileDownloadUri);
            response.setSize(file.getSize());
            response.setMessage("Upload was Successfull");
            return response;
        }

        response.setMessage("Sorry Something went wrong");
        return response;

    }

    @PostMapping("/uploadMultipleFiles")
    public List<Response> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }
}
