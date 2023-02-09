package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.abstraction.lambda.SqlExpression;
import org.jdqc.sql.core.abstraction.sql.base.ColumnSelector;
import org.jdqc.sql.core.abstraction.sql.base.SqlColumnSelector;
import org.jdqc.sql.core.enums.SelectTableInfoTypeEnum;
import org.jdqc.sql.core.exception.JDQCException;
import org.jdqc.sql.core.impl.lambda.DefaultSqlColumnSelector;
import org.jdqc.sql.core.query.builder.SelectTableInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: MySqlSelect1.java
 * @Description: 文件说明
 * @Date: 2023/2/7 13:04
 * @Created by xuejiaming
 */
public class MySqlSelect1<T1> extends AbstractSelect1<T1> {

    private final MySQLSelectContext selectContext;

    public MySqlSelect1(Class<T1> t1Class, MySQLSelectContext selectContext) {
        super(t1Class, selectContext);
        this.selectContext = selectContext;
    }

    @Override
    protected <T2> AbstractSelect2<T1, T2> createSelect2(Class<T2> joinClass, SelectTableInfoTypeEnum selectTableInfoType) {
        return new MySqlSelect2<>(t1Class,joinClass,selectContext,selectTableInfoType);
    }


    @Override
    public int count() {
        return 0;
    }

    @Override
    public boolean any() {
        return false;
    }

    @Override
    protected List<T1> toInternalList() {
        String s = toSql();
        System.out.println(s);
        return new ArrayList<>();
    }



}
