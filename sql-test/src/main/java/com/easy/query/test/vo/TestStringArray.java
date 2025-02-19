package com.easy.query.test.vo;

import com.easy.query.core.annotation.EntityProxy;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * create time 2025/2/19 17:42
 * 文件说明
 *
 * @author xuejiaming
 */
@EntityProxy
@Data
public class TestStringArray {
    @NotBlank(message = "aa不能为空")
    private String[] aa;
}
