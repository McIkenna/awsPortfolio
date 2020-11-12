package com.ikenna.portfolios.repository;

import com.ikenna.portfolios.infos.Doc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocRepository extends JpaRepository<Doc, Integer> {

    public Doc findByProjectTitle(String projectTitle);
}
