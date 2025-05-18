package com.easy.query.test.doc.dto;

import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.api.dynamic.executor.query.ConfigureArgument;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.extra.ExtraAutoIncludeConfigure;
import com.easy.query.core.proxy.sql.Select;
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
public class MyComUserDTO9 {


    private String gw;
    @Navigate(value = RelationTypeEnum.OneToMany)
    private List<InternalMySignUps> mySignUps;


    /**
     * {@link com.easy.query.test.doc.MySignUp }
     */
    @Data
    @FieldNameConstants
    public static class InternalMySignUps {


        private static final ExtraAutoIncludeConfigure EXTRA_AUTO_INCLUDE_CONFIGURE = MySignUpProxy.TABLE.EXTRA_AUTO_INCLUDE_CONFIGURE()
                .where(o -> {
                    Filter filter = o.getEntitySQLContext().getFilter();
                    TableAvailable table = o.getTable();
                    Map<String, String> map = o.getEntitySQLContext().getExpressionContext().getConfigureArgument().getTypeArg();
                    for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
                        String column = stringStringEntry.getKey();
                        String value = stringStringEntry.getValue();

                        filter.eq(table, column, value);
                    }
                }).ignoreNavigateConfigure();
        private String id;
        private LocalDateTime time;
        private String content;

    }


}
