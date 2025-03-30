package com.easy.query.core.proxy.available;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.proxy.core.EntitySQLContext;

/**
 * create time 2023/12/8 15:00
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntitySQLContextAvailable {
    EntitySQLContext getEntitySQLContext();
    default @NotNull EntitySQLContext getCurrentEntitySQLContext(){
        return getEntitySQLContext().getCurrentEntitySQLContext();
    }



}
