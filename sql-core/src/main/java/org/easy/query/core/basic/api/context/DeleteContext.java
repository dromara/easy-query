package org.easy.query.core.basic.api.context;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.expression.builder.SelectSqlSegmentBuilder;
import org.easy.query.core.expression.builder.SqlSegmentBuilder;
import org.easy.query.core.expression.builder.UpdateSetSqlSegmentBuilder;
import org.easy.query.core.expression.segment.AndPredicateSegment;
import org.easy.query.core.expression.segment.PredicateSegment;
import org.easy.query.core.query.builder.SqlTableInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: DeleteContext.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:36
 * @Created by xuejiaming
 */
public class DeleteContext extends AbstractSqlContext{
    private final SqlSegmentBuilder setColumns;
    private final PredicateSegment where;
    private SqlSegmentBuilder setIgnoreColumns;
    private SqlSegmentBuilder whereColumns;
    private boolean logicDelete=true;
    public DeleteContext(EasyQueryRuntimeContext runtimeContext) {
        super(runtimeContext);
        setColumns =new UpdateSetSqlSegmentBuilder();
        where=new AndPredicateSegment(true);
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


    public void setLogicDelete(boolean logicDelete) {
        this.logicDelete = logicDelete;
    }
    public boolean isLogicDelete() {
        return logicDelete;
    }
}
