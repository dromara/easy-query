package com.easy.query.core.basic.api.select.extension.queryable10;

import com.easy.query.core.common.tuple.Tuple10;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression10;
import com.easy.query.core.expression.parser.core.base.ColumnResultSelector;

import java.math.BigDecimal;

/**
 * create time 2023/8/15 21:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Aggregatable10<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10> {
    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLExpression10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>> columnSelectorExpression) {
        return sumBigDecimalOrDefault(columnSelectorExpression, null);
    }

    <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>> columnSelectorExpression, BigDecimal def);

    default <TMember extends Number> TMember sumOrNull(SQLExpression10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>> columnSelectorExpression) {
        return sumOrDefault(columnSelectorExpression, null);
    }

    <TMember extends Number> TMember sumOrDefault(SQLExpression10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>> columnSelectorExpression, TMember def);

    default <TMember> TMember maxOrNull(SQLExpression10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>> columnSelectorExpression) {
        return maxOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember maxOrDefault(SQLExpression10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>> columnSelectorExpression, TMember def);

    default <TMember> TMember minOrNull(SQLExpression10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>> columnSelectorExpression) {
        return minOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember minOrDefault(SQLExpression10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>> columnSelectorExpression, TMember def);

    default <TMember extends Number> Double avgOrNull(SQLExpression10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>> columnSelectorExpression) {
        return avgOrDefault(columnSelectorExpression, null, Double.class);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLExpression10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>> columnSelectorExpression) {
        return avgOrDefault(columnSelectorExpression, null, BigDecimal.class);
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLExpression10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>> columnSelectorExpression) {
        return avgOrDefault(columnSelectorExpression, null, Float.class);
    }

    default <TMember extends Number> Double avgOrDefault(SQLExpression10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>> columnSelectorExpression, Double def) {
        return avgOrDefault(columnSelectorExpression, def, Double.class);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLExpression10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>> columnSelectorExpression, BigDecimal def) {
        return avgOrDefault(columnSelectorExpression, def, BigDecimal.class);
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLExpression10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>> columnSelectorExpression, Float def) {
        return avgOrDefault(columnSelectorExpression, def, Float.class);
    }

    <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLExpression10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>> columnSelectorExpression, TResult def, Class<TResult> resultClass);



    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNullMerge(SQLExpression1<Tuple10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>>> columnSelectorExpression) {
        return sumBigDecimalOrDefaultMerge(columnSelectorExpression, null);
    }

   default  <TMember extends Number> BigDecimal sumBigDecimalOrDefaultMerge(SQLExpression1<Tuple10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>>> columnSelectorExpression, BigDecimal def){
        return sumBigDecimalOrDefault((t,t1,t2,t3,t4,t5,t6,t7,t8,t9)->{
            columnSelectorExpression.apply(new Tuple10<>(t,t1,t2,t3,t4,t5,t6,t7,t8,t9));
        },def);
   }

    default <TMember extends Number> TMember sumOrNullMerge(SQLExpression1<Tuple10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>>> columnSelectorExpression) {
        return sumOrDefaultMerge(columnSelectorExpression, null);
    }

   default  <TMember extends Number> TMember sumOrDefaultMerge(SQLExpression1<Tuple10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>>> columnSelectorExpression, TMember def){
        return sumOrDefault((t,t1,t2,t3,t4,t5,t6,t7,t8,t9)->{
            columnSelectorExpression.apply(new Tuple10<>(t,t1,t2,t3,t4,t5,t6,t7,t8,t9));
        },def);
   }

    default <TMember> TMember maxOrNullMerge(SQLExpression1<Tuple10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>>> columnSelectorExpression) {
        return maxOrDefaultMerge(columnSelectorExpression, null);
    }

   default  <TMember> TMember maxOrDefaultMerge(SQLExpression1<Tuple10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>>> columnSelectorExpression, TMember def){
        return maxOrDefault((t,t1,t2,t3,t4,t5,t6,t7,t8,t9)->{
            columnSelectorExpression.apply(new Tuple10<>(t,t1,t2,t3,t4,t5,t6,t7,t8,t9));
        },def);
   }

    default <TMember> TMember minOrNullMerge(SQLExpression1<Tuple10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>>> columnSelectorExpression) {
        return minOrDefaultMerge(columnSelectorExpression, null);
    }

   default  <TMember> TMember minOrDefaultMerge(SQLExpression1<Tuple10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>>> columnSelectorExpression, TMember def){
        return minOrDefault((t,t1,t2,t3,t4,t5,t6,t7,t8,t9)->{
            columnSelectorExpression.apply(new Tuple10<>(t,t1,t2,t3,t4,t5,t6,t7,t8,t9));
        },def);
    }

    default <TMember extends Number> Double avgOrNullMerge(SQLExpression1<Tuple10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>>> columnSelectorExpression) {
        return avgOrDefaultMerge(columnSelectorExpression, null, Double.class);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNullMerge(SQLExpression1<Tuple10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>>> columnSelectorExpression) {
        return avgOrDefaultMerge(columnSelectorExpression, null, BigDecimal.class);
    }

    default <TMember extends Number> Float avgFloatOrNullMerge(SQLExpression1<Tuple10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>>> columnSelectorExpression) {
        return avgOrDefaultMerge(columnSelectorExpression, null, Float.class);
    }

    default <TMember extends Number> Double avgOrDefaultMerge(SQLExpression1<Tuple10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>>> columnSelectorExpression, Double def) {
        return avgOrDefaultMerge(columnSelectorExpression, def, Double.class);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefaultMerge(SQLExpression1<Tuple10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>>> columnSelectorExpression, BigDecimal def) {
        return avgOrDefaultMerge(columnSelectorExpression, def, BigDecimal.class);
    }

    default <TMember extends Number> Float avgFloatOrDefaultMerge(SQLExpression1<Tuple10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>>> columnSelectorExpression, Float def) {
        return avgOrDefaultMerge(columnSelectorExpression, def, Float.class);
    }

  default   <TMember extends Number, TResult extends Number> TResult avgOrDefaultMerge(SQLExpression1<Tuple10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>>> columnSelectorExpression, TResult def, Class<TResult> resultClass){
        return avgOrDefault((t,t1,t2,t3,t4,t5,t6,t7,t8,t9)->{
            columnSelectorExpression.apply(new Tuple10<>(t,t1,t2,t3,t4,t5,t6,t7,t8,t9));
        },def,resultClass);
  }
}
