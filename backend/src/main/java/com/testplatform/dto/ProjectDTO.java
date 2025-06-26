package com.testplatform.dto;

import lombok.Data;

@Data
public class ProjectDTO {
    private String name;
    private String description;
    private Integer status;
    private Long ownerId;
}