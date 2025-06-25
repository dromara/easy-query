package com.easy.query.core.api.dynamic.executor.search.param;

import java.util.*;

public class ParamHashMap implements ParamMap {
    private final Map<String, List<Object>> params = new HashMap<>();

    @Override
    public boolean writable() {
        return true;
    }

    @Override
    public Set<String> getParamNames() {
        return params.keySet();
    }

    @Override
    public List<Object> getParamValues(String name) {
        return params.get(name);
    }

    @Override
    public ParamMap setParam(String name, Object value) {
        List<Object> values = params.get(name);
        if (values == null) {
            values = new ArrayList<>();
            params.put(name, values);
        } else {
            values.clear();
        }
        values.add(value);
        return this;
    }

    @Override
    public ParamMap setParam(String name, List<Object> value) {
        params.put(name, value);
        return this;
    }

    @Override
    public ParamMap addParam(String name, Object value) {
        params.computeIfAbsent(name, k -> new ArrayList<>()).add(value);
        return this;
    }

    @Override
    public ParamMap clear() {
        params.clear();
        return this;
    }

    @Override
    public ParamMap removeParam(String name) {
        params.remove(name);
        return this;
    }
}
