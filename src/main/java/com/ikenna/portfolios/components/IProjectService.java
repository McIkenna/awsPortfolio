package com.ikenna.portfolios.components;

import com.ikenna.portfolios.infos.Project;
import org.springframework.stereotype.Component;

@Component
public interface IProjectService {
    public Project saveOrUpdateProject(Project project);
}
