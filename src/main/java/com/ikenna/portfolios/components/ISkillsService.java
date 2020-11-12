package com.ikenna.portfolios.components;

import com.ikenna.portfolios.infos.Skills;
import org.springframework.stereotype.Component;

@Component
public interface ISkillsService {

    public Skills saveOrUpdateSkills(Skills skills);
}
