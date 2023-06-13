package com.easy.query.core.sharding.router.descriptor;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.executor.parser.descriptor.TableEntityParseDescriptor;
import com.easy.query.core.expression.executor.parser.descriptor.TableParseDescriptor;
import com.easy.query.core.expression.executor.parser.descriptor.TablePredicateParseDescriptor;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;

/**
 * create time 2023/5/19 08:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultRouteDescriptorFactor implements RouteDescriptorFactory {
    @Override
    public RouteDescriptor createRouteDescriptor(TableAvailable table,TableParseDescriptor tableParseDescriptor) {
        if(tableParseDescriptor instanceof TableEntityParseDescriptor){
            TableEntityParseDescriptor tableEntityParseDescriptor = (TableEntityParseDescriptor) tableParseDescriptor;
            List<Object> entities = tableEntityParseDescriptor.getEntitiesNotNull(table);
            if(EasyCollectionUtil.isNotSingle(entities)){
                throw new EasyQueryInvalidOperationException("create entity route descriptor error.multi entities found:"+entities.size());
            }
            return new EntityRouteDescriptorImpl(table,EasyCollectionUtil.first(entities));
        }
        if(tableParseDescriptor instanceof TablePredicateParseDescriptor){
            TablePredicateParseDescriptor tablePredicateParseDescriptor = (TablePredicateParseDescriptor) tableParseDescriptor;
            List<PredicateSegment> predicateSegments = tablePredicateParseDescriptor.getPredicatesOrNull(table);
            return new PredicateRouteDescriptorImpl(table,predicateSegments);
        }
        throw new UnsupportedOperationException("cant create route descriptor:"+ EasyClassUtil.getInstanceSimpleName(tableParseDescriptor));
    }
}
