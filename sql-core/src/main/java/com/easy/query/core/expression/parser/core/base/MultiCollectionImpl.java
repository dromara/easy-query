package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * create time 2024/10/17 12:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class MultiCollectionImpl implements MultiCollection {

    private final List<List<Object>> relationIds;
    private final List<Object> singleCollection;

    public MultiCollectionImpl(List<List<Object>> relationIds) {
        this.relationIds = relationIds;
        this.singleCollection =new ArrayList<>();
    }

    @Override
    public Collection<Object> singleCollection() {
        return getSingleFromNestCollection();
    }

    private Collection<Object> getSingleFromNestCollection() {
        singleCollection.clear();
        for (List<Object> objects : relationIds) {
            if (objects == null) {
                throw new EasyQueryInvalidOperationException("nest collection has null element");
            }
            if (objects.size() != 1) {
                throw new EasyQueryInvalidOperationException("nest collection element.size() != 1");
            }
            singleCollection.add(EasyCollectionUtil.first(objects));
        }
        return singleCollection;
    }

    @Override
    public Collection<List<Object>> multiCollection() {
        return relationIds;
    }
}
