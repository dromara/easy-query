package com.easy.query.core.annotation;

import com.easy.query.core.basic.extension.complex.ComplexPropType;
import com.easy.query.core.basic.extension.complex.DefaultComplexPropType;
import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.DefaultColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.DefaultValueConverter;
import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.basic.extension.generated.DefaultGeneratedKeySQLColumnGenerator;
import com.easy.query.core.basic.extension.generated.GeneratedKeySQLColumnGenerator;
import com.easy.query.core.basic.extension.track.update.DefaultValueUpdateAtomicTrack;
import com.easy.query.core.basic.extension.track.update.ValueUpdateAtomicTrack;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.basic.jdbc.types.handler.UnKnownTypeHandler;
import com.easy.query.core.util.EasyStringUtil;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * create time 2023/6/24 14:16
 * 映射字段到数据库列
 *
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
     * 请使用 {@param generatedKey}
     * 是否是自增键
     * 自增列不会出现在insert语句中,并且调用{@link com.easy.query.core.basic.api.insert.Insertable#executeRows(boolean)}传入true参数会自动回填返回值
     */
    @Deprecated
    boolean increment() default false;

    /**
     * 自增或者由数据库函数生成
     * 是否是数据库生成列,默认不在insert语句中如果配置了{@param generatedSQLColumnGenerator} ,并且调用{@link com.easy.query.core.basic.api.insert.Insertable#executeRows(boolean)}传入true参数会自动回填返回值
     * @return
     */
    boolean generatedKey() default false;
    /**
     * 指定实体对象映射到数据库的名称
     */
    String value() default EasyStringUtil.EMPTY;

//    /**
//     * 仅decimal有效
//     * @return
//     */
//    int precision() default 0;

//    /**
//     * 仅decimal有效小数位用于db获取后补0
//     * 暂时未用到该属性
//     * @return
//     */
//    int scale() default 0;

    /**
     * 是否是大字段 true:是 false:否
     * 如果需要默认不查询请使用{@link #autoSelect()}
     */
    @Deprecated
    boolean large() default false;

    /**
     * 值转换器 在内存中通过java代码进行转换
     */
    Class<? extends ValueConverter<?, ?>> conversion() default DefaultValueConverter.class;

    /**
     * 数据库函数列增强器 比如ase(column) des(column)等加密函数或者geo函数在数据库中进行转换
     */
    Class<? extends ColumnValueSQLConverter> sqlConversion() default DefaultColumnValueSQLConverter.class;

    /**
     * 对象原子追踪更新 生成 UPDATE TABLE SET COLUMN=COLUMN-[1] WHERE ID=XX AND COLUMN >= [1]
     * 对象更新通过差值进行动态生成原则增减值比如原始对象user.money=100，更新user.name=90那么动态生成差值10并且是递减所以生成
     * eg. UPDATE user SET money=money-10 WHERE ID=XX AND money >= 10
     */
    Class<? extends ValueUpdateAtomicTrack<?>> valueUpdateAtomicTrack() default DefaultValueUpdateAtomicTrack.class;

    /**
     * 请使用 {@param generatedSQLColumnGenerator}
     */
    @Deprecated
    Class<? extends GeneratedKeySQLColumnGenerator> incrementSQLColumnGenerator() default DefaultGeneratedKeySQLColumnGenerator.class;

    /**
     * 数据库生成键插入时生效比如 NEWID()  RAND()
     */

    Class<? extends GeneratedKeySQLColumnGenerator> generatedSQLColumnGenerator() default DefaultGeneratedKeySQLColumnGenerator.class;

    /**
     * 复杂类型
     * @return
     */
    Class<? extends ComplexPropType> complexPropType() default DefaultComplexPropType.class;

    /**
     * 当且仅当查询指定该属性才会查询出来
     * columnAll相同对象无法查询出来，除非手动指定column列
     * @return
     */
    boolean autoSelect() default true;
    Class<? extends JdbcTypeHandler> typeHandler() default UnKnownTypeHandler.class;
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
