package org.easy.query.mysql.base;

import org.easy.query.core.enums.SelectTableInfoTypeEnum;
import org.easy.query.core.exception.JDQCException;
import org.easy.query.core.impl.AbstractSelect1;
import org.easy.query.core.impl.AbstractSelect2;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.mysql.util.MySQLUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    protected <T2> AbstractSelect2<T1, T2> createSelect2(Class<T2> joinClass, SelectTableInfoTypeEnum selectTableInfoType) {
        return new MySQLSelect2<>(t1Class,joinClass,selectContext,selectTableInfoType);
    }


    @Override
    public String toSql(String columns) {
        return MySQLUtil.toSql(selectContext,columns);
    }


}
