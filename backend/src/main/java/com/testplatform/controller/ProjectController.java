package com.testplatform.controller;

import com.testplatform.common.ResultVO;
import com.testplatform.dto.ProjectDTO;
import com.testplatform.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "项目管理", description = "项目相关接口")
@RestController
@RequestMapping("/api/project")
@PreAuthorize("isAuthenticated()")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Operation(summary = "创建项目")
    @PostMapping
    public ResultVO createProject(@RequestBody ProjectDTO projectDTO) {
        return projectService.createProject(projectDTO);
    }

    @Operation(summary = "更新项目")
    @PutMapping("/{id}")
    public ResultVO updateProject(
            @Parameter(description = "项目ID") @PathVariable Long id,
            @RequestBody ProjectDTO projectDTO) {
        return projectService.updateProject(id, projectDTO);
    }

    @Operation(summary = "删除项目")
    @DeleteMapping("/{id}")
    public ResultVO deleteProject(
            @Parameter(description = "项目ID") @PathVariable Long id) {
        return projectService.deleteProject(id);
    }

    @Operation(summary = "获取项目详情")
    @GetMapping("/{id}")
    public ResultVO getProjectById(
            @Parameter(description = "项目ID") @PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @Operation(summary = "获取项目列表")
    @GetMapping("/list")
    public ResultVO listProjects(
            @Parameter(description = "项目状态：0-未开始，1-进行中，2-已完成，3-已归档") 
            @RequestParam(required = false) Integer status) {
        return projectService.listProjects(status);
    }

    @Operation(summary = "获取用户的项目列表")
    @GetMapping("/list/owner/{ownerId}")
    public ResultVO listProjectsByOwner(
            @Parameter(description = "项目负责人ID") @PathVariable Long ownerId) {
        return projectService.listProjectsByOwner(ownerId);
    }
}