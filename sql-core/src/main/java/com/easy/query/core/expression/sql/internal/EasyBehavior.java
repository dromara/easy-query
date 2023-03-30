package com.easy.query.core.expression.sql.internal;

import com.easy.query.core.enums.EasyBehaviorEnum;

/**
 * create time 2023/3/30 08:16
 * 文件说明
 *
 * @author xuejiaming
 */
public final class EasyBehavior {
    private static int DEFAULT_BEHAVIOR=EasyBehaviorEnum.LOGIC_DELETE.getCode()|EasyBehaviorEnum.USE_INTERCEPTOR.getCode();
    private int behavior= DEFAULT_BEHAVIOR;
    public boolean isDefaultBehavior(){
        return behavior==0;
    }

    public boolean hasBehavior(EasyBehaviorEnum easyBehavior){
       return (behavior& easyBehavior.getCode())== easyBehavior.getCode();
    }

    public boolean addBehavior(EasyBehaviorEnum easyBehavior){
        if(hasBehavior(easyBehavior)){
            return false;
        }else{
            behavior=behavior|easyBehavior.getCode();
            return true;
        }
    }
    public boolean removeBehavior(EasyBehaviorEnum easyBehavior){
        if(hasBehavior(easyBehavior)){
            behavior=behavior&~easyBehavior.getCode();
            return true;
        }
        return false;
    }
}
