package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.builder.Setter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.SQLColumnSetExpression;

/**
 * create time 2023/12/8 10:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLColumnIncrementImpl implements SQLColumnSetExpression {

    private final TableAvailable table;
    private final String property;
    private final Number val;

    public SQLColumnIncrementImpl(TableAvailable table, String property, Number val){
        this.table = table;
        this.property = property;
        this.val = val;
    }

    @Override
    public void accept(Setter s) {
        s.setIncrement(table, property, val);
    }

    @Override
    public void accept(Selector s) {
//        String increment = val.toString();
//        s.sqlNativeSegment("{0} + {1}",c->{
//            c.expression(table,property);
//            c.value(increment);
//            c.setPropertyAlias(property);
//        });
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(AsSelector s) {
//        String increment = val.toString();
//        s.sqlNativeSegment("{0} + {1}",c->{
//            c.expression(table,property);
//            c.value(increment);
//            c.setPropertyAlias(property);
//        });
        throw new UnsupportedOperationException();
    }

}
