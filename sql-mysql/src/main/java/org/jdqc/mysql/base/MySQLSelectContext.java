package org.jdqc.mysql.base;

import org.jdqc.core.config.JDQCConfiguration;
import org.jdqc.core.impl.SelectContext;

/**
 * @FileName: MySQLSelectContext.java
 * @Description: 文件说明
 * @Date: 2023/2/10 22:49
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
