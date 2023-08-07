//package com.easy.query.core.expression.segment.core.impl;
//
//import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
//import com.easy.query.core.context.QueryRuntimeContext;
//import com.easy.query.core.expression.parser.core.available.TableAvailable;
//import com.easy.query.core.expression.segment.core.SelectSQLSegment;
//import com.easy.query.core.metadata.ColumnMetadata;
//import com.easy.query.core.util.EasySQLExpressionUtil;
//
///**
// * create time 2023/8/6 14:21
// * 文件说明
// *
// * @author xuejiaming
// */
//public class SelectSQLSegmentImpl implements SelectSQLSegment {
//    protected final TableAvailable table;
//    private final String propertyName;
//
//
//    protected ColumnMetadata columnMetadata;
//    protected final QueryRuntimeContext runtimeContext;
//    protected final String alias;
//
//    public SelectSQLSegmentImpl(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext, String alias) {
//        this.table = table;
//        this.propertyName = propertyName;
//        this.runtimeContext = runtimeContext;
//        this.alias = alias;
//    }
//    protected ColumnMetadata getColumnMetadata(){
//        if(columnMetadata==null){
//            columnMetadata=table.getEntityMetadata().getColumnNotNull(propertyName);
//        }
//        return columnMetadata;
//    }
//    @Override
//    public String toSQL(ToSQLContext toSQLContext) {
//        ColumnMetadata column = getColumnMetadata();
//        String sqlColumnSegment = EasySQLExpressionUtil.getSQLOwnerColumn(runtimeContext, table, column.getName(), toSQLContext);
//        if (alias == null) {
//            return sqlColumnSegment;
//        }
//        return sqlColumnSegment + " AS " + EasySQLExpressionUtil.getQuoteName(runtimeContext, alias);
//    }
//
//    @Override
//    public TableAvailable getTable() {
//        return table;
//    }
//
//    @Override
//    public String getPropertyName() {
//        return propertyName;
//    }
//
//    @Override
//    public String getAlias() {
//        return alias;
//    }
//
//    @Override
//    public SelectSQLSegment cloneSQLSegment() {
//        return new SelectSQLSegmentImpl(table,propertyName,runtimeContext,alias);
//    }
//}
