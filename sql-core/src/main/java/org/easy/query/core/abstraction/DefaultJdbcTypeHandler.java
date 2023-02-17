package org.easy.query.core.abstraction;

import org.easy.query.core.executor.type.BigDecimalTypeHandler;
import org.easy.query.core.executor.type.JdbcTypeHandler;
import org.easy.query.core.executor.type.ObjectTypeHandler;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @FileName: DefaultJdbcTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 22:09
 * @Created by xuejiaming
 */
public class DefaultJdbcTypeHandler implements EasyJdbcTypeHandler{
    private static final HashMap<Class,JdbcTypeHandler> handlers=new HashMap<>();
    private static final JdbcTypeHandler DEFAULT_HANDLER=new ObjectTypeHandler();
    static{
        handlers.put(BigDecimal.class,new BigDecimalTypeHandler());
    }
    @Override
    public void appendHandler(Class type,JdbcTypeHandler typeHandler, boolean replace) {
        if(handlers.containsKey(type)){
             if(replace){
                 handlers.put(type,typeHandler);
             }
        }else{
            handlers.put(type,typeHandler);
        }
    }

    @Override
    public JdbcTypeHandler getHandler(Class type) {
        return handlers.getOrDefault(type,DEFAULT_HANDLER);
    }
}
