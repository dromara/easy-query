package org.easy.query.core.query.builder;

import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.abstraction.metadata.ColumnMetadata;
import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.basic.sql.segment.segment.AndPredicateSegment;
import org.easy.query.core.basic.sql.segment.segment.PredicateSegment;
import org.easy.query.core.util.LambdaUtil;

/**
 * @FileName: SelectTableInfo.java
 * @Description: 文件说明
 * @Date: 2023/2/7 11:36
 * @Created by xuejiaming
 */
public class SqlTableInfo {
    private final String alias;
    private final int index;
    private  PredicateSegment on;

    private final MultiTableTypeEnum multiTableType;

    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    private final EntityMetadata entityMetadata;

    public SqlTableInfo(EntityMetadata entityMetadata, String alias, int index, MultiTableTypeEnum multiTableType) {
        this.entityMetadata = entityMetadata;
        this.alias = alias;
        this.index = index;
        this.multiTableType = multiTableType;
    }


    public String getAlias() {
        return alias;
    }

    public int getIndex() {
        return index;
    }

    public PredicateSegment getOn() {
        if(on==null){
            on=new AndPredicateSegment(true);
        }
        return on;
    }

    public String getColumnName(String propertyName){
        return this.entityMetadata.getColumnName(propertyName);

    }
    public <T1> ColumnMetadata getColumn(Property<T1, ?> column){
        String attrName = LambdaUtil.getAttrName(column);
        return this.entityMetadata.getColumn(attrName);

    }
    public <T1> String getColumnName(Property<T1, ?> column){
        String attrName = LambdaUtil.getAttrName(column);
        return this.getColumnName(attrName);
    }
    public <T1> String getPropertyName(Property<T1, ?> column){
        return LambdaUtil.getAttrName(column);
    }

    public MultiTableTypeEnum getSelectTableInfoType() {
        return multiTableType;
    }
    public String getSelectTableSource(){
        if(MultiTableTypeEnum.LEFT_JOIN.equals(getSelectTableInfoType())){
            return  " LEFT JOIN ";
        }
        else if(MultiTableTypeEnum.INNER_JOIN.equals(getSelectTableInfoType())){
            return  " INNER JOIN ";
        }
        else if(MultiTableTypeEnum.RIGHT_JOIN.equals(getSelectTableInfoType())){
            return  " RIGHT JOIN ";
        }
        return " FROM ";
    }

}
