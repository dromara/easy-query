package org.easy.query.mysql.base;

import org.easy.query.core.enums.SelectTableInfoTypeEnum;
import org.easy.query.core.impl.AbstractSelect2;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.mysql.util.MySQLUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: MySqlSelects.java
 * @Description: 文件说明
 * @Date: 2023/2/7 13:05
 * @Created by xuejiaming
 */
public class MySQLSelect2<T1,T2> extends AbstractSelect2<T1,T2> {
    private final SelectContext selectContext;

    public MySQLSelect2(Class<T1> t1Class, Class<T2> t2Class, SelectContext selectContext, SelectTableInfoTypeEnum selectTableInfoType) {
        super(t1Class,t2Class, selectContext, selectTableInfoType);
        this.selectContext = selectContext;
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
    public String toSql(String columns) {
        return MySQLUtil.toSql(selectContext,columns);
    }

}
