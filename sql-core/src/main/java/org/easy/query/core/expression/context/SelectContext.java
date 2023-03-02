package org.easy.query.core.expression.context;

import org.easy.query.core.abstraction.*;
import org.easy.query.core.expression.segment.builder.GroupBySqlBuilderSegment;
import org.easy.query.core.expression.segment.builder.OrderBySqlBuilderSegment;
import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import org.easy.query.core.exception.EasyQueryException;
import org.easy.query.core.expression.segment.condition.PredicateSegment;
import org.easy.query.core.query.builder.SqlTableInfo;
import org.easy.query.core.expression.segment.condition.AndPredicateSegment;

/**
 *
 * @FileName: SelectContext.java
 * @Description: 文件说明
 * @Date: 2023/2/6 12:39
 * @Created by xuejiaming
 */
public  class SelectContext extends AbstractSqlContext {

    private final String alias;

    private long offset;
    private long rows;
    private PredicateSegment where;
//    private  StringBuilder select;
    private SqlBuilderSegment group;
    private PredicateSegment having;
    private SqlBuilderSegment order;


    public SelectContext(EasyQueryRuntimeContext runtimeContext){
        this(runtimeContext,"t");
    }
    public SelectContext(EasyQueryRuntimeContext runtimeContext, String alias){
        super(runtimeContext);
        this.alias = alias;
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
    public boolean hasWhere(){
        return where!=null&&where.isNotEmpty();
    }
    public PredicateSegment getHaving() {
        if(having==null){
            having=new AndPredicateSegment(true);
        }
        return having;
    }
    public boolean hasHaving(){
        return having!=null&&having.isNotEmpty();
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
            throw new EasyQueryException("cant get current join table");
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


    public SqlBuilderSegment getGroup() {
        if(group==null){
            group=new GroupBySqlBuilderSegment();
        }
        return group;
    }
    public boolean hasGroup(){
        return group!=null&&group.isNotEmpty();
    }

    public SqlBuilderSegment getOrder() {
        if(order==null){
            order=new OrderBySqlBuilderSegment();
        }
        return order;
    }
    public boolean hasOrder(){
        return order!=null&&order.isNotEmpty();
    }

}
