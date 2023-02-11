package org.jdqc.core.query.builder;

import org.jdqc.core.abstraction.metadata.EntityMetadata;
import org.jdqc.core.enums.SelectTableInfoTypeEnum;
import org.jdqc.core.metadata.TableInfo;

/**
 * @FileName: SelectTableInfo.java
 * @Description: 文件说明
 * @Date: 2023/2/7 11:36
 * @Created by xuejiaming
 */
public class SelectTableInfo {
    private final String alias;
    private final StringBuilder on;
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
        this.on=new StringBuilder();
    }


    public String getAlias() {
        return alias;
    }

    public int getIndex() {
        return index;
    }

    public StringBuilder getOn() {
        return on;
    }

    public String getColumnName(String attrName){
        return this.entityMetadata.getColumnName(attrName);

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
