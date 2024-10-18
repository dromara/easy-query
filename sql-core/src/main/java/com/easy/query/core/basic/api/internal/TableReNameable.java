package com.easy.query.core.basic.api.internal;

import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.util.EasyStringUtil;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * create time 2023/4/2 22:01
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableReNameable<TChain> {
    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     *
     * @param tableName 新的表名
     * @return
     * @throws IllegalArgumentException tableName为null时抛错
     */
    default TChain asTable(String tableName) {
        if (EasyStringUtil.isBlank(tableName)) {
            throw new IllegalArgumentException("tableName is empty");
        }
        return asTable(old -> tableName);
    }

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableNameAs}返回的表名
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表则不更改
     * asTable(old->old+xxx)
     *
     * @param tableNameAs 通过旧的表名生成新的表名
     * @return
     */
    TChain asTable(Function<String, String> tableNameAs);

    /**
     * 将当前表达式最近的一张表的schema修改成 {@param schema}
     * 如果当前最近的表是正常的数据库表名,那么直接将表schema改写
     *
     * @param schema 新的schema
     * @return
     * @throws IllegalArgumentException schema为null时抛错
     */

    default TChain asSchema(String schema) {
        return asSchema(old -> schema);
    }

    /**
     * 将当前表达式最近的一张表的schema修改成 {@param schema}
     * 如果当前最近的表是正常的数据库表名,那么直接将表schema改写
     *
     * @param schemaAs 通过旧的schema生成新的schema
     * @return
     * @throws IllegalArgumentException schema为null时抛错
     *                                  asSchema(old->old+xxx)
     */
    TChain asSchema(Function<String, String> schemaAs);

    /**
     * 将当前表达式最近的一张表的别名进行指定
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     * select * from table a 其中a就是alias
     *
     * @param alias 别名
     * @return
     */
    TChain asAlias(String alias);


    /**
     * 生成新的表连接副比如left join可以改成 left out join
     *
     * @param linkAs 别名 FROM | LEFT JOIN | RIGHT JOIN
     * @return
     */
    default TChain asTableLink(String linkAs) {
        return asTableLink(o -> linkAs);
    }

    /**
     * 通过旧的表达式连接符生成新的连接符
     *
     * @param linkAs
     * @return
     */
    TChain asTableLink(Function<String, String> linkAs);


    default TChain asTableSegment(String segmentAs) {
        return asTableSegment((table, alias) -> segmentAs);
    }

    TChain asTableSegment(BiFunction<String, String, String> segmentAs);

}
