package com.easy.query.core.proxy.sql.include;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2025/3/3 21:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class IncludeExpression {
    private List<IncludeManyAvailable> includeManyAvailableList=new ArrayList<>();
    public void append(IncludeManyAvailable includeManyAvailable){
        includeManyAvailableList.add(includeManyAvailable);
    }
}
