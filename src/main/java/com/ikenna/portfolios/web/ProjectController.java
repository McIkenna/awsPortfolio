package com.ikenna.portfolios.web;


import com.ikenna.portfolios.infos.Project;
import com.ikenna.portfolios.repository.ProjectRepository;
import com.ikenna.portfolios.services.MapErrorService;
import com.ikenna.portfolios.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@SpringBootApplication
@RequestMapping("")
@CrossOrigin
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    MapErrorService mapErrorService;
    @Autowired
    ProjectService projectService;

    @PostMapping("/admin/project")
    public ResponseEntity<?> createNewProject(@RequestParam(value = "file") MultipartFile file, Project project, BindingResult result){

        ResponseEntity<?> errorMap = mapErrorService.MapErrorService(result);
        if(errorMap != null) return errorMap;

        Project project1 = projectService.save(file, project);
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }

    @GetMapping("/api/project/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId){

        Project project = projectService.findByProjectId(projectId);
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/api/project/all")
    public Iterable<Project> findAllProject(){
        return projectService.findAll();
    }

    @DeleteMapping("/admin/project/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId){
      projectService.deleteProject(projectId);
        return new ResponseEntity<String>("Project Title: '" + projectId + "' was deleted", HttpStatus.OK);
    }

    @PutMapping("/admin/project")
    public String updateInfo(@RequestParam(value = "file") MultipartFile file, Project project){
        return projectService.updateProject(file,project);
    }
}
