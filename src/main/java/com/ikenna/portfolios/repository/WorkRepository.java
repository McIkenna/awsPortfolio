package com.ikenna.portfolios.repository;

import com.ikenna.portfolios.infos.Work;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepository extends CrudRepository<Work, Long> {

   Work findByCompanyName(String companyName);

}
