package org.easy.query.core.expression.context;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegment;
import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import org.easy.query.core.expression.segment.builder.UpdateSetSqlBuilderSegment;
import org.easy.query.core.expression.segment.condition.AndPredicateSegment;
import org.easy.query.core.expression.segment.condition.PredicateSegment;
import org.easy.query.core.query.builder.SqlTableInfo;

/**
 * @FileName: DeleteContext.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:36
 * @Created by xuejiaming
 */
public class DeleteContext extends AbstractSqlContext {
    private final SqlBuilderSegment setColumns;
    private final PredicateSegment where;
    private SqlBuilderSegment setIgnoreColumns;
    private SqlBuilderSegment whereColumns;
    private boolean logicDelete=true;
    public DeleteContext(EasyQueryRuntimeContext runtimeContext) {
        super(runtimeContext);
        setColumns =new UpdateSetSqlBuilderSegment();
        where=new AndPredicateSegment(true);
    }
    public void addSqlTable(SqlTableInfo sqlTableInfo){
        this.tables.add(sqlTableInfo);
    }

    public SqlBuilderSegment getSetColumns() {
        return setColumns;
    }

    public PredicateSegment getWhere() {
        return where;
    }
    public SqlBuilderSegment getSetIgnoreColumns(){
        if(setIgnoreColumns==null){
            setIgnoreColumns=new UpdateSetSqlBuilderSegment();
        }
        return setIgnoreColumns;
    }
    public SqlBuilderSegment getWhereColumns(){
        if(whereColumns==null){
            whereColumns=new ProjectSqlBuilderSegment();
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
