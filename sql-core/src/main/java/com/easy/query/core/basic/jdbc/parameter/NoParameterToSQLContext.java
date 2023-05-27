//package com.easy.query.core.basic.jdbc.parameter;
//
//import com.easy.query.core.basic.jdbc.executor.internal.common.SQLRewriteUnit;
//import com.easy.query.core.expression.parser.core.available.TableAvailable;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * create time 2023/5/26 21:35
// * 文件说明
// *
// * @author xuejiaming
// */
//public class NoParameterToSQLContext implements ToSQLContext{
//    private final int tableCount;
//    private final Map<TableAvailable, String> tableAliasMapping;
//    private final SQLRewriteUnit sqlRewriteUnit;
//    private final String alias;
//    private int invokeCount;
//
//    public NoParameterToSQLContext(int tableCount, String alias) {
//        this(tableCount,null, alias);
//    }
//
//    public NoParameterToSQLContext(int tableCount) {
//        this(tableCount, "t");
//    }
//
//    public NoParameterToSQLContext(int tableCount, SQLRewriteUnit sqlRewriteUnit) {
//        this(tableCount, sqlRewriteUnit, "t");
//    }
//
//    public NoParameterToSQLContext(int tableCount,  SQLRewriteUnit sqlRewriteUnit, String alias) {
//        if (tableCount <= 0) {
//            throw new IllegalArgumentException("invalid arguments:tableCount <= 0");
//        }
//        this.tableCount = tableCount;
//        this.tableAliasMapping = tableCount == 1 ? null : new HashMap<>(tableCount);
//        this.sqlRewriteUnit = sqlRewriteUnit;
//        this.alias = alias;
//        this.invokeCount = 0;
//    }
//
//    @Override
//    public int expressionInvokeCountGetIncrement() {
//        int oldInvokeCount = invokeCount;
//        invokeCount++;
//        return oldInvokeCount;
//    }
//
//    @Override
//    public int currentInvokeCount() {
//        return invokeCount;
//    }
//
//    @Override
//    public void addParameter(SQLParameter sqlParameter) {
//
//    }
//
//    @Override
//    public List<SQLParameter> getParameters() {
//        return null;
//    }
//
//    @Override
//    public SQLRewriteUnit getSQLRewriteUnit() {
//        return sqlRewriteUnit;
//    }
//
//    @Override
//    public String getAlias(TableAvailable table) {
//        if(tableCount==1){
//            return null;
//        }
//        int aliasSeq = tableAliasMapping.size();
//
//        return tableAliasMapping.computeIfAbsent(table, k -> createAlias(aliasSeq));
//    }
//    private String createAlias(int aliasSeq){
//        if(aliasSeq==0){
//            return alias;
//        }
//        return alias+aliasSeq;
//    }
//    public static ToSQLContext defaultToSQLContext(int tableCount){
//        return new NoParameterToSQLContext(tableCount);
//    }
//}
