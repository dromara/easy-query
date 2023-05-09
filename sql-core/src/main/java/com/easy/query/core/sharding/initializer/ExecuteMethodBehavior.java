package com.easy.query.core.sharding.initializer;

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
    private int methods =DEFAULT;
    private int orders=DEFAULT;
    public boolean isDefault(){
        return methods ==0;
    }

    public boolean hasMethod(ExecuteMethodEnum executeMethod){
        return (methods & executeMethod.getCode())== executeMethod.getCode();
    }

    public boolean addMethod(ExecuteMethodEnum executeMethod){
        if(hasMethod(executeMethod)){
            return false;
        }else{
            methods = methods |executeMethod.getCode();
            return true;
        }
    }
    public boolean removeMethod(ExecuteMethodEnum executeMethod){
        if(hasMethod(executeMethod)){
            methods = methods &~executeMethod.getCode();
            return true;
        }
        return false;
    }
    public boolean hasMethodAsc(ExecuteMethodEnum executeMethod){
        return (orders & executeMethod.getCode())== executeMethod.getCode();
    }

    public boolean addMethodAsc(ExecuteMethodEnum executeMethod){
        if(hasMethod(executeMethod)){
            return false;
        }else{
            orders = orders |executeMethod.getCode();
            return true;
        }
    }
    public boolean removeMethodAsc(ExecuteMethodEnum executeMethod){
        if(hasMethod(executeMethod)){
            orders = orders &~executeMethod.getCode();
            return true;
        }
        return false;
    }
}
