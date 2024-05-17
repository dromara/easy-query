package com.easy.query.test.entity.blogtest.dto;

import com.easy.query.core.annotation.EntityProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

/**
 * create time 2024/5/15 22:04
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@FieldNameConstants
@EntityProxy
public class SysUserDTO {
    private String id;
    private String name;
    private LocalDateTime createTime;
    //来自SysUserAddress.addr
    private String myAddress;
}
