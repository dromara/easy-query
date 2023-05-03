package com.easy.query.core.sharding.compare;

import com.easy.query.core.util.ShardingUtil;


/**
 * create time 2023/4/27 13:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class JavaLanguageShardingComparer implements ShardingComparer{
    @Override
    public int compare(Comparable<?> x, Comparable<?> y, boolean asc) {
        return ShardingUtil.safeCompare(x,y,asc,false);
    }
}
