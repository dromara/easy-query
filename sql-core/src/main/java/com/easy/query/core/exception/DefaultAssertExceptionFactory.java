package com.easy.query.core.exception;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.metadata.ErrorMessage;
import com.easy.query.core.util.EasyStringUtil;

/**
 * create time 2023/12/1 13:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultAssertExceptionFactory implements AssertExceptionFactory {
    private final EntityMetadataManager entityMetadataManager;

    public DefaultAssertExceptionFactory(EntityMetadataManager entityMetadataManager){

        this.entityMetadataManager = entityMetadataManager;
    }
    @Override
    public <T> RuntimeException createFirstNotNullException(Query<T> query, String msg, String code) {
        if(msg==null&&code==null){
            EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(query.queryClass());
            ErrorMessage errorMessage = entityMetadata.getErrorMessage();
            return new EasyQueryFirstOrNotNullException(errorMessage.getFirstNotNull(),null);
        }
        return new EasyQueryFirstOrNotNullException(msg,code);
    }

    @Override
    public <T> RuntimeException createSingleNotNullException(Query<T> query, String msg, String code) {
        if(msg==null&&code==null){
            EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(query.queryClass());
            ErrorMessage errorMessage = entityMetadata.getErrorMessage();
            return new EasyQuerySingleOrNotNullException(errorMessage.getSingleNotNull(),null);
        }
        return new EasyQuerySingleOrNotNullException(msg,code);
    }

    @Override
    public <T> RuntimeException createSingleMoreElementException(Query<T> query) {
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(query.queryClass());
        ErrorMessage errorMessage = entityMetadata.getErrorMessage();
        if(EasyStringUtil.isNotBlank(errorMessage.getSingleMoreThan())){
            return new EasyQuerySingleMoreElementException(errorMessage.getSingleMoreThan(),null);
        }
//        return new EasyQuerySingleMoreElementException("single query has more element in result set.");
        return new EasyQuerySingleMoreElementException("single query at most one element in result set.");
    }

}
