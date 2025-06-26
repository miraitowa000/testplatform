package com.testplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.testplatform.common.ResultVO;
import com.testplatform.dto.ProjectDTO;
import com.testplatform.entity.Project;

public interface ProjectService extends IService<Project> {
    ResultVO createProject(ProjectDTO projectDTO);
    ResultVO updateProject(Long id, ProjectDTO projectDTO);
    ResultVO deleteProject(Long id);
    ResultVO getProjectById(Long id);
    ResultVO listProjects(Integer status);
    ResultVO listProjectsByOwner(Long ownerId);
}