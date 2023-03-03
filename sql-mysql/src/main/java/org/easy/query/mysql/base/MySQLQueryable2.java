package org.easy.query.mysql.base;

import org.easy.query.core.basic.api.select.Queryable;
import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.basic.api.select.AbstractQueryable2;
import org.easy.query.core.expression.context.SelectContext;
import org.easy.query.core.expression.lambda.SqlExpression2;
import org.easy.query.core.expression.parser.abstraction.SqlColumnAsSelector;
import org.easy.query.core.expression.parser.abstraction.internal.ColumnResultSelector;
import org.easy.query.mysql.util.MySQLUtil;

import java.math.BigDecimal;

/**
 * @FileName: MySqlSelects.java
 * @Description: 文件说明
 * @Date: 2023/2/7 13:05
 * @Created by xuejiaming
 */
public class MySQLQueryable2<T1,T2> extends AbstractQueryable2<T1,T2> {
    private final SelectContext selectContext;

    public MySQLQueryable2(Class<T1> t1Class, Class<T2> t2Class, SelectContext selectContext, MultiTableTypeEnum selectTableInfoType) {
        super(t1Class,t2Class, selectContext, selectTableInfoType);
        this.selectContext = selectContext;
    }

    @Override
    public String toSql() {
         return MySQLUtil.toSelectSql(selectContext);
    }

}
