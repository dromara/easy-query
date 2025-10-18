package com.easy.query.core.metadata;

import com.easy.query.core.enums.PartitionOrderEnum;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;

/**
 * create time 2025/10/18 13:44
 * 文件说明
 *
 * @author xuejiaming
 */
public class EndNavigateParams {
    private final NavigateMetadata entityNavigateMetadata;
    private final NavigateMetadata resultNavigateMetadata;

    public EndNavigateParams(NavigateMetadata entityNavigateMetadata) {
       this(entityNavigateMetadata,null);
    }

    public EndNavigateParams(NavigateMetadata entityNavigateMetadata, NavigateMetadata resultNavigateMetadata) {
        this.entityNavigateMetadata = entityNavigateMetadata;
        this.resultNavigateMetadata = resultNavigateMetadata;
    }

    public NavigateMetadata getEntityNavigateMetadata() {
        return entityNavigateMetadata;
    }

    public NavigateMetadata getResultNavigateMetadata() {
        return resultNavigateMetadata;
    }

    public PartitionOrderEnum getPartitionOrder() {
        if (resultNavigateMetadata != null) {
            PartitionOrderEnum partitionOrder = resultNavigateMetadata.getPartitionOrder();
            if (partitionOrder != PartitionOrderEnum.THROW) {
                return partitionOrder;
            }
        }
        return entityNavigateMetadata.getPartitionOrder();
    }
    public List<NavigateOrderProp> getOrderProps() {
        if (resultNavigateMetadata != null) {
            List<NavigateOrderProp> navigateOrderProps = resultNavigateMetadata.getOrderProps();
            if (EasyCollectionUtil.isNotEmpty(navigateOrderProps)) {
                return navigateOrderProps;
            }
        }
        return entityNavigateMetadata.getOrderProps();
    }

    public long getOffset() {
        if (resultNavigateMetadata != null) {
            if(resultNavigateMetadata.getLimit()>0){
                return resultNavigateMetadata.getOffset();
            }
        }
        return entityNavigateMetadata.getOffset();
    }

    public long getLimit() {
        if (resultNavigateMetadata != null) {
            if(resultNavigateMetadata.getLimit()>0){
                return resultNavigateMetadata.getLimit();
            }
        }
        return entityNavigateMetadata.getLimit();
    }
}
