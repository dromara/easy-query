package com.easy.query.core.sharding.comparer;

import com.easy.query.core.util.EasyCompareUtil;


/**
 * create time 2023/4/27 13:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class JavaLanguageShardingComparer implements ShardingComparer{
    @Override
    public int compare(Comparable<?> x, Comparable<?> y, boolean asc) {
        return EasyCompareUtil.safeCompare(x,y,asc,false);
    }
}
