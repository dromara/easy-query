package com.easy.query.test.doc.dto;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.api.dynamic.executor.query.ConfigureArgument;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.expression.parser.core.extra.ExtraAutoIncludeConfigure;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.test.doc.MySignUp;
import com.easy.query.test.doc.proxy.MySignUpProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * create time 2025/2/2 08:53
 * 文件说明
 *
 * @author xuejiaming
 */

@Data
public class MyComUserDTO10 {


    private String gw;
    @Navigate(value = RelationTypeEnum.OneToMany)
    private List<InternalMySignUps> mySignUps;


    /**
     * {@link MySignUp }
     */
    @Data
    @FieldNameConstants
    public static class InternalMySignUps {
        private String id;
        private LocalDateTime time;
        private String content;


        private static final ExtraAutoIncludeConfigure EXTRA_AUTO_INCLUDE_CONFIGURE = MySignUpProxy.TABLE.EXTRA_AUTO_INCLUDE_CONFIGURE()
                .where(o -> {
                    o.userId().eq("12345");
                })
                .configure(q->{
                    q.orderBy(m -> m.content().asc());
                }).ignoreNavigateConfigure();

    }


}
