package org.easy.query.core.abstraction;

/**
 * @FileName: DefaultSqlSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/12 16:31
 * @Created by xuejiaming
 */
public class DefaultSqlSegment implements SqlSegment {
    private final StringBuilder sqlBuilder =new StringBuilder();
    @Override
    public StringBuilder getSql() {
        return sqlBuilder;
    }

    @Override
    public void append(String sql) {
        this.sqlBuilder.append(sql);
    }

    @Override
    public void append(SqlSegment sqlSegment) {
        this.append(sqlSegment.getSql().toString());
    }

    @Override
    public boolean isEmpty() {
        return this.sqlBuilder.length()==0;
    }
}
