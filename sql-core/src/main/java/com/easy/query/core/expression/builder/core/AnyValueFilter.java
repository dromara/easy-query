package com.easy.query.core.expression.builder.core;

import org.jetbrains.annotations.NotNull;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import org.jetbrains.annotations.Nullable;

/**
 * create time 2023/8/19 15:06
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnyValueFilter implements ValueFilter {
    public static final ValueFilter DEFAULT=new AnyValueFilter();
    private AnyValueFilter(){

    }
    @Override
    public boolean accept(@Nullable TableAvailable table, @Nullable String property, Object value) {
        return true;
    }
}
