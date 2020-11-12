package com.ikenna.portfolios.repository;

import com.ikenna.portfolios.infos.Education;
import org.springframework.data.repository.CrudRepository;

public interface ContributedWorkRepository extends CrudRepository<Education, Long> {
}
