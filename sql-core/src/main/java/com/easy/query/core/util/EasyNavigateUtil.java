package com.easy.query.core.util;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.enums.OrderByModeEnum;
import com.easy.query.core.metadata.NavigateOrderProp;

import java.util.List;

/**
 * create time 2024/10/14 11:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyNavigateUtil {
    public static void checkProperties(String[] selfProperties, String[] targetProperties) {
        if (selfProperties == null) {
            throw new IllegalArgumentException("selfProperties is null");
        }
        if (targetProperties == null) {
            throw new IllegalArgumentException("targetProperties is null");
        }
        if (selfProperties.length > 1 || targetProperties.length > 1) {
            if (selfProperties.length != targetProperties.length) {
                throw new EasyQueryInvalidOperationException("selfProperties.length != targetProperties.length");
            }
        }
    }

    public static <T> ClientQueryable<T> navigateOrderBy(ClientQueryable<T> clientQueryable, List<NavigateOrderProp> navigateOrderProps, QueryRuntimeContext runtimeContext){
        return clientQueryable.orderBy(EasyCollectionUtil.isNotEmpty(navigateOrderProps),o -> {
            TableAvailable table = o.getTable();
            OrderSelector orderSelector = o.getOrderSelector();
            for (NavigateOrderProp orderProp : navigateOrderProps) {
                orderSelector.setAsc(orderProp.isAsc());
                OrderByModeEnum nullsModeEnum = orderProp.getMode();
                if(nullsModeEnum!=null){
                    SQLFunc fx = runtimeContext.fx();
                    SQLFunction orderByNullsModeFunction = fx.orderByNullsMode(orderProp.getProperty(), orderProp.isAsc(), nullsModeEnum);
                    orderSelector.func(table, orderByNullsModeFunction,false);
                }else{
                    orderSelector.column(table, orderProp.getProperty());
                }
            }
        },true);
    }
}
