package com.easy.query.core.expression.segment.scec.expression;

import java.util.function.Supplier;

/**
 * create time 2023/9/26 18:02
 * sql native segment参数格式化参数支持多调用
 *
 * @author xuejiaming
 */
public class SQLFormatArgument {

    private final Supplier<String> argCreator;

    private SQLFormatArgument(Supplier<String> argCreator){
        this.argCreator = argCreator;
    }
    public static SQLFormatArgument create(Supplier<String> argCreator){
        return new SQLFormatArgument(argCreator);
    }
    @Override
    public String toString() {
        return argCreator.get();
    }
}
