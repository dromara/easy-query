package com.easy.query.core.expression.builder;

import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.parser.core.available.RuntimeContextAvailable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.func.SQLFunction;

/**
 * create time 2023/6/23 13:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface GroupSelector extends SQLNative<GroupSelector>, RuntimeContextAvailable {

    GroupSelector columnKeys(TableAvailable table);
    GroupSelector column(TableAvailable table,String property);
    GroupSelector columnIgnore(TableAvailable table, String property);

    GroupSelector columnFunc(TableAvailable table,ColumnPropertyFunction columnPropertyFunction);
    GroupSelector columnFunc(TableAvailable table, SQLFunction sqlFunction);
    GroupSelector sqlSegmentAs(CloneableSQLSegment sqlColumnSegment);
}
