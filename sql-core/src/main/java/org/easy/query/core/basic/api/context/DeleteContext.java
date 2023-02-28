package org.easy.query.core.basic.api.context;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.basic.sql.segment.builder.SelectSqlSegmentBuilder;
import org.easy.query.core.basic.sql.segment.builder.SqlSegmentBuilder;
import org.easy.query.core.basic.sql.segment.builder.UpdateSetSqlSegmentBuilder;
import org.easy.query.core.basic.sql.segment.segment.AndPredicateSegment;
import org.easy.query.core.basic.sql.segment.segment.PredicateSegment;
import org.easy.query.core.query.builder.SqlTableInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: DeleteContext.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:36
 * @Created by xuejiaming
 */
public class DeleteContext extends AbstractSqlPredicateContext implements SqlColumnPredicateContext{
    private final SqlSegmentBuilder setColumns;
    private final PredicateSegment where;
    private SqlSegmentBuilder setIgnoreColumns;
    private SqlSegmentBuilder whereColumns;
    private final List<Object> parameters;
    private final List<String> properties;
    private boolean logicDelete=true;
    public DeleteContext(EasyQueryRuntimeContext runtimeContext,boolean expressionDelete) {
        super(runtimeContext);
        setColumns =new UpdateSetSqlSegmentBuilder();
        where=new AndPredicateSegment(true);
        parameters=expressionDelete?new ArrayList<>():null;
        properties=expressionDelete?null:new ArrayList<>();
    }
    public void addSqlTable(SqlTableInfo sqlTableInfo){
        this.tables.add(sqlTableInfo);
    }

    public SqlSegmentBuilder getSetColumns() {
        return setColumns;
    }

    public PredicateSegment getWhere() {
        return where;
    }
    public SqlSegmentBuilder getSetIgnoreColumns(){
        if(setIgnoreColumns==null){
            setIgnoreColumns=new UpdateSetSqlSegmentBuilder();
        }
        return setIgnoreColumns;
    }
    public SqlSegmentBuilder getWhereColumns(){
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

    @Override
    public List<String> getProperties() {
        return properties;
    }
    @Override
    public void addColumnProperty(String propertyName) {
        properties.add(propertyName);
    }


    public void setLogicDelete(boolean logicDelete) {
        this.logicDelete = logicDelete;
    }
    public boolean isLogicDelete() {
        return logicDelete;
    }
}
