package com.easy.query.core.basic.api.internal;

import com.easy.query.core.enums.SQLExecuteStrategyEnum;

/**
 * create time 2023/4/11 22:48
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLExecuteStrategy<TChain>  {

    default TChain setSQLStrategy(SQLExecuteStrategyEnum sqlStrategy){
        return setSQLStrategy(true,sqlStrategy);
    }
    TChain setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy);
}
