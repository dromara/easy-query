package com.easy.query.api.lambda;

import com.easy.query.api.lambda.client.EasyLambdaQueryClient;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.delete.ClientEntityDeletable;
import com.easy.query.core.basic.api.delete.ClientExpressionDeletable;
import com.easy.query.core.basic.api.flat.MapQueryable;
import com.easy.query.core.basic.api.insert.ClientInsertable;
import com.easy.query.core.basic.api.insert.map.MapClientInsertable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
import com.easy.query.core.basic.api.update.map.MapClientUpdatable;
import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.configuration.LoadIncludeConfiguration;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.lambda.SQLExpression1;

import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main
{
    public static void main(String[] args)
    {
        int i = 0;
        String input = "{}";
        // 正则表达式匹配 {}
        Pattern pattern = Pattern.compile("\\{}");
        Matcher matcher = pattern.matcher(input);
        // 使用StringBuilder来构建新的字符串
        StringBuilder sb = new StringBuilder(input.length());
        // 遍历输入字符串
        int prevEnd = 0; // 上一个匹配的结束位置
        while (matcher.find())
        {
            // 将从上一个匹配结束到当前匹配开始的部分添加到StringBuilder中
            sb.append(input.substring(prevEnd, matcher.start()));
            // 替换 {} 为 {index}
            sb.append("{").append(i++).append("}");
            // 更新上一个匹配的结束位置为当前匹配的结束位置
            prevEnd = matcher.end();
        }
        // 如果输入字符串的末尾有未匹配的部分，将其添加到StringBuilder中
        if (prevEnd < input.length())
        {
            sb.append(input.substring(prevEnd));
        }

        System.out.println(sb.toString());


        EasyLambdaQueryClient client=new EasyLambdaQueryClient(new EasyQueryClient()
        {
            @Override
            public QueryRuntimeContext getRuntimeContext()
            {
                return null;
            }

            @Override
            public <T> List<T> sqlEasyQuery(String sql, Class<T> clazz, List<SQLParameter> parameters)
            {
                return Collections.emptyList();
            }

            @Override
            public List<Map<String, Object>> sqlQueryMap(String sql, List<Object> parameters)
            {
                return Collections.emptyList();
            }

            @Override
            public long sqlExecute(String sql, List<Object> parameters)
            {
                return 0;
            }

            @Override
            public <T> ClientQueryable<T> queryable(Class<T> clazz)
            {
                return null;
            }

            @Override
            public MapQueryable mapQueryable()
            {
                return null;
            }

            @Override
            public MapQueryable mapQueryable(String sql)
            {
                return null;
            }

            @Override
            public <T> ClientQueryable<T> queryable(String sql, Class<T> clazz, Collection<Object> sqlParams)
            {
                return null;
            }

            @Override
            public Transaction beginTransaction(Integer isolationLevel)
            {
                return null;
            }

            @Override
            public <T> ClientInsertable<T> insertable(T entity)
            {
                return null;
            }

            @Override
            public <T> ClientInsertable<T> insertable(Collection<T> entities)
            {
                return null;
            }

            @Override
            public <T> ClientExpressionUpdatable<T> updatable(Class<T> entityClass)
            {
                return null;
            }

            @Override
            public <T> ClientEntityUpdatable<T> updatable(T entity)
            {
                return null;
            }

            @Override
            public <T> ClientEntityUpdatable<T> updatable(Collection<T> entities)
            {
                return null;
            }

            @Override
            public <T> ClientEntityDeletable<T> deletable(T entity)
            {
                return null;
            }

            @Override
            public <T> ClientEntityDeletable<T> deletable(Collection<T> entities)
            {
                return null;
            }

            @Override
            public <T> ClientExpressionDeletable<T> deletable(Class<T> entityClass)
            {
                return null;
            }

            @Override
            public boolean addTracking(Object entity)
            {
                return false;
            }

            @Override
            public boolean removeTracking(Object entity)
            {
                return false;
            }

            @Override
            public EntityState getTrackEntityStateNotNull(Object entity)
            {
                return null;
            }

            @Override
            public MapClientInsertable<Map<String, Object>> mapInsertable(Map<String, Object> map)
            {
                return null;
            }

            @Override
            public MapClientInsertable<Map<String, Object>> mapInsertable(Collection<Map<String, Object>> maps)
            {
                return null;
            }

            @Override
            public MapClientUpdatable<Map<String, Object>> mapUpdatable(Map<String, Object> map)
            {
                return null;
            }

            @Override
            public MapClientUpdatable<Map<String, Object>> mapUpdatable(Collection<Map<String, Object>> maps)
            {
                return null;
            }

            @Override
            public <T> void loadInclude(List<T> entities, String navigateProperty, SQLExpression1<LoadIncludeConfiguration> configure)
            {

            }
        }, MethodHandles.lookup());

        long aaa = client.updatable(Thread.class).set(a ->
        {
            a.setName("aaa");
        }).executeRows();
    }
}
