package com.ikenna.portfolios.repository;


import com.ikenna.portfolios.infos.Info;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface InfoRepository {

    Info findByInfoId(String infoId);
    Info save(MultipartFile multipartFile, Info info);
    String deleteInfo(String infoId);
    String updateInfo(MultipartFile multipartFile, Info info);

    Iterable<Info> findAll();
}
