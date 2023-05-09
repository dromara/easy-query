package com.easy.query.core.sharding.initializer;

import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.ExecuteMethodEnum;

/**
 * create time 2023/5/9 08:25
 * 文件说明
 *
 * @author xuejiaming
 */
public final class ExecuteMethodBehavior {
    private static int DEFAULT= ExecuteMethodEnum.UNKNOWN.getCode();
    public static ExecuteMethodBehavior getDefault(){
        return new ExecuteMethodBehavior();
    }
    private int behavior=DEFAULT;
    public boolean isDefault(){
        return behavior==0;
    }

    public boolean hasBehavior(ExecuteMethodEnum executeMethod){
        return (behavior& executeMethod.getCode())== executeMethod.getCode();
    }

    public boolean addBehavior(ExecuteMethodEnum executeMethod){
        if(hasBehavior(executeMethod)){
            return false;
        }else{
            behavior=behavior|executeMethod.getCode();
            return true;
        }
    }
    public boolean removeBehavior(ExecuteMethodEnum executeMethod){
        if(hasBehavior(executeMethod)){
            behavior=behavior&~executeMethod.getCode();
            return true;
        }
        return false;
    }
}
