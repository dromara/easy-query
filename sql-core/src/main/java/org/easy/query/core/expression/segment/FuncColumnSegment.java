package org.easy.query.core.expression.segment;

import org.easy.query.core.abstraction.sql.enums.IEasyFunc;
import org.easy.query.core.basic.api.context.SqlContext;
import org.easy.query.core.query.builder.SqlTableInfo;

/**
 * @FileName: FuncColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/19 22:17
 * @Created by xuejiaming
 */
public class FuncColumnSegment  implements SqlSegment {


    protected final SqlTableInfo table;
    protected final String propertyName;
    protected final SqlContext sqlContext;
    protected final IEasyFunc easyFunc;
    protected String alias;

    public FuncColumnSegment(SqlTableInfo table, String propertyName, SqlContext sqlContext, IEasyFunc easyFunc){
        this(table,propertyName,sqlContext,easyFunc,null);
    }
    public FuncColumnSegment(SqlTableInfo table, String propertyName, SqlContext sqlContext, IEasyFunc easyFunc,String alias){
        this.table = table;
        this.propertyName = propertyName;
        this.sqlContext = sqlContext;
        this.easyFunc = easyFunc;
        this.alias = alias;
    }

    @Override
    public String getSql() {
        String sqlColumnSegment = sqlContext.getSqlColumnSegment(table,propertyName);
        String funcColumn = easyFunc.getFuncColumn(sqlColumnSegment);
        StringBuilder sql = new StringBuilder().append(funcColumn);
        if(alias!=null){
            sql.append(" AS ").append(sqlContext.getQuoteName(alias));
        }
        return sql.toString();
    }
}
