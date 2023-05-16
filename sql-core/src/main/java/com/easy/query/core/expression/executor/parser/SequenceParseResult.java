package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.sharding.route.table.TableUnit;

import java.util.Comparator;

/**
 * create time 2023/5/8 08:56
 * 文件说明
 *
 * @author xuejiaming
 */
public class SequenceParseResult {

    private final TableAvailable table;
    private final Comparator<TableUnit> tableComparator;
    private final boolean reverse;
    private final ConnectionModeEnum connectionMode;
    private final int connectionsLimit;

    public SequenceParseResult(TableAvailable table, Comparator<TableUnit> tableComparator, boolean reverse,ConnectionModeEnum connectionMode, int connectionsLimit){
        this.table = table;
        this.tableComparator = tableComparator;

        this.reverse = reverse;
        this.connectionMode = connectionMode;
        this.connectionsLimit = connectionsLimit;
    }

    public TableAvailable getTable() {
        return table;
    }

    public Comparator<TableUnit> getTableComparator() {
        return tableComparator;
    }

    public boolean isReverse() {
        return reverse;
    }

    public int getConnectionsLimit() {
        return connectionsLimit;
    }

    public ConnectionModeEnum getConnectionMode() {
        return connectionMode;
    }
}
