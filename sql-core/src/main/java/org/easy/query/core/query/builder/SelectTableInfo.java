package org.easy.query.core.query.builder;

import org.easy.query.core.abstraction.SqlPredicateSegmentBuilder;
import org.easy.query.core.abstraction.SqlSegment0;
import org.easy.query.core.abstraction.SqlSegment0Builder;
import org.easy.query.core.abstraction.lambda.Property;
import org.easy.query.core.enums.SelectTableInfoTypeEnum;
import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.segments.AndPredicateSegment;
import org.easy.query.core.segments.PredicateSegment;
import org.easy.query.core.util.LambdaUtil;

/**
 * @FileName: SelectTableInfo.java
 * @Description: 文件说明
 * @Date: 2023/2/7 11:36
 * @Created by xuejiaming
 */
public class SelectTableInfo {
    private final String alias;
    private final PredicateSegment on;
    private final int index;



    private final SelectTableInfoTypeEnum selectTableInfoType;

    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    private final EntityMetadata entityMetadata;

    public SelectTableInfo(EntityMetadata entityMetadata, String alias, int index, SelectTableInfoTypeEnum selectTableInfoType) {
        this.entityMetadata = entityMetadata;
        this.alias = index == 0 ? alias : alias + index;
        this.index = index;
        this.selectTableInfoType = selectTableInfoType;
        this.on=new AndPredicateSegment(true);
    }


    public String getAlias() {
        return alias;
    }

    public int getIndex() {
        return index;
    }

    public PredicateSegment getOn() {
        return on;
    }

    public String getColumnName(String attrName){
        return this.entityMetadata.getColumnName(attrName);

    }
    public <T1> String getColumnName(Property<T1, ?> column){
        String attrName = LambdaUtil.getAttrName(column);
        return this.getColumnName(attrName);
    }


    public SelectTableInfoTypeEnum getSelectTableInfoType() {
        return selectTableInfoType;
    }
    public String getSelectTableSource(){
        if(SelectTableInfoTypeEnum.LEFT_JOIN.equals(getSelectTableInfoType())){
            return  " LEFT JOIN ";
        }
        else if(SelectTableInfoTypeEnum.INNER_JOIN.equals(getSelectTableInfoType())){
            return  " INNER JOIN ";
        }
        else if(SelectTableInfoTypeEnum.RIGHT_JOIN.equals(getSelectTableInfoType())){
            return  " RIGHT JOIN ";
        }
        return " FROM ";
    }
}
