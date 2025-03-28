package com.easy.query.test.doc;


import lombok.Data;
import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.enums.RelationTypeEnum;

import java.util.List;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * this file automatically generated by easy-query struct dto mapping
 * 当前文件是easy-query自动生成的 结构化dto 映射
 * {@link MySignUp }
 *
 * @author xuejiaming
 * @easy-query-dto schema: normal
 */
@Data
@FieldNameConstants
public class MySignUpDTO {


    private String id;
    private String comId;
    private String userId;
    private LocalDateTime time;
    private String content;
    private String comUserName;
    @Navigate(value = RelationTypeEnum.ManyToOne)
    private InternalComUser comUser;


    /**
     * {@link MyComUser }
     */
    @Data
    public static class InternalComUser {

        private String id;
        private String comId;
        private String userId;
        private String gw;


    }

}
