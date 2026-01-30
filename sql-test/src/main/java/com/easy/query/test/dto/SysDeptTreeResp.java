package com.easy.query.test.dto;

import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.enums.RelationTypeEnum;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * create time 2026/1/30 19:14
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@FieldNameConstants
public class SysDeptTreeResp {

    private Long id;

    private Long pid;

    private String name;
    private Long deep;

    @Navigate(value = RelationTypeEnum.OneToMany)
    private List<SysDeptTreeResp> children;


}
