package org.easy.query.core.impl;

import org.easy.query.core.abstraction.*;
import org.easy.query.core.exception.JDQCException;
import org.easy.query.core.query.builder.SqlTableInfo;
import org.easy.query.core.segments.AndPredicateSegment;
import org.easy.query.core.segments.PredicateSegment;

import java.util.*;

/**
 *
 * @FileName: SelectContext.java
 * @Description: 文件说明
 * @Date: 2023/2/6 12:39
 * @Created by xuejiaming
 */
public  class SelectContext extends AbstractSqlPredicateContext {

    private final String alias;

    private long offset;
    private long rows;
    private final List<Object> params;

    private PredicateSegment where;
//    private  StringBuilder select;
    private SqlSegment0Builder group;
    private PredicateSegment having;
    private SqlSegment0Builder order;

    public SelectContext(EasyQueryRuntimeContext runtimeContext){
        this(runtimeContext,"t");
    }
    public SelectContext(EasyQueryRuntimeContext runtimeContext, String alias){
        super(runtimeContext);
        this.alias = alias;
        this.params =new ArrayList<>();
    }
    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getRows() {
        return rows;
    }

    public void setRows(long rows) {
        this.rows = rows;
    }

    public PredicateSegment getWhere() {
        if(where==null){
            where=new AndPredicateSegment(true);
        }
        return where;
    }
    public PredicateSegment getHaving() {
        if(having==null){
            having=new AndPredicateSegment(true);
        }
        return having;
    }
    public void addSqlTable(SqlTableInfo sqlTableInfo){
        this.tables.add(sqlTableInfo);
    }

    /**
     * 数据库别名 默认t
     * @return
     */

    public String getAlias() {
        return alias;
    }

    /**
     * 获取下次表索引
     * @return
     */
    public int getNextTableIndex(){
        return this.tables.size();
    }
    public SqlTableInfo getCurrentPredicateTable(){
        return this.getPredicateTableByOffset(0);
    }
    public SqlTableInfo getPreviewPredicateTable(){
        return this.getPredicateTableByOffset(1);
    }
    public SqlTableInfo getPredicateTableByOffset(int offsetForward){
        if(this.tables.isEmpty()){
            throw new JDQCException("cant get current join table");
        }
        int i = getNextTableIndex() -1 - offsetForward;
        return this.tables.get(i);
    }

//    public StringBuilder getSelect() {
//        if(select==null){
//            select=new StringBuilder();
//        }
//        return select;
//    }


    public SqlSegment0Builder getGroup() {
        if(group==null){
            group=new SqlGroupSegmentBuilder();
        }
        return group;
    }

    public SqlSegment0Builder getOrder() {
        if(order==null){
            order=new SqlOrderSegmentBuilder();
        }
        return order;
    }

    @Override
    public List<Object> getParameters() {
        return params;
    }
    @Override
    public void addParameter(Object parameter) {
       params.add(parameter);
    }
}
