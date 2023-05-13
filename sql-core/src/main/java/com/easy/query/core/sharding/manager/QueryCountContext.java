package com.easy.query.core.sharding.manager;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/5/12 21:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryCountContext {
    private final List<QueryCountResult> results=new ArrayList<>();
    public void addCountResult(QueryCountResult queryCountResult){
        results.add(queryCountResult);
    }
    public List<QueryCountResult> getCountResult(){
        return results;
    }
}
