package com.easy.query.core.annotation;
import com.easy.query.core.basic.plugin.conversion.DefaultValueConverter;
import com.easy.query.core.basic.plugin.conversion.ValueConverter;
import com.easy.query.core.util.EasyStringUtil;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

/**
 * @FileName: Column.java
 * @Description: 文件说明
 * @Date: 2023/2/10 23:07
 * @author xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface Column {
    /**
     * 是否是主键
     * 主键列用于对象更新、删除操作
     */
    boolean primaryKey() default false;
    /**
     * 是否是自增键
     * 自增列不会出现在insert语句中,并且调用{@link com.easy.query.core.basic.api.insert.Insertable#executeRows(boolean)}传入true参数会自动回填返回值
     */
    boolean increment() default false;
    /**
     * 指定实体对象映射到数据库的名称
     */
    String value() default EasyStringUtil.EMPTY;

//    /**
//     * 仅decimal有效
//     * @return
//     */
//    int precision() default 0;

    /**
     * 仅decimal有效小数位用于db获取后补0
     * @return
     */
    int scale() default 0;

    /**
     * 是否是大字段
     * @return
     */
    boolean large() default false;
    Class<? extends ValueConverter> conversion() default DefaultValueConverter.class;

//    /**
//     * 当且仅当查询指定该属性才会查询出来
//     * columnAll相同对象无法查询出来，如果是不同对象的映射columnAll则可以
//     * 相同对象columnAll后需要在column指定列
//     * @return
//     */
//    boolean select() default true;
//
//    /**
//     * 该字段在数据库中是否为null，如果为null那么在update整个对象的时候
//     * 如果nullable为null就会更新为null
//     * 如果nullable不为null那么属性为null将不会更新
//     * 如果属性是PrimaryKey那么一定不为null
//     */
//    boolean nullable() default true;

//    /**
//     * 是否是主键
//     */
//    boolean primary() default false;
//
//    /**
//     * 是否是自增键
//     */
//    boolean increment() default false;
//
//    /**
//     * 乐观锁
//     */
//    boolean version() default false;
}
