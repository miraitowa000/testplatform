package com.testplatform.controller;

import com.testplatform.common.ResultVO;
import com.testplatform.dto.ProjectDTO;
import com.testplatform.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "项目管理", description = "项目相关接口")
@RestController
@RequestMapping("/api/project")
@PreAuthorize("isAuthenticated()")
@Validated
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Operation(summary = "创建项目", description = "创建新项目，项目名称、负责人、开始时间和结束时间为必填项，描述为选填项。\n" +
            "项目状态根据时间自动计算：\n" +
            "- 开始时间 > 当前时间：未开始(0)\n" +
            "- 开始时间 ≤ 当前时间 且 结束时间 > 当前时间：进行中(1)\n" +
            "- 开始时间 < 当前时间 且 结束时间 ≤ 当前时间：已完成(2)")
    @PostMapping
    public ResultVO createProject(@Valid @RequestBody ProjectDTO projectDTO) {
        return projectService.createProject(projectDTO);
    }

    @Operation(summary = "更新项目", description = "更新项目信息，项目名称、负责人、开始时间和结束时间为必填项，描述为选填项。\n" +
            "项目状态根据时间自动计算：\n" +
            "- 开始时间 > 当前时间：未开始(0)\n" +
            "- 开始时间 ≤ 当前时间 且 结束时间 > 当前时间：进行中(1)\n" +
            "- 开始时间 < 当前时间 且 结束时间 ≤ 当前时间：已完成(2)")
    @PutMapping("/{id}")
    public ResultVO updateProject(
            @Parameter(description = "项目ID") @PathVariable Long id,
            @Valid @RequestBody ProjectDTO projectDTO) {
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

    @Operation(summary = "获取项目列表", description = "获取项目列表，可以根据状态筛选：\n" +
            "- 0: 未开始（开始时间 > 当前时间）\n" +
            "- 1: 进行中（开始时间 ≤ 当前时间 且 结束时间 > 当前时间）\n" +
            "- 2: 已完成（开始时间 < 当前时间 且 结束时间 ≤ 当前时间）")
    @GetMapping("/list")
    public ResultVO listProjects(
            @Parameter(description = "项目状态：0-未开始，1-进行中，2-已完成") 
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