package com.ikenna.portfolios.repository;

import com.ikenna.portfolios.infos.Project;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface ProjectRepository {

    Project findByProjectId(String projectId);
    Project save(MultipartFile multipartFile, Project project);
    String deleteProject(String projectId);
    String updateProject(MultipartFile multipartFile, Project project);

    Iterable<Project> findAll();
}
