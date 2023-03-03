package org.easy.query.core.query;

import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.expression.segment.condition.AndPredicateSegment;
import org.easy.query.core.expression.segment.condition.PredicateSegment;
import org.easy.query.core.util.LambdaUtil;

import java.util.List;

/**
 * @FileName: EasyEntityTableExpressionSegment.java
 * @Description: 文件说明
 * @Date: 2023/3/3 23:31
 * @Created by xuejiaming
 */
public class EasyEntityTableExpressionSegment implements SqlEntityTableExpressionSegment{

    private final EntityMetadata entityMetadata;
    private final int index;
    private final String alias;
    private final MultiTableTypeEnum multiTableType;
    private List<SqlExpressionSegment> tables;
    private PredicateSegment on;

    public EasyEntityTableExpressionSegment(EntityMetadata entityMetadata,int index, String alias, MultiTableTypeEnum multiTableType){
        this.entityMetadata = entityMetadata;

        this.index = index;
        this.alias = alias;
        this.multiTableType = multiTableType;
    }
    @Override
    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    @Override
    public <T1> String getPropertyName(Property<T1, ?> column) {
        return LambdaUtil.getAttrName(column);
    }

    @Override
    public String getColumnName(String propertyName) {
        return this.entityMetadata.getColumnName(propertyName);
    }



    @Override
    public SqlExpression<SqlPredicate<?>> getQueryFilterExpression() {
        if(entityMetadata.enableLogicDelete()){
            return entityMetadata.getLogicDeleteMetadata().getQueryFilterExpression();
        }
        return null;
    }

    @Override
    public SqlExpression<SqlColumnSetter<?>> getDeletedSqlExpression() {
        if(entityMetadata.enableLogicDelete()){
            return entityMetadata.getLogicDeleteMetadata().getDeletedSqlExpression();
        }
        return null;
    }

    @Override
    public Class<?> entityClass() {
        return entityMetadata.getEntityClass();
    }

    @Override
    public PredicateSegment getOn() {
        if(on==null){
            on=new AndPredicateSegment(true);
        }
        return on;
    }

    @Override
    public boolean hasOn() {
        return on!=null&&on.isNotEmpty();
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public String getSelectTableSource() {
        if(MultiTableTypeEnum.LEFT_JOIN.equals(multiTableType)){
            return  " LEFT JOIN ";
        }
        else if(MultiTableTypeEnum.INNER_JOIN.equals(multiTableType)){
            return  " INNER JOIN ";
        }
        else if(MultiTableTypeEnum.RIGHT_JOIN.equals(multiTableType)){
            return  " RIGHT JOIN ";
        }
        return " FROM ";
    }
    @Override
    public String toSql() {
        return null;
    }

}
