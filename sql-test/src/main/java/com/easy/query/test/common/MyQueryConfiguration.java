package com.easy.query.test.common;

import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategy;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategyEnum;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.configuration.nameconversion.NameConversion;
import com.easy.query.core.job.EasyTimeJobManager;

/**
 * create time 2024/8/12 16:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyQueryConfiguration extends QueryConfiguration {
    public MyQueryConfiguration(EasyQueryOption easyQueryOption, SQLKeyword SQLKeyWord, NameConversion nameConversion, EasyTimeJobManager easyTimeJobManager) {
        super(easyQueryOption, SQLKeyWord, nameConversion, easyTimeJobManager);
    }

//    @Override
//    public LogicDeleteStrategy getSysLogicDeleteStrategyNotNull(LogicDeleteStrategyEnum strategy) {
//        if(strategy==LogicDeleteStrategyEnum.BOOLEAN){
//            return new 我的逻辑删除();
//        }
//        return super.getSysLogicDeleteStrategyNotNull(strategy);
//    }
}
