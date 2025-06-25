package com.easy.query.search.param;

import java.util.List;
import java.util.Set;

/**
 * 只读参数字典
 *
 * @author bkbits
 */
public class UnmodifiableParamMap implements ParamMap {
    private final ParamMap paramMap;

    public UnmodifiableParamMap(ParamMap paramMap) {
        this.paramMap = paramMap;
    }

    @Override
    public boolean writable() {
        return false;
    }

    @Override
    public Object getParam(String name) {
        return paramMap.getParam(name);
    }

    @Override
    public Set<String> getParamNames() {
        return paramMap.getParamNames();
    }

    @Override
    public List<Object> getParamValues(String name) {
        return paramMap.getParamValues(name);
    }

    @Override
    public ParamMap setParam(String name, Object value) {
        throw new UnsupportedOperationException("This ParamMap is read-only and cannot be modified.");
    }

    @Override
    public ParamMap setParam(String name, List<Object> value) {
        throw new UnsupportedOperationException("This ParamMap is read-only and cannot be modified.");
    }

    @Override
    public ParamMap addParam(String name, Object value) {
        throw new UnsupportedOperationException("This ParamMap is read-only and cannot be modified.");
    }

    @Override
    public ParamMap clear() {
        throw new UnsupportedOperationException("This ParamMap is read-only and cannot be modified.");
    }

    @Override
    public ParamMap removeParam(String name) {
        throw new UnsupportedOperationException("This ParamMap is read-only and cannot be modified.");
    }
}
