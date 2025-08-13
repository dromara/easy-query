package com.easy.query.core.annotation;

import com.easy.query.core.common.tuple.MergeTuple2;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.enums.SQLRangeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.exception.EasyQueryWhereInvalidOperationException;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * create time 2023/3/22 21:27
 * 默认采用like
 *
 * @author xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface EasyWhereCondition {
    /**
     * 查询条件表索引 from表index为0，join一次加1
     * SELECT * FROM table t [LEFT | RIGHT | INNER] JOIN table1 t1 ON .....
     * 如果当前字段作用到 table表 {@param tableIndex}=0 如果作用到 table1表 {@param tableIndex}=1
     *
     * @return 表索引
     */
    int tableIndex() default 0;

    /**
     * 默认不允许空字符串就是表示空字符串也不会进入条件
     * 默认 false
     *
     * @return 是否允许
     */
    boolean allowEmptyStrings() default false;

    /**
     * 映射的属性名称,空表示原属性名称
     *
     * @return 属性名
     */
    String propName() default "";

    /**
     * 映射属性名多个
     *
     * @return
     */
    String[] propNames() default {};

    /**
     * 映射属性对应的表数组索引对应空为对应属性0
     *
     * @return
     */
    int[] tablesIndex() default {};

    /**
     * 模式 默认是单属性匹配
     * 多属性模式下所有属性只能在同一张表内
     * 且属性不能为空
     *
     * @return
     */
    Mode mode() default Mode.SINGLE;

    /**
     * 查询类型 默认like全匹配
     *
     * @return
     */
    Condition type() default Condition.LIKE;

    enum Condition {
        //等于
        EQUAL
        //不等于
        , NOT_EQUAL
        //大于
        , GREATER_THAN
        //小于
        , LESS_THAN
        //模糊
        , LIKE
        //左模糊 like 'word%'
        , LIKE_MATCH_LEFT
        //右模糊 like '%word'
        , LIKE_MATCH_RIGHT
        //大于等于
        , GREATER_THAN_EQUAL
        //小于等于
        , LESS_THAN_EQUAL
        //in
        , IN
        //not in
        , NOT_IN
        //大于
        , RANGE_LEFT_OPEN
        //大于等于
        , RANGE_LEFT_CLOSED
        //小于
        , RANGE_RIGHT_OPEN
        //小于等于
        , RANGE_RIGHT_CLOSED
        //equal or (a=? or a=? or a=?)
        , COLLECTION_EQUAL_OR
        //(left..right)
        , RANGE_OPEN
        //[left..right]
        , RANGE_CLOSED
        //[left..right)
        , RANGE_CLOSED_OPEN
        //(left..right]
        , RANGE_OPEN_CLOSED;


        public static MergeTuple2<SQLPredicateCompareEnum,SQLPredicateCompareEnum> getSQLPredicateCompareEnum(Condition condition) {
            switch (condition) {
                case RANGE_OPEN:
                    return new MergeTuple2<>(SQLPredicateCompareEnum.GT,SQLPredicateCompareEnum.LT);
                case RANGE_CLOSED:
                    return new MergeTuple2<>(SQLPredicateCompareEnum.GE,SQLPredicateCompareEnum.LE);
                case RANGE_CLOSED_OPEN:
                    return new MergeTuple2<>(SQLPredicateCompareEnum.GE,SQLPredicateCompareEnum.LT);
                case RANGE_OPEN_CLOSED:
                    return new MergeTuple2<>(SQLPredicateCompareEnum.GT,SQLPredicateCompareEnum.LE);
            }
            throw new EasyQueryInvalidOperationException("getSQLPredicateCompareEnum not support condition:" + condition);
        }
    }

    enum Mode {
        /**
         * 单属性匹配
         */
        SINGLE,
        /**
         * 多属性or匹配 ( a = x or b = x)
         */
        MULTI_OR
    }
}
