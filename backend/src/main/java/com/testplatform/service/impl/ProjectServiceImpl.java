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

import java.util.Date;
import java.util.List;

@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Override
    public ResultVO createProject(ProjectDTO projectDTO) {
        Project project = new Project();
        BeanUtils.copyProperties(projectDTO, project);
        
        // 根据开始时间和结束时间计算项目状态
        project.setStatus(calculateProjectStatus(projectDTO.getStartTime(), projectDTO.getEndTime()));
        
        // 设置创建时间和更新时间
        Date now = new Date();
        project.setCreateTime(now);
        project.setUpdateTime(now);
        
        // 设置删除标记为0（未删除）
        project.setDeletedAt(0L);
        
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
        
        // 根据开始时间和结束时间重新计算项目状态
        project.setStatus(calculateProjectStatus(projectDTO.getStartTime(), projectDTO.getEndTime()));
        
        // 设置更新时间
        project.setUpdateTime(new Date());
        
        if (updateById(project)) {
            return new ResultVO(200, "更新项目成功", project);
        }
        return new ResultVO(500, "更新项目失败", null);
    }

    @Override
    public ResultVO deleteProject(Long id) {
        Project project = getById(id);
        if (project == null) {
            return new ResultVO(404, "项目不存在", null);
        }
        
        // 设置删除时间戳（转换为秒级时间戳，避免超出数据库 BIGINT 范围）
        project.setDeletedAt(System.currentTimeMillis() / 1000L);
        
        if (updateById(project)) {
            return new ResultVO(200, "删除项目成功", null);
        }
        return new ResultVO(500, "删除项目失败", null);
    }

    @Override
    public ResultVO getProjectById(Long id) {
        LambdaQueryWrapper<Project> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Project::getId, id)
                  .eq(Project::getDeletedAt, 0L);
        Project project = getOne(queryWrapper);
        if (project == null) {
            return new ResultVO(404, "项目不存在", null);
        }
        return new ResultVO(200, "获取项目成功", project);
    }

    @Override
    public ResultVO listProjects(Integer status) {
        LambdaQueryWrapper<Project> queryWrapper = new LambdaQueryWrapper<>();
        // 只查询未删除的项目
        queryWrapper.eq(Project::getDeletedAt, 0L);
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

    /**
     * 根据开始时间和结束时间计算项目状态
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 项目状态：0-未开始，1-进行中，2-已完成
     */
    private Integer calculateProjectStatus(Date startTime, Date endTime) {
        Date now = new Date();
        
        // 如果开始时间晚于当前时间，项目未开始
        if (startTime.after(now)) {
            return 0; // 未开始
        }
        
        // 如果结束时间早于或等于当前时间，项目已完成
        if (endTime.before(now) || endTime.equals(now)) {
            return 2; // 已完成
        }
        
        // 如果开始时间早于或等于当前时间，且结束时间晚于当前时间，项目进行中
        return 1; // 进行中
    }
}