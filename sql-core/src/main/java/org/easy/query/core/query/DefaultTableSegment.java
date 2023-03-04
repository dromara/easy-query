//package org.easy.query.core.query;
//
//import org.easy.query.core.enums.MultiTableTypeEnum;
//import org.easy.query.core.expression.segment.condition.AndPredicateSegment;
//import org.easy.query.core.expression.segment.condition.PredicateSegment;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @FileName: AbstractTableSegment.java
// * @Description: 文件说明
// * @Date: 2023/3/3 17:17
// * @Created by xuejiaming
// */
//public class DefaultTableSegment implements SqlTableExpressionSegment {
//    private final int index;
//    private final String alias;
//    private final MultiTableTypeEnum multiTableType;
//    private List<SqlExpressionSegment> tables;
//    private PredicateSegment on;
//
//    public DefaultTableSegment(int index, String alias, MultiTableTypeEnum multiTableType){
//
//        this.index = index;
//        this.alias = alias;
//        this.multiTableType = multiTableType;
//    }
//
//    @Override
//    public boolean isEmpty() {
//        return tables == null || tables.isEmpty();
//    }
//
//    @Override
//    public void addSqlTableExpressionSegment(SqlExpressionSegment tableSegment) {
//        if (tables == null) {
//            tables = new ArrayList<>();
//        }
//        tables.add(tableSegment);
//    }
//
//    @Override
//    public PredicateSegment getOn() {
//        if(on==null){
//            on=new AndPredicateSegment(true);
//        }
//        return on;
//    }
//
//
//    @Override
//    public String getAlias() {
//        return alias;
//    }
//
//    @Override
//    public int getIndex() {
//        return index;
//    }
//
//    @Override
//    public String getSelectTableSource() {
//
//        if(MultiTableTypeEnum.LEFT_JOIN.equals(multiTableType)){
//            return  " LEFT JOIN ";
//        }
//        else if(MultiTableTypeEnum.INNER_JOIN.equals(multiTableType)){
//            return  " INNER JOIN ";
//        }
//        else if(MultiTableTypeEnum.RIGHT_JOIN.equals(multiTableType)){
//            return  " RIGHT JOIN ";
//        }
//        return " FROM ";
//    }
//    @Override
//    public String toSql() {
//        return null;
//    }
//
//}
