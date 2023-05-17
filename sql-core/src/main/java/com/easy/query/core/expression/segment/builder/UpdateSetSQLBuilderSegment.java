package com.easy.query.core.expression.segment.builder;

import com.easy.query.core.basic.jdbc.parameter.SQLParameterCollector;
import com.easy.query.core.enums.SQLKeywordEnum;
import com.easy.query.core.expression.segment.SQLSegment;

import java.util.*;

/**
 * @FileName: UpdateSetSqlSegmentBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:15
 * @author xuejiaming
 */
public class UpdateSetSQLBuilderSegment extends AbstractSqlBuilderSegment {

    @Override
    public String toSQL(SQLParameterCollector sqlParameterCollector) {

        StringBuilder sb=new StringBuilder();
        List<SQLSegment> sqlSegments = getSQLSegments();
        if(!sqlSegments.isEmpty()){
            Iterator<SQLSegment> iterator = sqlSegments.iterator();
            SQLSegment first = iterator.next();
            sb.append(first.toSQL(sqlParameterCollector));
            while(iterator.hasNext()){
                SQLSegment sqlSegment = iterator.next();
                sb.append(SQLKeywordEnum.DOT.toSQL()).append(sqlSegment.toSQL(sqlParameterCollector));
            }
        }
        return sb.toString();
    }

    @Override
    public SQLBuilderSegment cloneSQLBuilder() {
        UpdateSetSQLBuilderSegment updateSetSqlBuilderSegment = new UpdateSetSQLBuilderSegment();
        copyTo(updateSetSqlBuilderSegment);
        return updateSetSqlBuilderSegment;
    }
}
