package com.easy.query.core.expression.segment.builder;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLKeywordEnum;
import com.easy.query.core.expression.segment.SQLSegment;

import java.util.Iterator;
import java.util.List;

/**
 * @Description: 文件说明
 * create time 2023/2/13 22:39
 * @author xuejiaming
 */
public class GroupBySQLBuilderSegmentImpl extends AbstractSQLBuilderSegment {

    @Override
    public String toSQL(ToSQLContext toSQLContext) {

        StringBuilder sb=new StringBuilder();
        List<SQLSegment> sqlSegments = getSQLSegments();
        if(!sqlSegments.isEmpty()){
            Iterator<SQLSegment> iterator = sqlSegments.iterator();
            SQLSegment first = iterator.next();
            sb.append(first.toSQL(toSQLContext));
            while (iterator.hasNext()){
                SQLSegment sqlSegment = iterator.next();
                sb.append(SQLKeywordEnum.DOT.toSQL()).append(sqlSegment.toSQL(toSQLContext));
            }
        }

        return sb.toString();
    }

    @Override
    public SQLBuilderSegment cloneSQLBuilder() {
        GroupBySQLBuilderSegmentImpl groupBySQLBuilderSegment = new GroupBySQLBuilderSegmentImpl();
        copyTo(groupBySQLBuilderSegment);
        return groupBySQLBuilderSegment;
    }
}
