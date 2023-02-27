package org.easy.query.mysql.base;

import org.easy.query.core.abstraction.EasyQueryLambdaFactory;
import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.basic.sql.segment.segment.AndPredicateSegment;
import org.easy.query.core.basic.sql.segment.segment.PredicateSegment;
import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.expression.parser.impl.DefaultSqlPredicate;
import org.easy.query.core.impl.AbstractSelect1;
import org.easy.query.core.impl.AbstractSelect2;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.query.builder.SqlTableInfo;
import org.easy.query.mysql.util.MySQLUtil;

/**
 * @FileName: MySqlSelect1.java
 * @Description: 文件说明
 * @Date: 2023/2/7 13:04
 * @Created by xuejiaming
 */
public class MySQLSelect1<T1> extends AbstractSelect1<T1> {

    private final SelectContext selectContext;

    public MySQLSelect1(Class<T1> t1Class, SelectContext selectContext) {
        super(t1Class, selectContext);
        this.selectContext = selectContext;
    }

    @Override
    protected <T2> AbstractSelect2<T1, T2> createSelect2(Class<T2> joinClass, MultiTableTypeEnum selectTableInfoType) {
        return new MySQLSelect2<>(t1Class,joinClass,selectContext,selectTableInfoType);
    }


    @Override
    public String toSql(String columns) {
        return MySQLUtil.toSelectSql(selectContext,columns);
    }


}
