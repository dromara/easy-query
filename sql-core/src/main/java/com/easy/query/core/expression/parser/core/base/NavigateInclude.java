package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.metadata.IncludeNavigateParams;

/**
 * create time 2023/6/18 10:43
 * 文件说明
 *
 * @author xuejiaming
 */
public interface NavigateInclude {
    IncludeNavigateParams getIncludeNavigateParams();
    default <TREntity> ClientQueryable<TREntity> with(String property){
        return with(property,null);
    }
    <TREntity> ClientQueryable<TREntity> with(String property,Integer groupSize);

}
