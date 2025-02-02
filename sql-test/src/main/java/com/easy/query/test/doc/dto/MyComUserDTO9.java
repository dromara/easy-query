package com.easy.query.test.doc.dto;

import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.OrderByProperty;
import com.easy.query.core.api.dynamic.executor.query.ConfigureArgument;
import com.easy.query.core.api.dynamic.executor.query.SelectAutoIncludeConfigurable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.enums.OrderByPropertyModeEnum;
import com.easy.query.core.enums.RelationTypeEnum;
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
    public static class InternalMySignUps implements SelectAutoIncludeConfigurable {
        private String id;
        private LocalDateTime time;
        private String content;


        @Override
        public <T> ClientQueryable<T> configure(ClientQueryable<T> queryable, ConfigureArgument configureArgument) {
            Map<String, String> map = configureArgument.getTypeArg();
            return queryable.where(o -> {
                for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
                    String column = stringStringEntry.getKey();
                    String value = stringStringEntry.getValue();
                    o.eq(column, value);
                }
            });
        }
    }


}
