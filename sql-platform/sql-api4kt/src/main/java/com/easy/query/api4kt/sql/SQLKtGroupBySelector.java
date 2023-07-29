package com.easy.query.api4kt.sql;

import com.easy.query.api4kt.sql.scec.SQLKtColumnConstExpressionContext;
import com.easy.query.api4kt.sql.scec.SQLKtColumnConstExpressionContextImpl;
import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;
import kotlin.reflect.KProperty1;

/**
 * create time 2023/4/30 21:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtGroupBySelector<T1> extends EntitySQLTableOwner<T1> {
    ColumnGroupSelector<T1> getGroupBySelector();

    default TableAvailable getTable() {
        return getGroupBySelector().getTable();
    }

    default SQLKtGroupBySelector<T1> column(KProperty1<T1, ?> column) {
        getGroupBySelector().column(EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }
    default SQLKtGroupBySelector<T1> columnConst(String columnConst){
        return columnConst(columnConst,c->{});
    }
    default SQLKtGroupBySelector<T1> columnConst(String columnConst, SQLExpression1<SQLKtColumnConstExpressionContext<T1>> contextConsume){
        getGroupBySelector().columnConst(columnConst,context->{
            contextConsume.apply(new SQLKtColumnConstExpressionContextImpl<>(context));
        });
        return this;
    }

    default SQLKtGroupBySelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction) {
        getGroupBySelector().columnFunc(columnPropertyFunction);
        return this;
    }

    default <T2> SQLKtGroupBySelector<T2> then(SQLKtGroupBySelector<T2> sub) {
        return sub;
    }
}
