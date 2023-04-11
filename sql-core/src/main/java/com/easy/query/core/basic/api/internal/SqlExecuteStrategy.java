package com.easy.query.core.basic.api.internal;

import com.easy.query.core.basic.api.update.EntityUpdatable;
import com.easy.query.core.enums.SqlExecuteStrategyEnum;

/**
 * create time 2023/4/11 22:48
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SqlExecuteStrategy<TChain>  {

    default TChain setSqlStrategy(SqlExecuteStrategyEnum sqlStrategy){
        return setSqlStrategy(true,sqlStrategy);
    }
    TChain setSqlStrategy(boolean condition, SqlExecuteStrategyEnum sqlStrategy);
}
