package org.easy.query.core.basic.api.context;

import org.easy.query.core.abstraction.*;
import org.easy.query.core.basic.jdbc.parameter.SQLParameter;
import org.easy.query.core.expression.builder.SelectSqlSegmentBuilder;
import org.easy.query.core.expression.builder.SqlSegmentBuilder;
import org.easy.query.core.expression.builder.UpdateSetSqlSegmentBuilder;
import org.easy.query.core.query.builder.SqlTableInfo;
import org.easy.query.core.expression.segment.AndPredicateSegment;
import org.easy.query.core.expression.segment.PredicateSegment;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: UpdateContext.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:07
 * @Created by xuejiaming
 */
public class UpdateContext extends AbstractSqlContext {

    private final SqlSegmentBuilder setColumns;
    private final PredicateSegment where;
    private SqlSegmentBuilder setIgnoreColumns;
    private SqlSegmentBuilder whereColumns;
    private boolean logicDelete=true;

    public UpdateContext(EasyQueryRuntimeContext runtimeContext) {
        super(runtimeContext);
        setColumns =new UpdateSetSqlSegmentBuilder();
        where=new AndPredicateSegment(true);
//        properties=expressionUpdate?null:new ArrayList<>();
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

//    @Override
//    public List<String> getProperties() {
//        return properties;
//    }
//    @Override
//    public void addColumnProperty(String propertyName) {
//         properties.add(propertyName);
//    }


    public void setLogicDelete(boolean logicDelete) {
        this.logicDelete = logicDelete;
    }
    public boolean isLogicDelete() {
        return logicDelete;
    }
}
