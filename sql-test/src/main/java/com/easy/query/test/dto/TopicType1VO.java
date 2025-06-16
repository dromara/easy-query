package com.easy.query.test.dto;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.test.conversion.EnumConverter;
import com.easy.query.test.enums.TopicTypeEnum;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * create time 2023/5/22 14:34
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@ToString
@EntityProxy
public class TopicType1VO {

    private String column1;
    private String column2;
    private String column3;
    private String column4;
}
