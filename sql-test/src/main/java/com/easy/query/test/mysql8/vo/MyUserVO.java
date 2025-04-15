package com.easy.query.test.mysql8.vo;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.enums.RelationTypeEnum;
import lombok.Data;

import java.util.List;

/**
 * create time 2025/4/15 17:03
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
public class MyUserVO {
    private String vo1;
    private String vo2;
    private String vo3;
    /**
     * 银行卡数量
     */
    private Long bankCardCount;

    @Navigate(RelationTypeEnum.OneToMany)
    private List<MyUserCardVO> cards;
}
