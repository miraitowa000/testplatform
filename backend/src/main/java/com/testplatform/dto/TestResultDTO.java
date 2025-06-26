package com.testplatform.dto;

import lombok.Data;

@Data
public class TestResultDTO {
    private Long caseId;
    private Integer status;
    private String actualResult;
    private String bugDescription;
    private Long executorId;
}