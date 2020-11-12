package com.ikenna.portfolios.services;


import com.ikenna.portfolios.components.IWorkService;
import com.ikenna.portfolios.exceptions.WorkException;
import com.ikenna.portfolios.infos.Work;
import com.ikenna.portfolios.repository.AddressRepository;
import com.ikenna.portfolios.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;

@Service
public class WorkService implements IWorkService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private WorkRepository workRepository;


    public Work saveOrUpdateWork(MultipartFile file, Work work) {
        String companyName = work.getCompanyName() ;
        String jobTitle = work.getJobTitle();
        String jobDescription = work.getJobDescription();
        Date startDate = work.getStartDate();
        Date endDate = work.getEndDate();
        String docName = file.getOriginalFilename();
        String docType = file.getContentType();

        String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(companyName)
                .toUriString();
        String urlDownload = work.setUrlDownload(downloadUri);
        try{
            Work works = new Work(companyName, jobTitle, jobDescription, startDate, endDate, file.getBytes(), docName, docType , urlDownload);
            return workRepository.save(works);
        }catch(Exception ex){
            throw new WorkException("Schoolname '" + work.getCompanyName().toUpperCase() + "' Already exist");
        }
    }


    public Work findWorkByCompanyName(String companyName) {
        Work work = workRepository.findByCompanyName(companyName.toUpperCase());
        if(work == null){
            throw new WorkException("The user with phone number '" + companyName.toUpperCase() + "' does not exist");
        }
        return work;
    }


    public Iterable<Work> findAllWorkExperiences(){
        return workRepository.findAll();
    }

    public void DeleteWorkByCompanyName(String companyName){
        Work work = workRepository.findByCompanyName(companyName.toUpperCase());
        if(companyName == null){
            throw new WorkException("Cannot delete, '" + companyName + "' does not exist");
        }
        workRepository.delete(work);
    }


}
