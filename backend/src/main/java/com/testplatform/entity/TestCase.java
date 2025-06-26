package com.testplatform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName("tb_test_case")
public class TestCase {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    @TableField("project_id")
    private Long projectId;
    
    private String name;
    
    private String description;
    
    private String precondition;
    
    private String steps;
    
    @TableField("expected_result")
    private String expectedResult;
    
    private Integer priority;
    
    private Integer status;
    
    @TableField("creator_id")
    private Long creatorId;
    
    @TableField("executor_id")
    private Long executorId;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private Date createTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("update_time")
    private Date updateTime;
}