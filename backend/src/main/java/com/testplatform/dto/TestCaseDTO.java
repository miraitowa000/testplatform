package com.testplatform.dto;

import lombok.Data;

@Data
public class TestCaseDTO {
    private Long projectId;
    private String name;
    private String description;
    private String precondition;
    private String steps;
    private String expectedResult;
    private Integer priority;
    private Integer status;
    private Long creatorId;
    private Long executorId;
}