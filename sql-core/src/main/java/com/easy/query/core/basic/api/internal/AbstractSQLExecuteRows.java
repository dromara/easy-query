package com.easy.query.core.basic.api.internal;

import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.extension.track.TrackContext;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.jdbc.conn.ConnectionManager;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.exception.AssertExceptionFactory;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;

import java.util.List;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * create time 2023/3/17 17:14
 */
public abstract class AbstractSQLExecuteRows<TChain> implements SQLExecuteExpectRows, Interceptable<TChain>, LogicDeletable<TChain> {
    private final EntityExpressionBuilder entityExpressionBuilder;

    public AbstractSQLExecuteRows(EntityExpressionBuilder entityExpressionBuilder) {

        this.entityExpressionBuilder = entityExpressionBuilder;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {

        ConnectionManager connectionManager = entityExpressionBuilder.getRuntimeContext().getConnectionManager();
        Transaction transaction = null;
        try {
            boolean inTransaction = connectionManager.currentThreadInTransaction();
            if (!inTransaction) {
                transaction = connectionManager.beginTransaction();
            }
            doExecuteRows(expectRows, msg, code);
            if (!inTransaction) {
                transaction.commit();
            }
        } finally {
            if (transaction != null) {
                transaction.close();
            }
        }
    }

    private void doExecuteRows(long expectRows, String msg, String code) {
        long rows = executeRows();
        if (rows != expectRows) {
            AssertExceptionFactory assertExceptionFactory = entityExpressionBuilder.getRuntimeContext().getAssertExceptionFactory();
            throw assertExceptionFactory.createExecuteConcurrentException(expectRows, rows, msg, code);
        }
    }

    @Override
    public TChain noInterceptor() {
        entityExpressionBuilder.getExpressionContext().noInterceptor();
        return (TChain) this;
    }

    @Override
    public TChain useInterceptor(String name) {
        entityExpressionBuilder.getExpressionContext().useInterceptor(name);
        return (TChain) this;
    }

    @Override
    public TChain noInterceptor(String name) {
        entityExpressionBuilder.getExpressionContext().noInterceptor(name);
        return (TChain) this;
    }

    @Override
    public TChain useInterceptor() {
        entityExpressionBuilder.getExpressionContext().useInterceptor();
        return (TChain) this;
    }

    @Override
    public TChain useLogicDelete(boolean enable) {
        if (enable) {
            entityExpressionBuilder.getExpressionContext().getBehavior().addBehavior(EasyBehaviorEnum.LOGIC_DELETE);
        } else {
            entityExpressionBuilder.getExpressionContext().getBehavior().removeBehavior(EasyBehaviorEnum.LOGIC_DELETE);
        }
        return (TChain) this;
    }

    public TChain batch(boolean use) {
        if (use) {
            entityExpressionBuilder.getExpressionContext().getBehavior().removeBehavior(EasyBehaviorEnum.EXECUTE_NO_BATCH);
            entityExpressionBuilder.getExpressionContext().getBehavior().addBehavior(EasyBehaviorEnum.EXECUTE_BATCH);
        } else {
            entityExpressionBuilder.getExpressionContext().getBehavior().removeBehavior(EasyBehaviorEnum.EXECUTE_BATCH);
            entityExpressionBuilder.getExpressionContext().getBehavior().addBehavior(EasyBehaviorEnum.EXECUTE_NO_BATCH);
        }
        return (TChain) this;
    }



    protected <T> void refreshTrackEntities(List<T> entities) {
        TrackContext trackContext = getTrackContextOrNull();
        if (trackContext != null) {
            for (Object trackEntity : entities) {
                EntityState trackEntityState = trackContext.getTrackEntityState(trackEntity);
                if(trackEntityState!=null){
                    trackEntityState.refresh();
                }
            }
        }
    }
    protected <T> void removeTrackEntities(List<T> entities) {
        TrackContext trackContext = getTrackContextOrNull();
        if (trackContext != null) {
            for (Object trackEntity : entities) {
                EntityState trackEntityState = trackContext.getTrackEntityState(trackEntity);
                if(trackEntityState!=null){
                    trackContext.removeTracking(trackEntity);
                }
            }
        }
    }

    protected TrackContext getTrackContextOrNull() {

        QueryRuntimeContext runtimeContext = entityExpressionBuilder.getRuntimeContext();
        TrackManager trackManager = runtimeContext.getTrackManager();
        return trackManager.currentThreadTracking() ? trackManager.getCurrentTrackContext() : null;
    }
}
