package com.ikenna.portfolios.repository;

import com.ikenna.portfolios.infos.Work;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface WorkRepository {

   Work findByWorkId(String workId);
   Work save(MultipartFile multipartFile, Work work);
   String deleteWork(String workId);
   String updateWork(MultipartFile multipartFile, Work work);

   Iterable<Work> findAll();

}
