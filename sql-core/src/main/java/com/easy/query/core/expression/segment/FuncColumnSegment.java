package com.easy.query.core.expression.segment;

import com.easy.query.core.enums.EasyFunc;
import com.easy.query.core.expression.sql.EntityExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;

/**
 * @FileName: FuncColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/19 22:17
 * @author xuejiaming
 */
public class FuncColumnSegment  implements SqlEntityAliasSegment {


    protected final EntityTableExpression table;
    protected final String propertyName;
    protected final EntityExpression sqlEntityExpression;
    protected final EasyFunc easyFunc;
    protected String alias;

    public FuncColumnSegment(EntityTableExpression table, String propertyName, EntityExpression sqlEntityExpression, EasyFunc easyFunc){
        this(table,propertyName,sqlEntityExpression,easyFunc,null);
    }
    public FuncColumnSegment(EntityTableExpression table, String propertyName, EntityExpression sqlEntityExpression, EasyFunc easyFunc, String alias){
        this.table = table;
        this.propertyName = propertyName;
        this.sqlEntityExpression = sqlEntityExpression;
        this.easyFunc = easyFunc;
        this.alias = alias;
    }

    @Override
    public String toSql() {
        String sqlColumnSegment = sqlEntityExpression.getSqlOwnerColumn(table,propertyName);
        String funcColumn = easyFunc.getFuncColumn(sqlColumnSegment);
        StringBuilder sql = new StringBuilder().append(funcColumn);
        if(alias!=null){
            sql.append(" AS ").append(sqlEntityExpression.getQuoteName(alias));
        }
        return sql.toString();
    }

    @Override
    public EntityTableExpression getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }


    @Override
    public String getAlias() {
        return alias;
    }
}
