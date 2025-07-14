package com.easy.query.test;

import com.easy.query.core.basic.jdbc.parameter.ConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.Predicate;
import com.easy.query.core.expression.segment.condition.predicate.ValuePredicate;
import com.easy.query.core.expression.segment.condition.predicate.ValuesPredicate;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityUpdateSQLExpression;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * create time 2025/7/13 21:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class TestUpdateFlatAndPredicate extends BaseTest {

    @Test
    public void testExpressionKey() {
        EntityUpdateExpressionBuilder updateExpressionBuilder = easyEntityQuery.updatable(BlogEntity.class)
                .setColumns(t_blog -> t_blog.title().set("123"))
                .where(t_blog -> {
                    t_blog.id().eq("123123123");
                }).getUpdateExpressionBuilder();

        EntityUpdateSQLExpression expression = updateExpressionBuilder.toExpression();
        List<Object> keyValues = getKeyValues(expression);
        Assert.assertEquals(1,keyValues.size());
        Assert.assertEquals("123123123",keyValues.get(0));
    }
    private List<Object> getKeyValues(EntityUpdateSQLExpression expression){

        TableAvailable fromTable = expression.getTables().get(0).getEntityTable();
        EntityMetadata entityMetadata = fromTable.getEntityMetadata();
        Collection<String> keyProperties = entityMetadata.getKeyProperties();
        PredicateSegment where = expression.getWhere();
        List<Object> keyValues = new ArrayList<>();

        List<Predicate> flatAndPredicates = where.getFlatAndPredicates();
        for (Predicate predicate : flatAndPredicates) {

            if (predicate.getTable() == fromTable && predicate.getPropertyName() != null &&
                    (predicate.getOperator() == SQLPredicateCompareEnum.EQ || predicate.getOperator() == SQLPredicateCompareEnum.IN)) {
                if (keyProperties.contains(predicate.getPropertyName())) {
                    if (predicate.getOperator() == SQLPredicateCompareEnum.EQ) {
                        if(predicate instanceof ValuePredicate){
                            ValuePredicate valuePredicate = (ValuePredicate) predicate;
                            SQLParameter parameter = valuePredicate.getParameter();
                            if(parameter instanceof ConstSQLParameter){
                                Object value = parameter.getValue();
                                keyValues.add(value);
                            }
                        }
                    } else if (predicate.getOperator() == SQLPredicateCompareEnum.IN) {
                        if(predicate instanceof ValuesPredicate){
                            ValuesPredicate valuesPredicate = (ValuesPredicate) predicate;
                            Collection<SQLParameter> parameters = valuesPredicate.getParameters();
                            for (SQLParameter parameter : parameters) {
                                if(parameter instanceof ConstSQLParameter){
                                    Object value = parameter.getValue();
                                    keyValues.add(value);
                                }
                            }
                        }
                    }
                }
            }
        }
        return keyValues;
    }
    @Test
    public void testExpressionKey2() {
        EntityUpdateExpressionBuilder updateExpressionBuilder = easyEntityQuery.updatable(BlogEntity.class)
                .setColumns(t_blog -> t_blog.title().set("123"))
                .where(t_blog -> {
                    t_blog.id().in(Arrays.asList("1","2","3"));
                }).getUpdateExpressionBuilder();

        EntityUpdateSQLExpression expression = updateExpressionBuilder.toExpression();
        List<Object> keyValues = getKeyValues(expression);

        Assert.assertEquals(3,keyValues.size());
        Assert.assertEquals("1",keyValues.get(0));
        Assert.assertEquals("2",keyValues.get(1));
        Assert.assertEquals("3",keyValues.get(2));
    }
    @Test
    public void testExpressionKey3() {
        EntityUpdateExpressionBuilder updateExpressionBuilder = easyEntityQuery.updatable(BlogEntity.class)
                .setColumns(t_blog -> t_blog.title().set("123"))
                .where(t_blog -> {
                    t_blog.or(()->{
                        t_blog.id().eq("123123123");
                    });
                }).getUpdateExpressionBuilder();

        EntityUpdateSQLExpression expression = updateExpressionBuilder.toExpression();
        List<Object> keyValues = getKeyValues(expression);
        Assert.assertEquals(1,keyValues.size());
        Assert.assertEquals("123123123",keyValues.get(0));
    }
    @Test
    public void testExpressionKey4() {
        EntityUpdateExpressionBuilder updateExpressionBuilder = easyEntityQuery.updatable(BlogEntity.class)
                .setColumns(t_blog -> t_blog.title().set("123"))
                .where(t_blog -> {
                    t_blog.or(()->{
                        t_blog.id().eq("123123123");
                        t_blog.id().eq("1231231231");
                    });
                }).getUpdateExpressionBuilder();

        EntityUpdateSQLExpression expression = updateExpressionBuilder.toExpression();
        List<Object> keyValues = getKeyValues(expression);
        Assert.assertEquals(0,keyValues.size());
//        Assert.assertEquals("123123123",keyValues.get(0));
    }
    @Test
    public void testExpressionKey5() {
        EntityUpdateExpressionBuilder updateExpressionBuilder = easyEntityQuery.updatable(BlogEntity.class)
                .setColumns(t_blog -> t_blog.title().set("123"))
                .where(t_blog -> {
                    t_blog.id().eq("123123123");
                    t_blog.or(()->{
                        t_blog.id().eq("123123123");
                        t_blog.id().eq("1231231231");
                    });
                }).getUpdateExpressionBuilder();

        EntityUpdateSQLExpression expression = updateExpressionBuilder.toExpression();
        List<Object> keyValues = getKeyValues(expression);
        Assert.assertEquals(1,keyValues.size());
        Assert.assertEquals("123123123",keyValues.get(0));
    }
    @Test
    public void testExpressionKey6() {
        EntityUpdateExpressionBuilder updateExpressionBuilder = easyEntityQuery.updatable(BlogEntity.class)
                .setColumns(t_blog -> t_blog.title().set("123"))
                .where(t_blog -> {
                    t_blog.or(()->{
                        t_blog.id().eq("123123123");
                        t_blog.id().eq("1231231231");
                        t_blog.and(()->{

                            t_blog.id().eq("123123123");
                        });
                    });
                }).getUpdateExpressionBuilder();

        EntityUpdateSQLExpression expression = updateExpressionBuilder.toExpression();
        List<Object> keyValues = getKeyValues(expression);
        Assert.assertEquals(0,keyValues.size());
//        Assert.assertEquals("123123123",keyValues.get(0));
    }
    @Test
    public void testExpressionKey7() {
        EntityUpdateExpressionBuilder updateExpressionBuilder = easyEntityQuery.updatable(BlogEntity.class)
                .setColumns(t_blog -> t_blog.title().set("123"))
                .where(t_blog -> {
                    t_blog.or(()->{
                        t_blog.id().eq("123123123");
                        t_blog.id().eq("1231231231");
                        t_blog.and(()->{

                            t_blog.id().eq("123123123");
                            t_blog.id().eq("1231231231");
                        });
                    });
                }).getUpdateExpressionBuilder();

        EntityUpdateSQLExpression expression = updateExpressionBuilder.toExpression();
        List<Object> keyValues = getKeyValues(expression);
        Assert.assertEquals(0,keyValues.size());
//        Assert.assertEquals("123123123",keyValues.get(0));
    }
    @Test
    public void testExpressionKey8() {
        EntityUpdateExpressionBuilder updateExpressionBuilder = easyEntityQuery.updatable(Topic.class)
                .setColumns(t -> t.title().set("123"))
                .where(t -> {
                    t.or(()->{
                        t.id().eq("123123123");
                        t.id().eq("1231231231");
                        t.and(()->{

                            t.id().eq("123123123");
                            t.id().eq("1231231231");
                        });
                    });
                }).getUpdateExpressionBuilder();

        EntityUpdateSQLExpression expression = updateExpressionBuilder.toExpression();
        List<Object> keyValues = getKeyValues(expression);
        Assert.assertEquals(0,keyValues.size());
//        Assert.assertEquals("123123123",keyValues.get(0));
    }
}
