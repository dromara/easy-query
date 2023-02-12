package org.easy.query.mysql.base;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.impl.SelectContext;

/**
 * @FileName: MySQLSelectContext.java
 * @Description: 文件说明
 * @Date: 2023/2/10 22:49
 * @Created by xuejiaming
 */
public class MySQLSelectContext extends SelectContext{
    public MySQLSelectContext(EasyQueryRuntimeContext runtimeContext) {
        super(runtimeContext);
    }
    public MySQLSelectContext(EasyQueryRuntimeContext runtimeContext, String alias){
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
