package com.ikenna.portfolios.repository;

import com.ikenna.portfolios.infos.Doc;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface DocRepository {

    Doc findByDocId(String docId);
    Doc save(MultipartFile multipartFile, Doc doc);
    String deleteDoc(String docId);
    String updateDoc(MultipartFile multipartFile, Doc doc);

    Iterable<Doc> findAll();
}
