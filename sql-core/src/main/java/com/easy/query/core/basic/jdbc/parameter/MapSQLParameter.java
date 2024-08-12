package com.easy.query.core.basic.jdbc.parameter;

import com.easy.query.core.common.MapValue;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Map;

/**
 * create time 2023/10/2 12:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class MapSQLParameter implements BeanSQLParameter {
    private final String mapKey;
    private final boolean predicate;
    private Map<String, Object> bean;

    public MapSQLParameter(String mapKey,boolean predicate) {
        this.mapKey = mapKey;
        this.predicate = predicate;
    }

    @Override
    public TableAvailable getTableOrNull() {
        return null;
    }

    @Override
    public Object getValue() {
        if (bean == null) {
            throw new EasyQueryException("cant get sql parameter value,Map." + mapKey + ",bean is null");
        }
        Object val = bean.get(mapKey);
        if(val instanceof MapValue){
            if(predicate){
                return ((MapValue)val).getPredicateValue();
            }
            return ((MapValue)val).getCurrentValue();
        }
        return val;
    }

    @Override
    public void setBean(Object bean) {
        if(!(bean instanceof Map)){
            throw new EasyQueryException("cant get sql parameter value,,Map." + mapKey + ",bean is not map");
        }
        this.bean = EasyObjectUtil.typeCastNullable(bean);
    }

    @Override
    public String getPropertyNameOrNull() {
        return null;
    }
}

