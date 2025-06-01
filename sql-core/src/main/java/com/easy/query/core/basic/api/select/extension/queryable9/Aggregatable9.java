package com.easy.query.core.basic.api.select.extension.queryable9;

import com.easy.query.core.common.tuple.EasyTuple9;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression9;
import com.easy.query.core.expression.parser.core.base.ColumnResultSelector;

import java.math.BigDecimal;

/**
 * create time 2023/8/15 21:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Aggregatable9<T1,T2,T3,T4,T5,T6,T7,T8,T9> {
    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLActionExpression9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>> columnSelectorExpression) {
        return sumBigDecimalOrDefault(columnSelectorExpression, null);
    }

    <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLActionExpression9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>> columnSelectorExpression, BigDecimal def);

    default <TMember extends Number> TMember sumOrNull(SQLActionExpression9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>> columnSelectorExpression) {
        return sumOrDefault(columnSelectorExpression, null);
    }

    <TMember extends Number> TMember sumOrDefault(SQLActionExpression9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>> columnSelectorExpression, TMember def);

    default <TMember> TMember maxOrNull(SQLActionExpression9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>> columnSelectorExpression) {
        return maxOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember maxOrDefault(SQLActionExpression9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>> columnSelectorExpression, TMember def);

    default <TMember> TMember minOrNull(SQLActionExpression9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>> columnSelectorExpression) {
        return minOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember minOrDefault(SQLActionExpression9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>> columnSelectorExpression, TMember def);

    default <TMember extends Number> Double avgOrNull(SQLActionExpression9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>> columnSelectorExpression) {
        return avgOrDefault(columnSelectorExpression, null, Double.class);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLActionExpression9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>> columnSelectorExpression) {
        return avgOrDefault(columnSelectorExpression, null, BigDecimal.class);
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLActionExpression9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>> columnSelectorExpression) {
        return avgOrDefault(columnSelectorExpression, null, Float.class);
    }

    default <TMember extends Number> Double avgOrDefault(SQLActionExpression9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>> columnSelectorExpression, Double def) {
        return avgOrDefault(columnSelectorExpression, def, Double.class);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLActionExpression9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>> columnSelectorExpression, BigDecimal def) {
        return avgOrDefault(columnSelectorExpression, def, BigDecimal.class);
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLActionExpression9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>> columnSelectorExpression, Float def) {
        return avgOrDefault(columnSelectorExpression, def, Float.class);
    }

    <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLActionExpression9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>> columnSelectorExpression, TResult def, Class<TResult> resultClass);



    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNullMerge(SQLActionExpression1<EasyTuple9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>>> columnSelectorExpression) {
        return sumBigDecimalOrDefaultMerge(columnSelectorExpression, null);
    }

   default  <TMember extends Number> BigDecimal sumBigDecimalOrDefaultMerge(SQLActionExpression1<EasyTuple9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>>> columnSelectorExpression, BigDecimal def){
        return sumBigDecimalOrDefault((t,t1,t2,t3,t4,t5,t6,t7,t8)->{
            columnSelectorExpression.apply(new EasyTuple9<>(t,t1,t2,t3,t4,t5,t6,t7,t8));
        },def);
   }

    default <TMember extends Number> TMember sumOrNullMerge(SQLActionExpression1<EasyTuple9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>>> columnSelectorExpression) {
        return sumOrDefaultMerge(columnSelectorExpression, null);
    }

   default  <TMember extends Number> TMember sumOrDefaultMerge(SQLActionExpression1<EasyTuple9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>>> columnSelectorExpression, TMember def){
        return sumOrDefault((t,t1,t2,t3,t4,t5,t6,t7,t8)->{
            columnSelectorExpression.apply(new EasyTuple9<>(t,t1,t2,t3,t4,t5,t6,t7,t8));
        },def);
   }

    default <TMember> TMember maxOrNullMerge(SQLActionExpression1<EasyTuple9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>>> columnSelectorExpression) {
        return maxOrDefaultMerge(columnSelectorExpression, null);
    }

   default  <TMember> TMember maxOrDefaultMerge(SQLActionExpression1<EasyTuple9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>>> columnSelectorExpression, TMember def){
        return maxOrDefault((t,t1,t2,t3,t4,t5,t6,t7,t8)->{
            columnSelectorExpression.apply(new EasyTuple9<>(t,t1,t2,t3,t4,t5,t6,t7,t8));
        },def);
   }

    default <TMember> TMember minOrNullMerge(SQLActionExpression1<EasyTuple9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>>> columnSelectorExpression) {
        return minOrDefaultMerge(columnSelectorExpression, null);
    }

   default  <TMember> TMember minOrDefaultMerge(SQLActionExpression1<EasyTuple9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>>> columnSelectorExpression, TMember def){
        return minOrDefault((t,t1,t2,t3,t4,t5,t6,t7,t8)->{
            columnSelectorExpression.apply(new EasyTuple9<>(t,t1,t2,t3,t4,t5,t6,t7,t8));
        },def);
    }

    default <TMember extends Number> Double avgOrNullMerge(SQLActionExpression1<EasyTuple9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>>> columnSelectorExpression) {
        return avgOrDefaultMerge(columnSelectorExpression, null, Double.class);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNullMerge(SQLActionExpression1<EasyTuple9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>>> columnSelectorExpression) {
        return avgOrDefaultMerge(columnSelectorExpression, null, BigDecimal.class);
    }

    default <TMember extends Number> Float avgFloatOrNullMerge(SQLActionExpression1<EasyTuple9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>>> columnSelectorExpression) {
        return avgOrDefaultMerge(columnSelectorExpression, null, Float.class);
    }

    default <TMember extends Number> Double avgOrDefaultMerge(SQLActionExpression1<EasyTuple9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>>> columnSelectorExpression, Double def) {
        return avgOrDefaultMerge(columnSelectorExpression, def, Double.class);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefaultMerge(SQLActionExpression1<EasyTuple9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>>> columnSelectorExpression, BigDecimal def) {
        return avgOrDefaultMerge(columnSelectorExpression, def, BigDecimal.class);
    }

    default <TMember extends Number> Float avgFloatOrDefaultMerge(SQLActionExpression1<EasyTuple9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>>> columnSelectorExpression, Float def) {
        return avgOrDefaultMerge(columnSelectorExpression, def, Float.class);
    }

  default   <TMember extends Number, TResult extends Number> TResult avgOrDefaultMerge(SQLActionExpression1<EasyTuple9<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>>> columnSelectorExpression, TResult def, Class<TResult> resultClass){
        return avgOrDefault((t,t1,t2,t3,t4,t5,t6,t7,t8)->{
            columnSelectorExpression.apply(new EasyTuple9<>(t,t1,t2,t3,t4,t5,t6,t7,t8));
        },def,resultClass);
  }
}
