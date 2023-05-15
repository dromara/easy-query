package com.easy.query.core.sharding.rewrite;

import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;

/**
 * create time 2023/5/14 22:09
 * 文件说明
 *
 * @author xuejiaming
 */
public class RewriteContext {

    private final int mergeBehavior;
    private final PrepareParseResult prepareParseResult;
    private final List<RewriteRouteUnit> rewriteRouteUnits;
    private final boolean isEmpty;
    private final boolean isCrossDataSource;
    private final boolean isCrossTable;
    private final boolean sequenceQuery;
    private final boolean reverseMerge;

    public RewriteContext(int mergeBehavior,PrepareParseResult prepareParseResult,List<RewriteRouteUnit> rewriteRouteUnits, boolean isCrossDataSource, boolean isCrossTable, boolean sequenceQuery,boolean reverseMerge){
        this.mergeBehavior = mergeBehavior;
        this.prepareParseResult = prepareParseResult;

        this.rewriteRouteUnits = rewriteRouteUnits;
        this.isEmpty = EasyCollectionUtil.isEmpty(rewriteRouteUnits);
        this.isCrossDataSource = isCrossDataSource;
        this.isCrossTable = isCrossTable;
        this.sequenceQuery = sequenceQuery;
        this.reverseMerge = reverseMerge;
    }

    public boolean isReverseMerge() {
        return reverseMerge;
    }

    public int getMergeBehavior() {
        return mergeBehavior;
    }

    public PrepareParseResult getPrepareParseResult() {
        return prepareParseResult;
    }

    public List<RewriteRouteUnit> getRewriteRouteUnits() {
        return rewriteRouteUnits;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public boolean isCrossDataSource() {
        return isCrossDataSource;
    }

    public boolean isCrossTable() {
        return isCrossTable;
    }

    public boolean isSequenceQuery() {
        return sequenceQuery;
    }
}
