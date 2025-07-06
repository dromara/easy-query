package com.easy.query.cache.core.key;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.util.EasyMD5Util;
import com.easy.query.core.util.EasySQLSegmentUtil;
import com.easy.query.core.util.EasySQLUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * create time 2025/6/2 21:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultCacheKeyFactory implements CacheKeyFactory {

    @NotNull
    @Override
    public String getKey(ClientQueryable<?> entityQueryable) {

        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = entityQueryable.getSQLEntityExpressionBuilder();
        //只有单表查询形状的情况下判定是否存在关联关系表的筛选如果有那么应该将整个表达式作为一个缓存key如果没有则可以使用where条件来做关系key
        boolean hasRelationTables = sqlEntityExpressionBuilder.hasRelationTables();

        if (hasRelationTables) {
            return getRelationSQLKey(entityQueryable);
        } else {
            return getSampleSQLKey(entityQueryable);
        }
    }

    /**
     * 条件存在关联关系的sql key
     * @param entityQueryable
     * @return
     */
    private String getRelationSQLKey(ClientQueryable<?> entityQueryable) {
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = entityQueryable.getSQLEntityExpressionBuilder();
        ExpressionContext expressionContext = sqlEntityExpressionBuilder.getExpressionContext();
        ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(expressionContext.getTableContext());
        String sql = sqlEntityExpressionBuilder.toExpression().toSQL(toSQLContext);
        //不对参数进行解压如果存在valueConverter那么需要额外展开参数这边为了性能可以不进行设置
        List<SQLParameter> parameters = toSQLContext.getParameters();
        Map<String, Object> map = new LinkedHashMap<>();
        String key = sql + ":" + EasySQLUtil.sqlParameterToString(parameters);
        map.put("key", EasyMD5Util.getMD5Hash(key));
        return toJson(map);
    }

    /**
     * 无关联关系的sql key
     * @param entityQueryable
     * @return
     */
    private String getSampleSQLKey(ClientQueryable<?> entityQueryable) {

        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = entityQueryable.getSQLEntityExpressionBuilder();
        PredicateSegment sqlWhereWithQueryFilter = sqlEntityExpressionBuilder.getSQLWhereWithQueryFilter();
        ExpressionContext expressionContext = sqlEntityExpressionBuilder.getExpressionContext();
        if (sqlWhereWithQueryFilter == null || EasySQLSegmentUtil.isEmpty(sqlWhereWithQueryFilter)) {
            return "{}";
        }
        ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(expressionContext.getTableContext());
        String sql = sqlWhereWithQueryFilter.toSQL(toSQLContext);
        List<SQLParameter> parameters = toSQLContext.getParameters();
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("sql", sql);
        //直接获取参数如果存在valueConverter那么不会额外展开参数
        map.put("parameters", EasySQLUtil.sqlParameterToString(parameters));
        return toJson(map);
    }

    private String toJson(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            sb.append("\"").append(entry.getKey()).append("\":");
            Object value = entry.getValue();
            if (value instanceof Number || value instanceof Boolean) {
                sb.append(value);
            } else {
                sb.append("\"").append(value).append("\"");
            }
            if (iterator.hasNext()) {
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
