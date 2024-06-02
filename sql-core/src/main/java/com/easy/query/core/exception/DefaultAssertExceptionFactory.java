package com.easy.query.core.exception;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.metadata.ErrorMessage;

/**
 * create time 2023/12/1 13:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultAssertExceptionFactory implements AssertExceptionFactory {
    private final EntityMetadataManager entityMetadataManager;

    public DefaultAssertExceptionFactory(EntityMetadataManager entityMetadataManager) {

        this.entityMetadataManager = entityMetadataManager;
    }

    @Override
    @NotNull
    public <T> RuntimeException createFindNotNullException(Query<T> query, String msg, String code) {
        if (msg == null && code == null) {
            EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(query.queryClass());
            ErrorMessage errorMessage = entityMetadata.getErrorMessage();
            return new EasyQueryFindNotNullException(errorMessage.getNotNull(), null);
        }
        return new EasyQueryFindNotNullException(msg, code);
    }

    @Override
    @NotNull
    public <T> RuntimeException createRequiredException(Query<T> query, String msg, String code) {
        if (msg == null && code == null) {
            EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(query.queryClass());
            ErrorMessage errorMessage = entityMetadata.getErrorMessage();
            return new EasyQueryRequiredException(errorMessage.getNotNull(), null);
        }
        return new EasyQueryRequiredException(msg, code);
    }

    @Override
    @NotNull
    public <T> RuntimeException createFirstNotNullException(Query<T> query, String msg, String code) {
        if (msg == null && code == null) {
            EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(query.queryClass());
            ErrorMessage errorMessage = entityMetadata.getErrorMessage();
            return new EasyQueryFirstNotNullException(errorMessage.getNotNull(), null);
        }
        return new EasyQueryFirstNotNullException(msg, code);
    }

    @Override
    @NotNull
    public <T> RuntimeException createSingleNotNullException(Query<T> query, String msg, String code) {
        if (msg == null && code == null) {
            EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(query.queryClass());
            ErrorMessage errorMessage = entityMetadata.getErrorMessage();
            return new EasyQuerySingleNotNullException(errorMessage.getNotNull(), null);
        }
        return new EasyQuerySingleNotNullException(msg, code);
    }

    @Override
    @NotNull
    public <T> RuntimeException createSingleMoreElementException(Query<T> query) {
        return new EasyQuerySingleMoreElementException("single query at most one element in result set.");
    }

}
