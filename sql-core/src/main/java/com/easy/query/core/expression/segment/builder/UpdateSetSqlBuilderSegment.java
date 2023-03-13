package com.easy.query.core.expression.segment.builder;

import com.easy.query.core.enums.SqlKeywordEnum;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.expression.segment.SqlSegment;

import java.util.*;

/**
 * @FileName: UpdateSetSqlSegmentBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:15
 * @Created by xuejiaming
 */
public class UpdateSetSqlBuilderSegment extends AbstractSqlBuilderSegment {
    /**
     * 获取set 属性集合
     * @return
     */
    public Set<String> getSetProperties() {
        return setProperties;
    }

    private final Set<String> setProperties =new LinkedHashSet<>();
    @Override
    public void append(SqlSegment sqlSegment) {
        if(sqlSegment instanceof SqlEntitySegment){
            SqlEntitySegment sqlEntitySegment = (SqlEntitySegment) sqlSegment;
            setProperties.add(sqlEntitySegment.getPropertyName());
        }
        super.append(sqlSegment);
    }

    @Override
    public String toSql() {

        StringBuilder sb=new StringBuilder();
        List<SqlSegment> sqlSegments = getSqlSegments();
        if(!sqlSegments.isEmpty()){
            Iterator<SqlSegment> iterator = sqlSegments.iterator();
            SqlSegment first = iterator.next();
            sb.append(first.toSql());
            while(iterator.hasNext()){
                SqlSegment sqlSegment = iterator.next();
                sb.append(SqlKeywordEnum.DOT.toSql()).append(sqlSegment.toSql());
            }
        }
        return sb.toString();
    }
}
