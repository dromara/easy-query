package org.jdqc.mysql.base;

import org.jdqc.core.abstraction.JDQCRuntimeContext;
import org.jdqc.core.impl.SelectContext;

/**
 * @FileName: MySQLSelectContext.java
 * @Description: 文件说明
 * @Date: 2023/2/10 22:49
 * @Created by xuejiaming
 */
public class MySQLSelectContext extends SelectContext{
    public MySQLSelectContext(JDQCRuntimeContext runtimeContext) {
        super(runtimeContext);
    }
    public MySQLSelectContext(JDQCRuntimeContext runtimeContext, String alias){
        super(runtimeContext,alias);
    }

    @Override
    public SelectContext copy() {

        MySQLSelectContext mySQLSelectContext = new MySQLSelectContext(getRuntimeContext(), getAlias());
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
