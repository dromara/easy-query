package com.easy.query.test.mysql8.vo;


import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.NavigateFlat;
import com.easy.query.core.api.dynamic.executor.query.ConfigureArgument;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.expression.parser.core.extra.ExtraAutoIncludeConfigure;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.test.mysql8.entity.bank.proxy.SysUserProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * this file automatically generated by easy-query struct dto mapping
 * 当前文件是easy-query自动生成的 结构化dto 映射
 * {@link com.easy.query.test.mysql8.entity.bank.SysBank }
 *
 * @author xuejiaming
 * @easy-query-dto schema: normal
 */
@Data
public class SysBank2DTO {


    private String id;
    /**
     * 银行名称
     */
    private String name;
    /**
     * 成立时间
     */
    private LocalDateTime createTime;
    /**
     * 拥有的银行卡
     */
    @NavigateFlat(pathAlias = "bankCards.user")
    private List<InternalUser> bankCardUsers;


    /**
     * {@link com.easy.query.test.mysql8.entity.bank.SysUser }
     */
    @Data
    @FieldNameConstants
    public static class InternalUser {
        private static final ExtraAutoIncludeConfigure EXTRA_AUTO_INCLUDE_CONFIGURE = SysUserProxy.TABLE.EXTRA_AUTO_INCLUDE_CONFIGURE()
                .configure(query -> query.subQueryToGroupJoin(u -> u.userBooks()))
//                .configure(query -> query.configure(s->s.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN)))
                .where(o -> {
                    ConfigureArgument configureArgument = o.getEntitySQLContext().getExpressionContext().getConfigureArgument();
                    String arg = configureArgument.getTypeArg();
                    o.name().ne(arg);
                })
                .select(u -> Select.of(
                        u.userBooks().count().as(Fields.bookCount),
                        u.userBooks().orderBy(book -> book.price().desc()).firstElement().name().as(Fields.bookName),
                        u.userBooks().orderBy(book -> book.price().desc()).firstElement().price().as(Fields.bookPrice)
                ));

        private String id;
        private String name;
        private String phone;
        private LocalDateTime createTime;

        @SuppressWarnings("EasyQueryFieldMissMatch")
        private Long bookCount;
        @SuppressWarnings("EasyQueryFieldMissMatch")
        private String bookName;
        @SuppressWarnings("EasyQueryFieldMissMatch")
        private BigDecimal bookPrice;

    }

}
