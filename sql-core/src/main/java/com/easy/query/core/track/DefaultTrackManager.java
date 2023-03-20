package com.easy.query.core.track;

import com.easy.query.core.abstraction.metadata.ColumnMetadata;
import com.easy.query.core.abstraction.metadata.EntityMetadata;
import com.easy.query.core.abstraction.metadata.EntityMetadataManager;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.lambda.TrackKeyFunc;
import com.easy.query.core.util.ArrayUtil;
import com.easy.query.core.util.EasyUtil;
import com.easy.query.core.util.LambdaUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @FileName: DeffaultTrackManager.java
 * @Description: 文件说明
 * @Date: 2023/3/19 17:07
 * @Created by xuejiaming
 */
public class DefaultTrackManager implements TrackManager{
    private final ThreadLocal<TrackContext> threadTC = ThreadLocal.withInitial(() -> null);
    private final EntityMetadataManager entityMetadataManager;

    public DefaultTrackManager(EntityMetadataManager entityMetadataManager){

        this.entityMetadataManager = entityMetadataManager;
    }

    @Override
    public void begin() {
        TrackContext trackContext = threadTC.get();
        if(trackContext==null){
            trackContext = new DefaultTrackContext(entityMetadataManager);
            threadTC.set(trackContext);
        }
        trackContext.begin();

    }

    @Override
    public boolean currentThreadTracking() {
          return threadTC.get()!=null;
    }

    @Override
    public TrackContext getCurrentTrackContext() {
        return threadTC.get();
    }

    @Override
    public void release() {
        TrackContext trackContext = getCurrentTrackContext();
        if(trackContext==null){
            throw new EasyQueryException("current thread not begin track");
        }
        trackContext.release();
        threadTC.remove();
    }

}
