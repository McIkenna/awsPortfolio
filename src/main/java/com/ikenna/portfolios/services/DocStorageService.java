package com.ikenna.portfolios.services;


import com.ikenna.portfolios.exceptions.WorkException;
import com.ikenna.portfolios.infos.Doc;
import com.ikenna.portfolios.repository.DocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class DocStorageService {
    @Autowired
    private DocRepository docRepository;

    public Doc saveFile(MultipartFile file, Doc docFile) {
        String docName = file.getOriginalFilename();
        String projectTitle = docFile.getProjectTitle();
        String projectSummary = docFile.getProjectSummary();
        String role = docFile.getKeyRole();
        String progress = docFile.getProgress();
        String link = docFile.getLink();
        Date startDate = docFile.getStartDate();
        Date endDate = docFile.getEndDate();
        String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(docName)
                .toUriString();
        String urlDownload = docFile.setUrlDownload(downloadUri);


        try {
            Doc doc = new Doc(docName,file.getContentType(),file.getBytes(), urlDownload, link, projectTitle,projectSummary, progress, role, startDate, endDate);
            return docRepository.save(doc);
        }
        catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Doc getFile(String title) {
        Doc doc =  docRepository.findByProjectTitle(title.toUpperCase());
        if(doc == null){
            throw new WorkException("The user with phone number '" + title + "' does not exist");
        }
        return doc;
    }

    public void DeleteWorkByTitle(String title){
        Doc doc = docRepository.findByProjectTitle(title.toUpperCase());
        if(doc == null){
            throw new WorkException("Cannot delete, '" + title + "' does not exist");
        }
        docRepository.delete(doc);
    }

    public List<Doc> getFiles(){
        return docRepository.findAll();
    }
}
