package com.easy.query.test;

import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.enums.ContextTypeEnum;
import com.easy.query.core.enums.QueryLockEnum;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.exception.EasyQueryNoPrimaryKeyException;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.expression.sql.builder.EasyExpressionContext;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.test.encryption.Base64EncryptionStrategy;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.NoKeyEntity;
import com.easy.query.test.entity.UnknownTable;
import com.easy.query.test.entity.notable.QueryLargeColumnTestEntity;
import com.easy.query.test.increment.MyDatabaseIncrementSQLColumnGenerator;
import com.easy.query.test.interceptor.MyEntityInterceptor;
import com.easy.query.test.logicdel.MyLogicDelStrategy;
import org.junit.Assert;

import java.sql.SQLException;

/**
 * @author xuejiaming
 * @FileName: GenericTest.java
 * @Description: 文件说明
 * create time 2023/3/17 22:22
 */
public class GenericWithDatabaseTest extends BaseTest {
    @org.junit.Test
    public void queryLargeColumnTest1() {
        String sql = easyEntityQuery.queryable(QueryLargeColumnTestEntity.class).toSQL();
        Assert.assertEquals("SELECT `id`,`name`,`content` FROM `query_large_column_test`", sql);
    }

    @org.junit.Test
    public void queryLargeColumnTest3() {
        try {

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            long l = easyEntityQuery.insertable(queryLargeColumnTestEntity).executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex.getMessage().startsWith("not found insert columns :"));
        }
    }

    @org.junit.Test
    public void queryLargeColumnTest4() {
        try {

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyEntityQuery.insertable(queryLargeColumnTestEntity).executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("INSERT INTO `query_large_column_test` (`id`) VALUES (?)", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'easy-query-test.query_large_column_test' doesn't exist", ex1.getMessage());
        }
    }

    @org.junit.Test
    public void queryLargeColumnTest5() {
        try {

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyEntityQuery.insertable(queryLargeColumnTestEntity).setSQLStrategy(SQLExecuteStrategyEnum.DEFAULT).executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("INSERT INTO `query_large_column_test` (`id`) VALUES (?)", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'easy-query-test.query_large_column_test' doesn't exist", ex1.getMessage());
        }
    }

    @org.junit.Test
    public void queryLargeColumnTest6() {
        try {

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyEntityQuery.insertable(queryLargeColumnTestEntity).setSQLStrategy(SQLExecuteStrategyEnum.ALL_COLUMNS).executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("INSERT INTO `query_large_column_test` (`id`,`name`,`content`) VALUES (?,?,?)", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'easy-query-test.query_large_column_test' doesn't exist", ex1.getMessage());
        }
    }

    @org.junit.Test
    public void queryLargeColumnTest7() {
        try {

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyEntityQuery.insertable(queryLargeColumnTestEntity).setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS).executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("INSERT INTO `query_large_column_test` (`name`,`content`) VALUES (?,?)", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'easy-query-test.query_large_column_test' doesn't exist", ex1.getMessage());
        }
    }

    @org.junit.Test
    public void queryLargeColumnTest8() {
        try {

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyEntityQuery.updatable(queryLargeColumnTestEntity).executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("UPDATE `query_large_column_test` SET `name` = ?,`content` = ? WHERE `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'easy-query-test.query_large_column_test' doesn't exist", ex1.getMessage());
        }
    }

    @org.junit.Test
    public void queryLargeColumnTest9() {
        QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
        queryLargeColumnTestEntity.setId("123");
        long l = easyEntityQuery.updatable(queryLargeColumnTestEntity).setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS).executeRows();
        Assert.assertEquals(0, l);
    }

    @org.junit.Test
    public void queryLargeColumnTest10() {
        try {

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            queryLargeColumnTestEntity.setName("123");
            long l = easyEntityQuery.updatable(queryLargeColumnTestEntity).setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS).executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("UPDATE `query_large_column_test` SET `name` = ? WHERE `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'easy-query-test.query_large_column_test' doesn't exist", ex1.getMessage());
        }
    }

    @org.junit.Test
    public void queryLargeColumnTest11() {
        try {

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            queryLargeColumnTestEntity.setName("123");
            long l = easyEntityQuery.updatable(queryLargeColumnTestEntity).setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS).executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("UPDATE `query_large_column_test` SET `content` = ? WHERE `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'easy-query-test.query_large_column_test' doesn't exist", ex1.getMessage());
        }
    }

    @org.junit.Test
    public void queryLargeColumnTest12() {
        QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
        queryLargeColumnTestEntity.setId("123");
        queryLargeColumnTestEntity.setName("123");
        queryLargeColumnTestEntity.setContent("123");
        long l = easyEntityQuery.updatable(queryLargeColumnTestEntity).setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS).executeRows();
        Assert.assertEquals(0, l);
    }

    @org.junit.Test
    public void deleteTest13() {
        try {
            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyEntityQuery.deletable(queryLargeColumnTestEntity).executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("DELETE FROM `query_large_column_test` WHERE `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'easy-query-test.query_large_column_test' doesn't exist", ex1.getMessage());
        }
    }

    @org.junit.Test
    public void deleteTest14() {
        try {
            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyEntityQuery.deletable(queryLargeColumnTestEntity).asTable("aaa").executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("DELETE FROM `aaa` WHERE `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'easy-query-test.aaa' doesn't exist", ex1.getMessage());
        }
    }

    @org.junit.Test
    public void deleteTest15() {
        try {
            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyEntityQuery.deletable(queryLargeColumnTestEntity).asTable(o -> o + "aaa").asSchema("xxx").executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("DELETE FROM `xxx`.`query_large_column_testaaa` WHERE `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'xxx.query_large_column_testaaa' doesn't exist", ex1.getMessage());
        }
    }

    @org.junit.Test
    public void deleteTest16() {
        try {
            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyEntityQuery.deletable(queryLargeColumnTestEntity).asTable(o -> o + "aaa").asSchema(o -> "xxx").executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("DELETE FROM `xxx`.`query_large_column_testaaa` WHERE `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'xxx.query_large_column_testaaa' doesn't exist", ex1.getMessage());
        }
    }

    @org.junit.Test
    public void queryLargeColumnTest14() {
        try {
            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyEntityQuery.deletable(queryLargeColumnTestEntity).allowDeleteStatement(false).executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQueryInvalidOperationException);
            Assert.assertEquals("The delete operation cannot be executed because physical deletion is not allowed by default configuration. If physical deletion is needed, please call [.allowDeleteStatement(true)].", ex.getMessage());
        }
    }

    @org.junit.Test
    public void queryLargeColumnTest15() {
        QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
        queryLargeColumnTestEntity.setId("123");
        String sql = easyEntityQuery.deletable(queryLargeColumnTestEntity).toSQL();
        Assert.assertEquals("DELETE FROM `query_large_column_test` WHERE `id` = ?", sql);
    }

    @org.junit.Test
    public void queryLargeColumnTest19() {
        try {

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            queryLargeColumnTestEntity.setName("123");
            long l = easyEntityQuery.updatable(queryLargeColumnTestEntity)
                    .asTable("abc")
                    .asSchema("xxx").setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS).executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("UPDATE `xxx`.`abc` SET `content` = ? WHERE `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'xxx.abc' doesn't exist", ex1.getMessage());
        }
    }

    @org.junit.Test
    public void queryLargeColumnTest20() {
        try {

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            queryLargeColumnTestEntity.setName("123");
            long l = easyEntityQuery.updatable(queryLargeColumnTestEntity)
                    .asTable(o -> o + "abc")
                    .asSchema("xcv").setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS).executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("UPDATE `xcv`.`query_large_column_testabc` SET `content` = ? WHERE `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'xcv.query_large_column_testabc' doesn't exist", ex1.getMessage());
        }
    }

    @org.junit.Test
    public void queryLargeColumnTest21() {
        try {

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            queryLargeColumnTestEntity.setName("123");
            long l = easyEntityQuery.updatable(queryLargeColumnTestEntity)
                    .asTable("")
                    .asSchema("xcv").setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS).executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof IllegalArgumentException);
            Assert.assertEquals("tableName is empty", ex.getMessage());
        }
    }

    @org.junit.Test
    public void queryLargeColumnTest22() {
        try {

            long l = easyEntityQuery.updatable(QueryLargeColumnTestEntity.class)
                    .asTable(o -> o + "abc")
                    .asSchema("xcv")

                    .setColumns(q -> q.id().set("123"))
                    .setColumns(q -> q.name().set("123"))
                    .setColumns(q -> q.content().set("123"))
                    .executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            String message = ex.getMessage();
            Assert.assertEquals("'UPDATE' statement without 'WHERE'", message);
        }
    }

    @org.junit.Test
    public void queryLargeColumnTest23() {
        try {

            long l = easyEntityQuery.updatable(QueryLargeColumnTestEntity.class)
                    .setColumns(q -> q.id().set("123"))
                    .setColumns(q -> q.name().set("123"))
                    .setColumns(q -> q.content().set("123"))
                    .whereById("123")
                    .executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("UPDATE `query_large_column_test` SET `id` = ?,`name` = ?,`content` = ? WHERE `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'easy-query-test.query_large_column_test' doesn't exist", ex1.getMessage());
        }
    }

    @org.junit.Test
    public void updateTest24() {
        try {

            long l = easyEntityQuery.updatable(BlogEntity.class)
                    .asTable("x_t_blog")
                    .setColumns(t_blog -> t_blog.star().increment())
                    .whereById("123")
                    .executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("UPDATE `x_t_blog` SET `star` = `star` + ? WHERE `deleted` = ? AND `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'easy-query-test.x_t_blog' doesn't exist", ex1.getMessage());
        }
    }

    @org.junit.Test
    public void updateTest25() {
        try {

            long l = easyEntityQuery.updatable(BlogEntity.class)
                    .asTable("x_t_blog")
                    .setColumns(t_blog -> t_blog.star().increment(2))
                    .whereById("123")
                    .executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("UPDATE `x_t_blog` SET `star` = `star` + ? WHERE `deleted` = ? AND `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'easy-query-test.x_t_blog' doesn't exist", ex1.getMessage());
        }
    }

    @org.junit.Test
    public void updateTest26() {
        try {

            long l = easyEntityQuery.updatable(BlogEntity.class)
                    .asTable("x_t_blog")
                    .setColumns(false, t_blog -> t_blog.star().increment(2))
                    .setColumns(t_blog -> t_blog.score().increment(2))
                    .whereById("123")
                    .executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("UPDATE `x_t_blog` SET `score` = `score` + ? WHERE `deleted` = ? AND `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'easy-query-test.x_t_blog' doesn't exist", ex1.getMessage());
        }
    }

    @org.junit.Test
    public void updateTest27() {
        try {

            long l = easyEntityQuery.updatable(BlogEntity.class)
                    .asTable("x_t_blog")
                    .setColumns(t_blog -> t_blog.star().decrement(2))
                    .whereById("123")
                    .executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("UPDATE `x_t_blog` SET `star` = `star` - ? WHERE `deleted` = ? AND `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'easy-query-test.x_t_blog' doesn't exist", ex1.getMessage());
        }
    }

    @org.junit.Test
    public void updateTest28() {
        try {

            long l = easyEntityQuery.updatable(BlogEntity.class)
                    .asTable("x_t_blog")
                    .setColumns(false, t_blog -> t_blog.status().increment(1))
                    .setColumns(true, t_blog -> t_blog.star().increment(2))
                    .whereById("123")
                    .executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("UPDATE `x_t_blog` SET `star` = `star` + ? WHERE `deleted` = ? AND `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'easy-query-test.x_t_blog' doesn't exist", ex1.getMessage());
        }
    }

    @org.junit.Test
    public void updateTest29() {
        try {

            long l = easyEntityQuery.updatable(UnknownTable.class)
                    .setColumns(false, u -> u.money1().increment(2))
                    .setColumns(true, u -> u.money().increment(2))
                    .whereById("123")
                    .executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("UPDATE `t_unknown` SET `money` = `money` + ? WHERE `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'easy-query-test.t_unknown' doesn't exist", ex1.getMessage());
        }
    }

    @org.junit.Test
    public void updateTest30() {
        try {

            long l = easyEntityQuery.updatable(NoKeyEntity.class)
                    .setColumns(n -> n.name().set("123"))
                    .whereById("123")
                    .executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQueryNoPrimaryKeyException);
        }
    }
    @org.junit.Test
    public void repeatApply1() {
        EasyQueryClient easyQueryClient1 = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .build();
        QueryConfiguration queryConfiguration = easyQueryClient1.getRuntimeContext().getQueryConfiguration();
        queryConfiguration.applyGeneratedKeySQLColumnGenerator(new MyDatabaseIncrementSQLColumnGenerator());
        try {
            queryConfiguration.applyGeneratedKeySQLColumnGenerator(new MyDatabaseIncrementSQLColumnGenerator());
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            String message = ex.getMessage();
            Assert.assertEquals("generated key sql column generator:MyDatabaseIncrementSQLColumnGenerator,repeat", message);
        }
        queryConfiguration.applyInterceptor(new MyEntityInterceptor());
        try {
            queryConfiguration.applyInterceptor(new MyEntityInterceptor());
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            String message = ex.getMessage();
            Assert.assertEquals("global interceptor:MyEntityInterceptor,repeat", message);
        }
        queryConfiguration.applyLogicDeleteStrategy(new MyLogicDelStrategy());
        try {
            queryConfiguration.applyLogicDeleteStrategy(new MyLogicDelStrategy());
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            String message = ex.getMessage();
            Assert.assertEquals("global logic delete strategy:MyLogicDelStrategy,repeat", message);
        }
        queryConfiguration.applyEncryptionStrategy(new Base64EncryptionStrategy());
        try {
            queryConfiguration.applyEncryptionStrategy(new Base64EncryptionStrategy());
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            String message = ex.getMessage();
            Assert.assertEquals("easy encryption strategy:Base64EncryptionStrategy,repeat", message);
        }
    }

    @org.junit.Test
    public void testPrintSQL1() {
        EasyExpressionContext easyExpressionContext = new EasyExpressionContext(easyEntityQuery.getRuntimeContext(), ContextTypeEnum.QUERY);
        easyExpressionContext.setPrintSQL(false);
        easyExpressionContext.setPrintNavSQL(false);
        ExpressionContext expressionContext = easyExpressionContext.cloneExpressionContext();
        Assert.assertEquals(false, expressionContext.getPrintSQL());
        Assert.assertEquals(false, expressionContext.getPrintNavSQL());
    }

    @org.junit.Test
    public void testPrintSQL2() {
        EasyExpressionContext easyExpressionContext = new EasyExpressionContext(easyEntityQuery.getRuntimeContext(), ContextTypeEnum.QUERY);
        easyExpressionContext.setPrintSQL(null);
        easyExpressionContext.setPrintNavSQL(true);
        ExpressionContext expressionContext = easyExpressionContext.cloneExpressionContext();
        Assert.assertNull(expressionContext.getPrintSQL());
        Assert.assertEquals(true, expressionContext.getPrintNavSQL());
    }

    @org.junit.Test
    public void testQueryLockClonePropagation() {
        EasyExpressionContext easyExpressionContext = new EasyExpressionContext(easyEntityQuery.getRuntimeContext(), ContextTypeEnum.QUERY);
        easyExpressionContext.setQueryLock(QueryLockEnum.FOR_UPDATE);
        ExpressionContext expressionContext = easyExpressionContext.cloneExpressionContext();
        Assert.assertEquals(QueryLockEnum.FOR_UPDATE, expressionContext.getQueryLock());
    }

    @org.junit.Test
    public void testQueryLockExtendFromPropagation() {
        EasyExpressionContext sourceExpressionContext = new EasyExpressionContext(easyEntityQuery.getRuntimeContext(), ContextTypeEnum.QUERY);
        sourceExpressionContext.setQueryLock(QueryLockEnum.FOR_UPDATE);
        EasyExpressionContext targetExpressionContext = new EasyExpressionContext(easyEntityQuery.getRuntimeContext(), ContextTypeEnum.QUERY);
        sourceExpressionContext.extendFrom(targetExpressionContext);
        Assert.assertEquals(QueryLockEnum.FOR_UPDATE, targetExpressionContext.getQueryLock());
    }
}
