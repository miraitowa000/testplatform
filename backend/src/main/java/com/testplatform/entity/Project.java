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
@TableName("tb_project")
public class Project {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String name;
    
    private String description;
    
    @TableField(exist = false)
    private Integer status;
    
    @TableField("owner_id")
    private Long ownerId;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField("start_time")
    private Date startTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField("end_time")
    private Date endTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private Date createTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("update_time")
    private Date updateTime;
    
    public Integer getStatus() {
        Date now = new Date();
        
        if (startTime == null || endTime == null) {
            return 0; // 未开始
        }
        
        if (startTime.after(now)) {
            return 0; // 未开始
        } else if (startTime.before(now) && endTime.after(now)) {
            return 1; // 进行中
        } else {
            return 2; // 已完成
        }
    }
}