package com.easy.query.test.entity.vo;

import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.enums.RelationTypeEnum;
import lombok.Data;

import java.util.List;

/**
 * create time 2025/8/25 23:11
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class MyCategoryVO3 {
    private String id;
    private String parentId;
    private String name;

    private Long deep;

    @Navigate(value = RelationTypeEnum.OneToMany,selfProperty = "id",targetProperty = "parentId",supportNonEntity = true)
    private List<MyCategoryVO3> children;
}
