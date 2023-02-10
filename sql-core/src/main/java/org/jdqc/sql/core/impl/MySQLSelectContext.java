package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.config.JDQCConfiguration;
import org.jdqc.sql.core.exception.JDQCException;
import org.jdqc.sql.core.query.builder.SelectTableInfo;

import java.util.ArrayList;

/**
 * @FileName: MySQLSelectContext.java
 * @Description: 文件说明
 * @Date: 2023/2/9 23:21
 * @Created by xuejiaming
 */
public class MySQLSelectContext extends SelectContext{
    public MySQLSelectContext(JDQCConfiguration jdqcConfiguration) {
        super(jdqcConfiguration);
    }
    public MySQLSelectContext(JDQCConfiguration jdqcConfiguration,String alias){
        super(jdqcConfiguration,alias);
    }

    @Override
    public SelectContext copy() {

        MySQLSelectContext mySQLSelectContext = new MySQLSelectContext(getJdqcConfiguration(), getAlias());
        mySQLSelectContext.setSkip(getSkip());
        mySQLSelectContext.setTake(getTake());
        mySQLSelectContext.getTables().addAll(getTables());
        mySQLSelectContext.getParams().addAll(getParams());
        mySQLSelectContext.getWhere().append(getWhere());
        mySQLSelectContext.getGroup().append(getGroup());
        mySQLSelectContext.getOrder().append(getOrder());
        return mySQLSelectContext;
    }
}
