package com.testplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testplatform.common.ResultVO;
import com.testplatform.dto.ProjectDTO;
import com.testplatform.entity.Project;
import com.testplatform.mapper.ProjectMapper;
import com.testplatform.service.ProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Override
    public ResultVO createProject(ProjectDTO projectDTO) {
        Project project = new Project();
        BeanUtils.copyProperties(projectDTO, project);
        if (save(project)) {
            return new ResultVO(200, "创建项目成功", project);
        }
        return new ResultVO(500, "创建项目失败", null);
    }

    @Override
    public ResultVO updateProject(Long id, ProjectDTO projectDTO) {
        Project project = getById(id);
        if (project == null) {
            return new ResultVO(404, "项目不存在", null);
        }
        BeanUtils.copyProperties(projectDTO, project);
        if (updateById(project)) {
            return new ResultVO(200, "更新项目成功", project);
        }
        return new ResultVO(500, "更新项目失败", null);
    }

    @Override
    public ResultVO deleteProject(Long id) {
        if (removeById(id)) {
            return new ResultVO(200, "删除项目成功", null);
        }
        return new ResultVO(500, "删除项目失败", null);
    }

    @Override
    public ResultVO getProjectById(Long id) {
        Project project = getById(id);
        if (project != null) {
            return new ResultVO(200, "获取项目成功", project);
        }
        return new ResultVO(404, "项目不存在", null);
    }

    @Override
    public ResultVO listProjects(Integer status) {
        LambdaQueryWrapper<Project> queryWrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            queryWrapper.eq(Project::getStatus, status);
        }
        List<Project> projects = list(queryWrapper);
        return new ResultVO(200, "获取项目列表成功", projects);
    }

    @Override
    public ResultVO listProjectsByOwner(Long ownerId) {
        LambdaQueryWrapper<Project> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Project::getOwnerId, ownerId);
        List<Project> projects = list(queryWrapper);
        return new ResultVO(200, "获取项目列表成功", projects);
    }
}