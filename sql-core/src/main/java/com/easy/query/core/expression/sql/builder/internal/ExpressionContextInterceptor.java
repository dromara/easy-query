package com.easy.query.core.expression.sql.builder.internal;

import java.util.HashSet;
import java.util.Set;

/**
 * create time 2023/7/10 16:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class ExpressionContextInterceptor {
    protected Set<String> useInterceptors;
    protected Set<String> noInterceptors;

    private void clearInterceptors(){

        if(useInterceptors!=null){
            useInterceptors.clear();
        }
        if(noInterceptors!=null){
            noInterceptors.clear();
        }
    }

    public void useInterceptor(String name){
        if(noInterceptors!=null){
            noInterceptors.remove(name);
        }
        if(useInterceptors==null){
            useInterceptors=new HashSet<>();
        }
        useInterceptors.add(name);
    }
    public void noInterceptor(String name){
        if(useInterceptors!=null){
            useInterceptors.remove(name);
        }
        if(noInterceptors==null){
            noInterceptors=new HashSet<>();
        }
        noInterceptors.add(name);
    }
    public void useInterceptor(){
        clearInterceptors();
    }

    public void noInterceptor(){
        clearInterceptors();
    }
    public boolean useContains(String name){
        if(useInterceptors==null){
            return false;
        }
        return useInterceptors.contains(name);
    }

    /**
     * 不代表不包含,表示不使用的拦截器里面是否包含
     * @param name
     * @return
     */
    public boolean noContains(String name){
        if(noInterceptors==null){
            return false;
        }
        return noInterceptors.contains(name);
    }

    public void copyTo(ExpressionContextInterceptor expressionContextInterceptor){
        if(useInterceptors!=null){
            for (String useInterceptor : useInterceptors) {
                expressionContextInterceptor.useContains(useInterceptor);
            }
        }
        if(noInterceptors!=null){
            for (String noInterceptor : noInterceptors) {
                expressionContextInterceptor.noInterceptor(noInterceptor);
            }
        }
    }
}
