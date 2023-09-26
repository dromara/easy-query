package com.easy.query.core.expression.segment.scec.expression;

import java.util.function.Supplier;

/**
 * create time 2023/9/26 18:02
 * 文件说明
 *
 * @author xuejiaming
 */
public class NativeSQLArgument {

    private final Supplier<String> argCreator;

    public NativeSQLArgument(Supplier<String> argCreator){
        this.argCreator = argCreator;
    }
    @Override
    public String toString() {
        return argCreator.get();
    }
}
