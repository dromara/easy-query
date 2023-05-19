package com.easy.query.core.util;

import com.easy.query.core.expression.executor.parser.descriptor.TablePredicateParseDescriptor;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.SubQueryPredicate;
import com.easy.query.core.expression.sql.expression.AnonymousEntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.EntityPredicateSQLExpression;
import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;

import java.util.HashSet;

/**
 * create time 2023/5/18 21:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyParseUtil {
    private static void parseTableByPredicateSegment(PredicateSegment predicateSegment, TablePredicateParseDescriptor tablePredicateParseDescriptor) {

        if (EasySQLSegmentUtil.isNotEmpty(predicateSegment)) {
            HashSet<TableAvailable> relateTables = new HashSet<>();
            predicateSegment.forEach(predicate -> {
                if(predicate instanceof SubQueryPredicate){
                    SubQueryPredicate subQueryPredicate = (SubQueryPredicate) predicate;
                    EntityQuerySQLExpression entityQuerySQLExpression = subQueryPredicate.getSubQueryable().getSQLEntityExpressionBuilder().toExpression();
                    getTablePredicateParseDescriptor(entityQuerySQLExpression,tablePredicateParseDescriptor);
                }
                if (tablePredicateParseDescriptor.isShardingTable(predicate.getTable())) {
                    relateTables.add(predicate.getTable());
                }
                
                return false;
            });
            for (TableAvailable relateTable : relateTables) {
                tablePredicateParseDescriptor.addTablePredicate(relateTable, predicateSegment);
            }
        }
    }

    public static void getTablePredicateParseDescriptor(EntityPredicateSQLExpression entityPredicateSQLExpression, TablePredicateParseDescriptor tablePredicateParseDescriptor) {

        for (EntityTableSQLExpression table : entityPredicateSQLExpression.getTables()) {
            parseTableAndPredicates(tablePredicateParseDescriptor, table);
        }
        parseTableByPredicateSegment(entityPredicateSQLExpression.getWhere(),tablePredicateParseDescriptor);
    }

    private static void parseTableAndPredicates(TablePredicateParseDescriptor tablePredicateParseDescriptor, EntityTableSQLExpression table) {

        if (table instanceof AnonymousEntityTableSQLExpression) {
            getAnonymousTable(tablePredicateParseDescriptor, (AnonymousEntityTableSQLExpression) table);
        } else {
            if (!table.tableNameIsAs() && table.getEntityMetadata().isSharding()) {
                tablePredicateParseDescriptor.addTable(table.getEntityTable());
            }
            parseTableByPredicateSegment(table.getOn(),tablePredicateParseDescriptor);
        }
    }

    private static void getAnonymousTable(TablePredicateParseDescriptor tablePredicateParseDescriptor, AnonymousEntityTableSQLExpression anonymousEntityTableSQLExpression) {
        EntityQuerySQLExpression entityQuerySQLExpression = anonymousEntityTableSQLExpression.getEntityQuerySQLExpression();
        for (EntityTableSQLExpression table : entityQuerySQLExpression.getTables()) {
            parseTableAndPredicates(tablePredicateParseDescriptor, table);
        }
    }
}
