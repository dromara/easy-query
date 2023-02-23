//package org.easy.query.core.impl.lambda;
//
//
//import org.easy.query.core.abstraction.SqlSegment;
//
///**
// * @FileName: PredicateBuilder.java
// * @Description: 文件说明
// * @Date: 2023/2/7 14:14
// * @Created by xuejiaming
// */
//public abstract class SelectorBuilder extends AbstractSqlSegmentBuilder {
//    public static final String LINK = ", ";
//
//    public SelectorBuilder(SqlSegment sqlSegment) {
//        super(sqlSegment);
//    }
//    /**
//     * 拼接SQL
//     *
//     * @param sqlPart
//     */
//    public SelectorBuilder appendSql(String sqlPart) {
//        getSqlSegment().append(sqlPart);
//        return this;
//    }
//
//    /**
//     *
//     * @param columnOwner
//     * @param column
//     */
//    protected void appendSelectSql(String columnOwner,String column) {
//        //判断是否有效的变量
//        String link=getSqlSegment().isEmpty()?"":LINK;
//        this.appendSql(link).appendSql(getCol(columnOwner,column));
//    }
//    /**
//     * 获取字段 加上前后空格
//     *
//     * @param columnOwner
//     * @param colName
//     * @return
//     */
//    protected String getCol(String columnOwner,String colName) {
//        return columnOwner+"."+colName;
//    }
//
//}
