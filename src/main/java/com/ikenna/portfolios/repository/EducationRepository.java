package com.ikenna.portfolios.repository;


import com.ikenna.portfolios.infos.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationRepository extends JpaRepository<Education, Integer> {

    public Education findBySchoolName(String schoolName);


}
