package org.easy.query.core.impl;

import org.easy.query.core.abstraction.*;
import org.easy.query.core.query.builder.SqlTableInfo;
import org.easy.query.core.segments.AndPredicateSegment;
import org.easy.query.core.segments.PredicateSegment;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: UpdateContext.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:07
 * @Created by xuejiaming
 */
public class UpdateContext extends AbstractSqlPredicateContext{

    private final SqlSegment0Builder setColumns;
    private final PredicateSegment where;
    private  SqlSegment0Builder setIgnoreColumns;
    private  SqlSegment0Builder whereColumns;
    private final List<Object> parameters;

    public UpdateContext(EasyQueryRuntimeContext runtimeContext,boolean expressionUpdate) {
        super(runtimeContext);
        setColumns =expressionUpdate?new UpdateSetSqlSegmentBuilder():new UpdateSetSelectorSqlSegmentBuilder();
        where=new AndPredicateSegment(true);
        parameters=expressionUpdate?new ArrayList<>():null;
    }
    public void addSqlTable(SqlTableInfo sqlTableInfo){
        this.tables.add(sqlTableInfo);
    }

    public SqlSegment0Builder getSetColumns() {
        return setColumns;
    }

    public PredicateSegment getWhere() {
        return where;
    }
    public SqlSegment0Builder getSetIgnoreColumns(){
        if(setIgnoreColumns==null){
            setIgnoreColumns=new UpdateSetSqlSegmentBuilder();
        }
        return setIgnoreColumns;
    }
    public SqlSegment0Builder getWhereColumns(){
        if(whereColumns==null){
            whereColumns=new SelectSqlSegmentBuilder();
        }
        return whereColumns;
    }

    @Override
    public List<Object> getParameters() {
        return parameters;
    }

    @Override
    public void addParameter(Object parameter) {
        parameters.add(parameter);
    }
}
