//package com.easy.query.core.expression.predicate;
//
//import com.easy.query.core.enums.MultiTableTypeEnum;
//import com.easy.query.core.expression.parser.core.available.TableAvailable;
//import com.easy.query.core.expression.segment.Column2Segment;
//import com.easy.query.core.expression.segment.ColumnSegment;
//import com.easy.query.core.expression.segment.SQLSegment;
//import com.easy.query.core.expression.segment.condition.AbstractPredicateSegment;
//import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
//import com.easy.query.core.expression.segment.condition.OrPredicateSegment;
//import com.easy.query.core.expression.segment.condition.PredicateSegment;
//import com.easy.query.core.expression.segment.condition.predicate.ColumnCollectionPredicate;
//import com.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate;
//import com.easy.query.core.expression.segment.condition.predicate.Predicate;
//import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
//import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
//import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
//import com.easy.query.core.expression.sql.builder.ExpressionContext;
//import com.easy.query.core.metadata.ColumnMetadata;
//import com.easy.query.core.metadata.EntityMetadata;
//import com.easy.query.core.util.EasyColumnSegmentUtil;
//import com.easy.query.core.util.EasyStringUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//public class SmartPredicateUtils {
//
//    /**
//     * 同一内置表or语句处理
//     */
//    public static boolean parseOrPredicate(EntityQueryExpressionBuilder entityQueryExpressionBuilder, AbstractPredicateSegment orPredicateSegment) {
//        List<ColumnSegmentInfo> infos = new ArrayList<>();
//        boolean canParse = parseOrPredicate(entityQueryExpressionBuilder, orPredicateSegment, infos);
//        if (!canParse || infos.isEmpty()) {
//            return false;
//        }
//        ColumnSegmentInfo info = infos.get(0);
//        PredicateSegment predicateSegment = getPredicateSegment(info);
//        for (ColumnSegmentInfo item : infos) {
//            Predicate predicate = item.getPredicate();
//            Column2Segment column2Segment = createColumn2Segment(item.getEntityQueryExpressionBuilder(), item.getColumnSegment());
//            if (predicate instanceof ColumnValuePredicate) {
//                ((ColumnValuePredicate)predicate).setColumn2Segment(column2Segment);
//            } else if (predicate instanceof ColumnCollectionPredicate) {
//                ((ColumnCollectionPredicate)predicate).setColumn2Segment(column2Segment);
//            }
//        }
//        predicateSegment.addPredicateSegment(orPredicateSegment);
//        return true;
//    }
//
//    /**
//     * 同一内置表or语句处理
//     */
//    private static boolean parseOrPredicate(EntityQueryExpressionBuilder entityQueryExpressionBuilder, AbstractPredicateSegment predicateSegment, List<ColumnSegmentInfo> infos) {
//        Predicate predicate = predicateSegment.getPredicate();
//        if (predicate != null) {
//            ColumnSegmentInfo info = getColumnSegmentInfo(entityQueryExpressionBuilder, predicate);
//            if (info == null) {
//                return false;
//            }
//            if (!infos.isEmpty() && infos.get(0).getTableExpressionBuilder().getEntityTable() != info.getTableExpressionBuilder().getEntityTable()) {
//                return false;
//            }
//            infos.add(info);
//        }
//        List<PredicateSegment> children = predicateSegment.getChildren();
//        if (children != null && !children.isEmpty()) {
//            for (PredicateSegment child : children) {
//                if (!(child instanceof AbstractPredicateSegment)) {
//                    return false;
//                }
//                if (!parseOrPredicate(entityQueryExpressionBuilder, (AbstractPredicateSegment) child, infos)) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    /**
//     * and语句处理
//     */
//    public static boolean parseAndPredicate(EntityQueryExpressionBuilder entityQueryExpressionBuilder, Predicate predicate) {
//        ColumnSegmentInfo info = getColumnSegmentInfo(entityQueryExpressionBuilder, predicate);
//        if (info == null) {
//            return false;
//        }
//        PredicateSegment predicateSegment = getPredicateSegment(info);
//        Column2Segment column2Segment = createColumn2Segment(info.getEntityQueryExpressionBuilder(), info.getColumnSegment());
//        if (predicate instanceof ColumnValuePredicate) {
//            ((ColumnValuePredicate)predicate).setColumn2Segment(column2Segment);
//            predicateSegment.addPredicateSegment(new AndPredicateSegment(predicate));
//            return true;
//        } else if (predicate instanceof ColumnCollectionPredicate) {
//            ((ColumnCollectionPredicate)predicate).setColumn2Segment(column2Segment);
//            predicateSegment.addPredicateSegment(new AndPredicateSegment(predicate));
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 获取条件列对应的最内层表的列信息
//     */
//    public static ColumnSegmentInfo getColumnSegmentInfo(EntityQueryExpressionBuilder entityQueryExpressionBuilder, Predicate predicate) {
//        TableAvailable table = predicate.getTable();
//        if (table == null) {
//            return null;
//        }
//        EntityMetadata entityMetadata = table.getEntityMetadata();
//        ColumnMetadata columnMetadata = entityMetadata.getColumnOrNull(predicate.getPropertyName());
//        if (columnMetadata == null) {
//            return null;
//        }
//        ColumnSegmentInfo info = getColumnSegmentInfo(entityQueryExpressionBuilder, columnMetadata.getName());
//        if (info != null) {
//            info.setPredicate(predicate);
//        }
//        return info;
//    }
//
//    /**
//     * 获取列名对应的最内层表的列信息
//     */
//    public static ColumnSegmentInfo getColumnSegmentInfo(EntityQueryExpressionBuilder entityQueryExpressionBuilder, String name) {
//        ColumnSegment columnSegment = getSelectColumnSegment(entityQueryExpressionBuilder, name);
//        EntityTableExpressionBuilder tableExpressionBuilder = getTableExpressionBuilder(entityQueryExpressionBuilder, columnSegment);
//        if (tableExpressionBuilder == null) {
//            return null;
//        }
//        MultiTableTypeEnum multiTableType = tableExpressionBuilder.getMultiTableType();
//        if (multiTableType != MultiTableTypeEnum.FROM &&
//                multiTableType != MultiTableTypeEnum.INNER_JOIN &&
//                multiTableType != MultiTableTypeEnum.LEFT_JOIN &&
//                multiTableType != MultiTableTypeEnum.RIGHT_JOIN) {
//            return null;
//        }
//        if (tableExpressionBuilder instanceof AnonymousEntityTableExpressionBuilder) {
//            EntityQueryExpressionBuilder innerEntityQueryExpressionBuilder = ((AnonymousEntityTableExpressionBuilder) tableExpressionBuilder).getEntityQueryExpressionBuilder();
//            ColumnSegmentInfo info = getColumnSegmentInfo(innerEntityQueryExpressionBuilder, columnSegment.getColumnMetadata().getName());
//            if (info != null) {
//                return info;
//            }
//        }
//        return new ColumnSegmentInfo(columnSegment, tableExpressionBuilder, entityQueryExpressionBuilder);
//    }
//
//    /**
//     * 获取列名对应的表的where条件
//     */
//    public static PredicateSegment getPredicateSegment(ColumnSegmentInfo info) {
//        AbstractPredicateSegment predicateSegment = getAbstractPredicateSegment(info);
//        if(predicateSegment.isPredicate()){
//            PredicateSegment clonePredicateSegment = predicateSegment.clonePredicateSegment();
//            predicateSegment.reset();
//            predicateSegment.addPredicateSegment(clonePredicateSegment);
//        }
//        return predicateSegment;
//    }
//
//    /**
//     * 获取列名对应的表的where条件
//     */
//    private static AbstractPredicateSegment getAbstractPredicateSegment(ColumnSegmentInfo info) {
//        EntityTableExpressionBuilder tableExpressionBuilder = info.getTableExpressionBuilder();
//        MultiTableTypeEnum multiTableType =  tableExpressionBuilder.getMultiTableType();
//        if(multiTableType == MultiTableTypeEnum.LEFT_JOIN ||
//                multiTableType == MultiTableTypeEnum.INNER_JOIN ||
//                multiTableType == MultiTableTypeEnum.RIGHT_JOIN) {
//            return (AbstractPredicateSegment) tableExpressionBuilder.getOn();
//        }
//        return  (AbstractPredicateSegment) info.getEntityQueryExpressionBuilder().getWhere();
//    }
//
//    /**
//     * 获取where列对应的内层select列 (select列可能有别名)
//     */
//    private static ColumnSegment getSelectColumnSegment(EntityQueryExpressionBuilder entityQueryExpressionBuilder, String columnName) {
//        List<SQLSegment> sqlSegments = entityQueryExpressionBuilder.getProjects().getSQLSegments();
//        for (SQLSegment sqlSegment : sqlSegments) {
//            if (sqlSegment instanceof ColumnSegment) {
//                ColumnSegment columnSegment = (ColumnSegment) sqlSegment;
//                String name = EasyStringUtil.isEmpty(columnSegment.getAlias()) ? columnSegment.getColumnMetadata().getName() : columnSegment.getAlias();
//                if (Objects.equals(name, columnName)) {
//                    return columnSegment;
//                }
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 获取select列对应的表
//     */
//    private static EntityTableExpressionBuilder getTableExpressionBuilder(EntityQueryExpressionBuilder entityQueryExpressionBuilder, ColumnSegment columnSegment) {
//        if (columnSegment != null && columnSegment.getTable() != null) {
//            for (EntityTableExpressionBuilder tableExpressionBuilder : entityQueryExpressionBuilder.getTables()) {
//                if (tableExpressionBuilder.getEntityTable() == columnSegment.getTable()) {
//                    return tableExpressionBuilder;
//                }
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 根据当前条件，创建内层表的条件
//     */
//    private static Column2Segment createColumn2Segment(EntityQueryExpressionBuilder entityQueryExpressionBuilder, ColumnSegment columnSegment) {
//        ExpressionContext expressionContext = entityQueryExpressionBuilder.getExpressionContext();
//        TableAvailable table = columnSegment.getTable();
//        ColumnMetadata columnMetadata = columnSegment.getColumnMetadata();
//        return EasyColumnSegmentUtil.createColumn2Segment(table, columnMetadata, expressionContext);
//    }
//
//    /**
//     * 追溯的最内层列
//     */
//    public static class ColumnSegmentInfo {
//
//        public ColumnSegmentInfo(ColumnSegment columnSegment, EntityTableExpressionBuilder tableExpressionBuilder, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
//            this.columnSegment = columnSegment;
//            this.tableExpressionBuilder = tableExpressionBuilder;
//            this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
//        }
//
//        /**
//         * 条件
//         */
//        private Predicate predicate;
//
//        public ColumnSegment getColumnSegment() {
//            return columnSegment;
//        }
//
//        public void setColumnSegment(ColumnSegment columnSegment) {
//            this.columnSegment = columnSegment;
//        }
//
//        public Predicate getPredicate() {
//            return predicate;
//        }
//
//        public void setPredicate(Predicate predicate) {
//            this.predicate = predicate;
//        }
//
//        public EntityTableExpressionBuilder getTableExpressionBuilder() {
//            return tableExpressionBuilder;
//        }
//
//        public void setTableExpressionBuilder(EntityTableExpressionBuilder tableExpressionBuilder) {
//            this.tableExpressionBuilder = tableExpressionBuilder;
//        }
//
//        public EntityQueryExpressionBuilder getEntityQueryExpressionBuilder() {
//            return entityQueryExpressionBuilder;
//        }
//
//        public void setEntityQueryExpressionBuilder(EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
//            this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
//        }
//
//        /**
//         * 列
//         */
//        private ColumnSegment columnSegment;
//
//        /**
//         * 表关联关系
//         */
//        private EntityTableExpressionBuilder tableExpressionBuilder;
//
//        /**
//         * 表对应的表达式部分（where）
//         */
//        private EntityQueryExpressionBuilder entityQueryExpressionBuilder;
//    }
//}
