package com.easy.query.core.basic.extension.conversion;

import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContext;
import com.easy.query.core.expression.segment.SQLNativeSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.util.EasyStringUtil;

/**
 * create time 2023/8/8 15:42
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLPropertyConverter extends SQLTableOwner, SQLSegment {
    default void sqlNativeSegment(String sqlSegment){
        sqlNativeSegment(sqlSegment,c->{});
    }
    void sqlNativeSegment(String sqlSegment, SQLActionExpression1<SQLNativePropertyExpressionContext> contextConsume);
    default void sqlFunction(SQLFunction sqlFunction, SQLActionExpression1<SQLNativePropertyExpressionContext> contextConsume){
        String sqlSegment = sqlFunction.sqlSegment(getTable());
        sqlNativeSegment(sqlSegment,context->{
            sqlFunction.consume(context.getSQLNativeChainExpressionContext());
            contextConsume.apply(context);
        });
    }
    default void sqlFunction(SQLFunction sqlFunction){
        String sqlSegment = sqlFunction.sqlSegment(getTable());
        sqlNativeSegment(sqlSegment,context->{
            sqlFunction.consume(context.getSQLNativeChainExpressionContext());
        });
    }

    /**
     * 使用{@link #sqlFunction(SQLFunction, SQLActionExpression1)}
     * @param table
     * @param sqlFunction
     * @param aliasProp
     */
    @Deprecated
   default void sqlNativeSegment(TableAvailable table, SQLFunction sqlFunction,String aliasProp){
       String sqlSegment = sqlFunction.sqlSegment(table);
       sqlNativeSegment(sqlSegment,context->{
           sqlFunction.consume(context.getSQLNativeChainExpressionContext());
           if(EasyStringUtil.isNotBlank(aliasProp)){
               context.setAlias(aliasProp);//因为是返回所以需要设置别名
           }
       });
   }
    SQLNativeSegment getColumnSegment();
}
