package com.ikenna.portfolios.services;

import com.ikenna.portfolios.components.IProjectService;
import com.ikenna.portfolios.exceptions.ProjectException;
import com.ikenna.portfolios.infos.Project;
import com.ikenna.portfolios.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProjectService implements IProjectService {

    @Autowired
    ProjectRepository projectRepository;


    public Project saveOrUpdateProject(Project project) {
        try{
            project = projectRepository.save(project);

            return project;
        }catch(Exception e){
            throw new ProjectException("The project, '" + project.getProjectTitle().toUpperCase() + "' already exist");
        }
    }

    public Project findByProjectTitle(String projectIdentifier){
        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());

        if(project == null){
            throw new ProjectException("The Project '" + projectIdentifier.toUpperCase() + "' does not exist");
        }
        return project;
    }

    public Iterable<Project> findAllProject(){
        return projectRepository.findAll();
    }

    public void deleteByProjectIdentifier(String projectIdentifier){
        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
        if(project == null){
            throw new ProjectException("Cannot delete, '" + projectIdentifier.toUpperCase() + "' does not exist");
        }
       projectRepository.delete(project);
    }
}
