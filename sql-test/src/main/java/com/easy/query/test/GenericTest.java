package com.easy.query.test;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.Queryable2;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.common.LinkedCaseInsensitiveMap;
import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.configuration.nameconversion.impl.LowerCamelCaseNameConversion;
import com.easy.query.core.configuration.nameconversion.impl.UnderlinedNameConversion;
import com.easy.query.core.configuration.nameconversion.impl.UpperCamelCaseNameConversion;
import com.easy.query.core.configuration.nameconversion.impl.UpperUnderlinedNameConversion;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.enums.SQLRangeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.exception.EasyQueryNoPrimaryKeyException;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.exception.EasyQueryUnexpectedException;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.util.EasyAesUtil;
import com.easy.query.core.util.EasyBase64Util;
import com.easy.query.core.util.EasyBeanUtil;
import com.easy.query.core.util.EasyBitwiseUtil;
import com.easy.query.core.util.EasyCheck;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.test.common.MyEnum;
import com.easy.query.test.conversion.EnumValueConverter;
import com.easy.query.test.conversion.EnumValueDeserializer;
import com.easy.query.test.encryption.Base64EncryptionStrategy;
import com.easy.query.test.encryption.DefaultAesEasyEncryptionStrategy;
import com.easy.query.test.encryption.DefaultSafeAesEasyEncryptionStrategy;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.NoKeyEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.UnknownTable;
import com.easy.query.test.entity.notable.QueryLargeColumnTestEntity;
import com.easy.query.test.entity.proxy.TopicShardingProxy;
import com.easy.query.test.increment.MyDatabaseIncrementSQLColumnGenerator;
import com.easy.query.test.interceptor.MyEntityInterceptor;
import com.easy.query.test.logicdel.MyLogicDelStrategy;
import com.easy.query.test.testvo.MyUserDTO;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * @author xuejiaming
 * @FileName: GenericTest.java
 * @Description: 文件说明
 * @Date: 2023/3/17 22:22
 */
public class GenericTest extends BaseTest {

    @Test
    public void SqlRangeTest1() {
        boolean openFirst1 = SQLRangeEnum.openFirst(SQLRangeEnum.OPEN);
        Assert.assertTrue(openFirst1);
        boolean openFirst2 = SQLRangeEnum.openFirst(SQLRangeEnum.CLOSED);
        Assert.assertFalse(openFirst2);
        boolean openFirst3 = SQLRangeEnum.openFirst(SQLRangeEnum.CLOSED_OPEN);
        Assert.assertFalse(openFirst3);
        boolean openFirst4 = SQLRangeEnum.openFirst(SQLRangeEnum.OPEN_CLOSED);
        Assert.assertTrue(openFirst4);
        boolean openEnd1 = SQLRangeEnum.openEnd(SQLRangeEnum.OPEN);
        Assert.assertTrue(openEnd1);
        boolean openEnd2 = SQLRangeEnum.openEnd(SQLRangeEnum.CLOSED);
        Assert.assertFalse(openEnd2);
        boolean openEnd3 = SQLRangeEnum.openEnd(SQLRangeEnum.CLOSED_OPEN);
        Assert.assertTrue(openEnd3);
        boolean openEnd4 = SQLRangeEnum.openEnd(SQLRangeEnum.OPEN_CLOSED);
        Assert.assertFalse(openEnd4);
    }

    @Test
    public void base64Test() {
        String uuid = UUID.randomUUID().toString();
        byte[] bytes = uuid.getBytes(StandardCharsets.UTF_8);
        byte[] encode = EasyBase64Util.encode(bytes);
        byte[] decode = EasyBase64Util.decode(encode);
        Assert.assertArrayEquals(bytes, decode);
        Assert.assertEquals(uuid, new String(decode, StandardCharsets.UTF_8));
    }

    private final String key = "abcdef1234567890";//16位的秘钥
    private final String iv = "A-16-Byte-String";//16位的iv

    @Test
    public void aesTest2() {
        String uuid = UUID.randomUUID().toString();
        String encryptToString = EasyAesUtil.encrypt(uuid, key, iv, StandardCharsets.UTF_8);
        String decryptToString = EasyAesUtil.decrypt(encryptToString, key, iv, StandardCharsets.UTF_8);

        Assert.assertEquals(uuid, decryptToString);

    }

    @Test
    public void easyEncryptionTest() {

        DefaultAesEasyEncryptionStrategy aesEasyEncryptionStrategy = new DefaultAesEasyEncryptionStrategy();
        String xx = "❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️垚垚垚垚垚垚";

        Object encryptValue = aesEasyEncryptionStrategy.encrypt(null, null, xx);
        Object decryptValue = aesEasyEncryptionStrategy.decrypt(null, null, encryptValue);
        Assert.assertEquals(xx, decryptValue);

        String phone = "18888888888";
        Object encrypt = aesEasyEncryptionStrategy.encrypt(null, null, phone);
        Object decrypt = aesEasyEncryptionStrategy.decrypt(null, null, encrypt);
        Assert.assertEquals(phone, decrypt);
    }

    @Test
    public void easyEncryptionTest1() {
        DefaultAesEasyEncryptionStrategy aesEasyEncryptionStrategy = new DefaultAesEasyEncryptionStrategy();
        String xx = "188888881212";

        Object encryptValue = aesEasyEncryptionStrategy.encrypt(null, null, xx);
        Object decryptValue = aesEasyEncryptionStrategy.decrypt(null, null, encryptValue);
        Assert.assertEquals(xx, decryptValue);
        Object encryptValue1 = aesEasyEncryptionStrategy.encrypt(null, null, "1888888812");
        Assert.assertTrue(EasyStringUtil.startsWith(encryptValue.toString(), encryptValue1.toString()));
    }

    @Test
    public void behavior() {
        EasyBehavior easyBehavior = new EasyBehavior();
        Assert.assertFalse(easyBehavior.isDefaultBehavior());
        Assert.assertTrue(easyBehavior.hasBehavior(EasyBehaviorEnum.LOGIC_DELETE));
        easyBehavior.addBehavior(EasyBehaviorEnum.LOGIC_DELETE);
        Assert.assertTrue(easyBehavior.hasBehavior(EasyBehaviorEnum.LOGIC_DELETE));
        easyBehavior.addBehavior(EasyBehaviorEnum.LOGIC_DELETE);
        Assert.assertTrue(easyBehavior.hasBehavior(EasyBehaviorEnum.LOGIC_DELETE));

        Assert.assertTrue(easyBehavior.hasBehavior(EasyBehaviorEnum.USE_INTERCEPTOR));
        easyBehavior.addBehavior(EasyBehaviorEnum.USE_INTERCEPTOR);
        Assert.assertTrue(easyBehavior.hasBehavior(EasyBehaviorEnum.USE_INTERCEPTOR));
        Assert.assertTrue(easyBehavior.hasBehavior(EasyBehaviorEnum.LOGIC_DELETE));
        Assert.assertFalse(easyBehavior.hasBehavior(EasyBehaviorEnum.USE_TRACKING));
        Assert.assertFalse(easyBehavior.removeBehavior(EasyBehaviorEnum.USE_TRACKING));


        easyBehavior.removeBehavior(EasyBehaviorEnum.LOGIC_DELETE);
        Assert.assertFalse(easyBehavior.hasBehavior(EasyBehaviorEnum.LOGIC_DELETE));
        easyBehavior.removeBehavior(EasyBehaviorEnum.USE_INTERCEPTOR);
        easyBehavior.removeBehavior(EasyBehaviorEnum.QUERY_LARGE_COLUMN);
        easyBehavior.removeBehavior(EasyBehaviorEnum.JDBC_LISTEN);
        Assert.assertTrue(easyBehavior.isDefaultBehavior());
    }

    @Test
    public void executeMethodBehaviorTest1() {
        int code = ExecuteMethodEnum.UNKNOWN.getCode();
        code = EasyBitwiseUtil.addBit(code, ExecuteMethodEnum.ANY.getCode());
        Assert.assertNotEquals(code, ExecuteMethodEnum.UNKNOWN.getCode());
        Assert.assertTrue(EasyBitwiseUtil.hasBit(code, ExecuteMethodEnum.ANY.getCode()));
        Assert.assertNotEquals(code, ExecuteMethodEnum.UNKNOWN.getCode());
        Assert.assertTrue(EasyBitwiseUtil.hasBit(code, ExecuteMethodEnum.ANY.getCode()));
        code = EasyBitwiseUtil.removeBit(code, ExecuteMethodEnum.ANY.getCode());
        Assert.assertFalse(EasyBitwiseUtil.hasBit(code, ExecuteMethodEnum.ANY.getCode()));
        Assert.assertEquals(code, ExecuteMethodEnum.UNKNOWN.getCode());
    }

    @Test
    public void queryLargeColumnTest1() {
        String sql = easyQuery.queryable(QueryLargeColumnTestEntity.class).toSQL();
        Assert.assertEquals("SELECT `id`,`name`,`content` FROM `query_large_column_test`", sql);
    }

    @Test
    public void queryLargeColumnTest2() {
        String sql = easyQuery.queryable(QueryLargeColumnTestEntity.class).queryLargeColumn(false).toSQL();
        Assert.assertEquals("SELECT `id`,`name` FROM `query_large_column_test`", sql);
    }

    @Test
    public void queryLargeColumnTest3() {
        try {

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            long l = easyQuery.insertable(queryLargeColumnTestEntity).executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex.getMessage().startsWith("not found insert columns :"));
        }
    }

    @Test
    public void queryLargeColumnTest4() {
        try {

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyQuery.insertable(queryLargeColumnTestEntity).executeRows();
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

    @Test
    public void queryLargeColumnTest5() {
        try {

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyQuery.insertable(queryLargeColumnTestEntity).setSQLStrategy(SQLExecuteStrategyEnum.DEFAULT).executeRows();
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

    @Test
    public void queryLargeColumnTest6() {
        try {

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyQuery.insertable(queryLargeColumnTestEntity).setSQLStrategy(SQLExecuteStrategyEnum.ALL_COLUMNS).executeRows();
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

    @Test
    public void queryLargeColumnTest7() {
        try {

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyQuery.insertable(queryLargeColumnTestEntity).setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS).executeRows();
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

    @Test
    public void queryLargeColumnTest8() {
        try {

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyQuery.updatable(queryLargeColumnTestEntity).executeRows();
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

    @Test
    public void queryLargeColumnTest9() {
        QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
        queryLargeColumnTestEntity.setId("123");
        long l = easyQuery.updatable(queryLargeColumnTestEntity).setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS).executeRows();
        Assert.assertEquals(0, l);
    }

    @Test
    public void queryLargeColumnTest10() {
        try {

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            queryLargeColumnTestEntity.setName("123");
            long l = easyQuery.updatable(queryLargeColumnTestEntity).setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS).executeRows();
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

    @Test
    public void queryLargeColumnTest11() {
        try {

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            queryLargeColumnTestEntity.setName("123");
            long l = easyQuery.updatable(queryLargeColumnTestEntity).setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS).executeRows();
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

    @Test
    public void queryLargeColumnTest12() {
        QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
        queryLargeColumnTestEntity.setId("123");
        queryLargeColumnTestEntity.setName("123");
        queryLargeColumnTestEntity.setContent("123");
        long l = easyQuery.updatable(queryLargeColumnTestEntity).setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS).executeRows();
        Assert.assertEquals(0, l);
    }

    @Test
    public void deleteTest13() {
        try {
            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyQuery.deletable(queryLargeColumnTestEntity).executeRows();
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

    @Test
    public void deleteTest14() {
        try {
            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyQuery.deletable(queryLargeColumnTestEntity).asTable("aaa").executeRows();
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

    @Test
    public void deleteTest15() {
        try {
            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyQuery.deletable(queryLargeColumnTestEntity).asTable(o -> o + "aaa").asSchema("xxx").executeRows();
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

    @Test
    public void deleteTest16() {
        try {
            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyQuery.deletable(queryLargeColumnTestEntity).asTable(o -> o + "aaa").asSchema(o -> "xxx").executeRows();
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

    @Test
    public void queryLargeColumnTest14() {
        try {
            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyQuery.deletable(queryLargeColumnTestEntity).allowDeleteStatement(false).executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQueryInvalidOperationException);
            Assert.assertEquals("The delete operation cannot be executed because physical deletion is not allowed by default configuration. If physical deletion is needed, please call [.allowDeleteStatement(true)].", ex.getMessage());
        }
    }

    @Test
    public void queryLargeColumnTest15() {
        QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
        queryLargeColumnTestEntity.setId("123");
        String sql = easyQuery.deletable(queryLargeColumnTestEntity).toSQL();
        Assert.assertEquals("DELETE FROM `query_large_column_test` WHERE `id` = ?", sql);
    }

    @Test
    public void linkedCaseInsensitiveMapTest1() {
        LinkedCaseInsensitiveMap<Object> objectLinkedCaseInsensitiveMap = new LinkedCaseInsensitiveMap<>();
        Object put = objectLinkedCaseInsensitiveMap.put("123", "123");
        Assert.assertNull(put);
        Object put1 = objectLinkedCaseInsensitiveMap.put("123", "456");
        Assert.assertNotNull(put1);
        Assert.assertEquals("123", put1);
    }

    @Test
    public void queryLargeColumnTest19() {
        try {

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            queryLargeColumnTestEntity.setName("123");
            long l = easyQuery.updatable(queryLargeColumnTestEntity)
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

    @Test
    public void queryLargeColumnTest20() {
        try {

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            queryLargeColumnTestEntity.setName("123");
            long l = easyQuery.updatable(queryLargeColumnTestEntity)
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

    @Test
    public void queryLargeColumnTest21() {
        try {

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            queryLargeColumnTestEntity.setName("123");
            long l = easyQuery.updatable(queryLargeColumnTestEntity)
                    .asTable("")
                    .asSchema("xcv").setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS).executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof IllegalArgumentException);
            Assert.assertEquals("tableName is empty", ex.getMessage());
        }
    }

    @Test
    public void queryLargeColumnTest22() {
        try {

            long l = easyQuery.updatable(QueryLargeColumnTestEntity.class)
                    .asTable(o -> o + "abc")
                    .asSchema("xcv")
                    .set(QueryLargeColumnTestEntity::getId, "123")
                    .set(QueryLargeColumnTestEntity::getName, "123")
                    .set(QueryLargeColumnTestEntity::getContent, "123")
                    .executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            String message = ex.getMessage();
            Assert.assertEquals("'UPDATE' statement without 'WHERE'", message);
        }
    }

    @Test
    public void queryLargeColumnTest23() {
        try {

            long l = easyQuery.updatable(QueryLargeColumnTestEntity.class)
                    .set(QueryLargeColumnTestEntity::getId, "123")
                    .set(QueryLargeColumnTestEntity::getName, "123")
                    .set(QueryLargeColumnTestEntity::getContent, "123")
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

    @Test
    public void updateTest24() {
        try {

            long l = easyQuery.updatable(BlogEntity.class)
                    .asTable("x_t_blog")
                    .setIncrement(BlogEntity::getStar)
                    .whereById("123")
                    .executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("UPDATE `x_t_blog` SET `star` = `star`+ ? WHERE `deleted` = ? AND `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'easy-query-test.x_t_blog' doesn't exist", ex1.getMessage());
        }
    }

    @Test
    public void updateTest25() {
        try {

            long l = easyQuery.updatable(BlogEntity.class)
                    .asTable("x_t_blog")
                    .setIncrement(BlogEntity::getStar, 2)
                    .whereById("123")
                    .executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("UPDATE `x_t_blog` SET `star` = `star`+ ? WHERE `deleted` = ? AND `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'easy-query-test.x_t_blog' doesn't exist", ex1.getMessage());
        }
    }

    @Test
    public void updateTest26() {
        try {

            long l = easyQuery.updatable(BlogEntity.class)
                    .asTable("x_t_blog")
                    .setIncrement(false, BlogEntity::getStar, 2)
                    .setIncrement(BlogEntity::getScore, 2)
                    .whereById("123")
                    .executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("UPDATE `x_t_blog` SET `score` = `score`+ ? WHERE `deleted` = ? AND `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'easy-query-test.x_t_blog' doesn't exist", ex1.getMessage());
        }
    }

    @Test
    public void updateTest27() {
        try {

            long l = easyQuery.updatable(BlogEntity.class)
                    .asTable("x_t_blog")
                    .setDecrement(BlogEntity::getStar, 2)
                    .whereById("123")
                    .executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("UPDATE `x_t_blog` SET `star` = `star`- ? WHERE `deleted` = ? AND `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'easy-query-test.x_t_blog' doesn't exist", ex1.getMessage());
        }
    }

    @Test
    public void updateTest28() {
        try {

            long l = easyQuery.updatable(BlogEntity.class)
                    .asTable("x_t_blog")
                    .setIncrement(false, BlogEntity::getStatus, 1)
                    .setIncrement(true, BlogEntity::getStar, 2)
                    .whereById("123")
                    .executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("UPDATE `x_t_blog` SET `star` = `star`+ ? WHERE `deleted` = ? AND `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'easy-query-test.x_t_blog' doesn't exist", ex1.getMessage());
        }
    }

    @Test
    public void updateTest29() {
        try {

            long l = easyQuery.updatable(UnknownTable.class)
                    .setIncrement(false, UnknownTable::getMoney1, 2L)
                    .setIncrement(true, UnknownTable::getMoney, 2L)
                    .whereById("123")
                    .executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("UPDATE `t_unknown` SET `money` = `money`+ ? WHERE `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'easy-query-test.t_unknown' doesn't exist", ex1.getMessage());
        }
    }

    @Test
    public void updateTest30() {
        try {

            long l = easyQuery.updatable(NoKeyEntity.class)
                    .set(NoKeyEntity::getName, "123")
                    .whereById("123")
                    .executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQueryNoPrimaryKeyException);
        }
    }

    @Test
    public void updateTest31() {
        try {

            long l = easyQuery.updatable(UnknownTable.class)
                    .setWithColumn(UnknownTable::getMoney1, UnknownTable::getMoney)
                    .setWithColumn(false, UnknownTable::getAge, UnknownTable::getAge1)
                    .setWithColumn(true, UnknownTable::getBirthday, UnknownTable::getBirthday1)
                    .whereById("123")
                    .executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("UPDATE `t_unknown` SET `money1` = `money`,`birthday` = `birthday1` WHERE `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'easy-query-test.t_unknown' doesn't exist", ex1.getMessage());
        }
    }

    @Test
    public void updateTest32() {
        try {

            long l = easyQuery.updatable(QueryLargeColumnTestEntity.class)
                    .set(false, QueryLargeColumnTestEntity::getId, "123")
                    .set(true, QueryLargeColumnTestEntity::getName, "123")
                    .set(QueryLargeColumnTestEntity::getContent, "123")
                    .whereById("123")
                    .executeRows();
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

    @Test
    public void updateTest33() {
        try {

            long l = easyQuery.updatable(BlogEntity.class)
                    .asTable("x_t_blog")
                    .setDecrement(false, BlogEntity::getStatus, 1)
                    .setDecrement(true, BlogEntity::getStar, 2)
                    .whereById("123")
                    .executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("UPDATE `x_t_blog` SET `star` = `star`- ? WHERE `deleted` = ? AND `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.SQLSyntaxErrorException: Table 'easy-query-test.x_t_blog' doesn't exist", ex1.getMessage());
        }
    }
    @Test
    public void createTest1() {
        FastBean fastBean = EasyBeanUtil.getFastBean(BlogEntity.class);
        Supplier<Object> lambdaCreate = fastBean.getBeanConstructorCreator();
        Object o = lambdaCreate.get();
        Assert.assertNotNull(o);
        Assert.assertTrue(o instanceof  BlogEntity);
        BlogEntity blogEntity = (BlogEntity)o;
        Assert.assertNotNull(blogEntity);
        {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 100000; i++) {
                BlogEntity blogEntity1 = EasyClassUtil.newInstance(BlogEntity.class);
            }
            long end = System.currentTimeMillis();
            System.out.println((end-start)+"(ms)");
        }
        {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 100000; i++) {
                BlogEntity blogEntity1 = EasyObjectUtil.typeCastNullable(lambdaCreate.get());
            }
            long end = System.currentTimeMillis();
            System.out.println((end-start)+"(ms)");
        }
        Object o1 = lambdaCreate.get();
        Object o2 = lambdaCreate.get();
        Assert.assertFalse(o1==o2);
    }
    @Test
    public void repeatApply1(){
        EasyQueryClient easyQueryClient1 = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .build();
        QueryConfiguration queryConfiguration = easyQueryClient1.getRuntimeContext().getQueryConfiguration();
        queryConfiguration.applyGeneratedKeySQLColumnGenerator(new MyDatabaseIncrementSQLColumnGenerator());
        try {
            queryConfiguration.applyGeneratedKeySQLColumnGenerator(new MyDatabaseIncrementSQLColumnGenerator());
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof EasyQueryException);
            String message = ex.getMessage();
            Assert.assertEquals("generated key sql column generator:MyDatabaseIncrementSQLColumnGenerator,repeat",message);
        }
        queryConfiguration.applyInterceptor(new MyEntityInterceptor());
        try {
            queryConfiguration.applyInterceptor(new MyEntityInterceptor());
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof EasyQueryException);
            String message = ex.getMessage();
            Assert.assertEquals("global interceptor:MyEntityInterceptor,repeat",message);
        }
        queryConfiguration.applyLogicDeleteStrategy(new MyLogicDelStrategy());
        try {
            queryConfiguration.applyLogicDeleteStrategy(new MyLogicDelStrategy());
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof EasyQueryException);
            String message = ex.getMessage();
            Assert.assertEquals("global logic delete strategy:MyLogicDelStrategy,repeat",message);
        }
        queryConfiguration.applyEncryptionStrategy(new Base64EncryptionStrategy());
        try {
            queryConfiguration.applyEncryptionStrategy(new Base64EncryptionStrategy());
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof EasyQueryException);
            String message = ex.getMessage();
            Assert.assertEquals("easy encryption strategy:Base64EncryptionStrategy,repeat",message);
        }
    }


    @Test
    public void nameConversionTest1(){
        {
            LowerCamelCaseNameConversion lowerCamelCaseNameConversion = new LowerCamelCaseNameConversion();
            {
                String nameConversion = "NameConversion";
                String convert = lowerCamelCaseNameConversion.convert(nameConversion);
                Assert.assertEquals("nameConversion",convert);
            }
            {
                String nameConversion = "nameConversion";
                String convert = lowerCamelCaseNameConversion.convert(nameConversion);
                Assert.assertEquals("nameConversion",convert);
            }
        }
        {

            UpperCamelCaseNameConversion upperCamelCaseNameConversion = new UpperCamelCaseNameConversion();
            {
                String nameConversion = "NameConversion";
                String convert = upperCamelCaseNameConversion.convert(nameConversion);
                Assert.assertEquals("NameConversion",convert);
            }
            {
                String nameConversion = "nameConversion";
                String convert = upperCamelCaseNameConversion.convert(nameConversion);
                Assert.assertEquals("NameConversion",convert);
            }
        }
        {
            UnderlinedNameConversion underlinedNameConversion = new UnderlinedNameConversion();
            {
                String nameConversion = "NameConversion";
                String convert = underlinedNameConversion.convert(nameConversion);
                Assert.assertEquals("name_conversion",convert);
            }
            {
                String nameConversion = "nameConversion";
                String convert = underlinedNameConversion.convert(nameConversion);
                Assert.assertEquals("name_conversion",convert);
            }
        }
        {
            UpperUnderlinedNameConversion upperUnderlinedNameConversion = new UpperUnderlinedNameConversion();
            {
                String nameConversion = "NameConversion";
                String convert = upperUnderlinedNameConversion.convert(nameConversion);
                Assert.assertEquals("NAME_CONVERSION",convert);
            }
            {
                String nameConversion = "nameConversion";
                String convert = upperUnderlinedNameConversion.convert(nameConversion);
                Assert.assertEquals("NAME_CONVERSION",convert);
            }
        }
    }

    @Test
    public void cloneTest1(){
        Queryable2<Topic, BlogEntity> topicBlogEntityQueryable2 = easyQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId));
        Queryable2<Topic, BlogEntity> topicBlogEntityQueryable21 = topicBlogEntityQueryable2.cloneQueryable();
        Queryable2<Topic, BlogEntity> topicBlogEntityQueryable22 = topicBlogEntityQueryable2.cloneQueryable();
        String sql = topicBlogEntityQueryable21.where(o -> o.eq(Topic::getId, "123")).toSQL();
        String sql1 = topicBlogEntityQueryable22.where((t, t1) -> t1.eq(BlogEntity::getId, "234")).toSQL();
        String sql2 = topicBlogEntityQueryable2.toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` = ?",sql);
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`id` = ?",sql1);
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id`",sql2);
    }
    @Test
     public void StringCharSegmentTest1(){
         List<String> a = EasyStringUtil.getStringCharSegments("aaa啊啊a", 4, 1, 2);
        String as = a.toString();
        Assert.assertEquals("[aaa啊, aa啊, a啊啊, 啊啊, 啊a]",as);
        List<String> a1 = EasyStringUtil.getStringCharSegments("aaa啊啊", 4, 1, 2);
        String a1s = a1.toString();
        Assert.assertEquals("[aaa啊, aa啊, a啊啊, 啊啊]",a1s);
        String randomBase64 = "OTpIT+KDo+KdpDYq5Z6aIzMzek1A77iPMXlG8J+RqFNjXF5PXumHjDBY5ZyoMA==";
//        String randomString = new String(EasyBase64Util.decode(randomBase64.getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8);
        String randomString = "√在-`N*以\\字中d\\~_yb2❤USY\uD83D\uDC8A\uD83D\uDC68\uD83E\uDD21\uD83D\uDC685G*\uD83D\uDE08符T*9L^9oP可符F※,x·Z∝这4ェ(v\u200D";
        List<String> segments = EasyStringUtil.getStringSafeCharSegments(randomString, 4, 1, 2);
//
//        String iv="A-16-Byte-String";
//String key="abcdef1234567890";
//        StringBuilder stringBuilder = new StringBuilder();
//        for (String stringCharSegment : segments) {
//            byte[] encrypt = EasyAesUtil.encrypt(stringCharSegment.getBytes(StandardCharsets.UTF_16), key.getBytes(StandardCharsets.UTF_8), iv.getBytes(StandardCharsets.UTF_8));
//            String str= new String(EasyBase64Util.encode(encrypt),StandardCharsets.UTF_8);
//            stringBuilder.append(str);
//        }
//        String ciphertextString = stringBuilder.toString();
//
//
//        {
//            List<String> segment1s = EasyStringUtil.splitBase64ByGroupSize(ciphertextString, 24)
//                    .stream().map(o->{
//                        byte[] decrypt = EasyAesUtil.decrypt(EasyBase64Util.decode(o.getBytes(StandardCharsets.UTF_8)),key.getBytes(StandardCharsets.UTF_8), iv.getBytes(StandardCharsets.UTF_8));
//                        return new String(decrypt,StandardCharsets.UTF_16);
//                    }).collect(Collectors.toList());
//
//            {
//
//                String s = segments.get(33);
//                String s1 = segment1s.get(33);
//                byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
//                byte[] bytes1 = s1.getBytes(StandardCharsets.UTF_8);
//                String s2 = new String(bytes, StandardCharsets.UTF_8);
//                String s3 = new String(bytes1, StandardCharsets.UTF_8);
//                System.out.println("1");
//            }
//            {
//
//                String s = segments.get(33);
//                char c = s.charAt(0);
//                String aa= String.valueOf(c);
//                int x=(int)c;
//                String s1 = segment1s.get(33);
//                byte[] bytes = s.getBytes(StandardCharsets.UTF_16);
//                String s2 = new String(bytes,StandardCharsets.UTF_16);
//                char c1 = s2.charAt(0);
//
//
//                // 原始 char 值
//                char originalChar = c;
//
//                // 将 char 编码为字节流
//                byte[] utf16Bytes = new String(new char[] { originalChar }).getBytes(Charset.forName("UTF-16"));
//
//                // 将字节流解码回 char
//                char decodedChar = new String(utf16Bytes, Charset.forName("UTF-16")).charAt(0);
//
//                System.out.println("Original Char: " + originalChar);
//                System.out.println("Decoded Char: " + decodedChar);
//
//                // 检查原始 char 和解码后的 char 是否一致
//                boolean charsMatch = originalChar == decodedChar;
//                System.out.println("Chars Match: " + charsMatch);
//
//
//                String s4 = new String(EasyBase64Util.encode(bytes), StandardCharsets.UTF_8);
//                byte[] bytes2 = s4.getBytes(StandardCharsets.UTF_8);
//                String s5 = new String(EasyBase64Util.decode(bytes2), StandardCharsets.UTF_16);
//                byte[] bytes1 = s1.getBytes();
//                boolean equals = s.equals(s2);
//                String s3 = new String(bytes1);
//                System.out.println("1");
//
//
//            }
////
////        StringBuilder stringBuilder = new StringBuilder();
////        for (int i = 0; i < segments.size(); i++) {
////            String str= EasyAesUtil.decrypt(segments.get(i), getKey(), getIv(),StandardCharsets.UTF_8);
////            boolean last = i == (segments.size() - 1);
////            if (last) {
////                stringBuilder.append(str);
////            } else {
////                stringBuilder.append(str.charAt(0));
////            }
////        }
////        return stringBuilder.toString();
//
            StringBuilder stringBuilder1 = new StringBuilder();
            for (int i = 0; i < segments.size(); i++) {
                String str = segments.get(i);
                boolean last = i == (segments.size() - 1);
                if (last) {
                    stringBuilder1.append(str);
                } else {
                    stringBuilder1.append(str.charAt(0));
                }
            }
            String string = stringBuilder1.toString();
            DefaultAesEasyEncryptionStrategy defaultAesEasyEncryptionStrategy = new DefaultAesEasyEncryptionStrategy();
//        }

    }


    @Test
    public void StringCharSegmentTest2(){
        DefaultAesEasyEncryptionStrategy defaultAesEasyEncryptionStrategy = new DefaultAesEasyEncryptionStrategy();
        for (int i = 0; i < 8000; i++) {
            int desiredLength = new Random().nextInt(40);
            String randomString = generateRandomString(desiredLength+1);
            try {

                Object encrypt = defaultAesEasyEncryptionStrategy.encrypt(null, null, randomString);
                Assert.assertNotNull(encrypt);
                Object decrypt = defaultAesEasyEncryptionStrategy.decrypt(null, null, encrypt);
                Assert.assertNotNull(decrypt);
                String string = decrypt.toString();
                boolean equals = randomString.equals(string);
                if(!equals){
                    String s = new String(EasyBase64Util.encode(randomString.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
                    System.out.println("随机串base64:"+s);
                }
                Assert.assertEquals(randomString,string);
            }catch (Exception ex){
                System.out.println("随机串:"+randomString);
                throw ex;
            }
        }
    }
    @Test
    public void StringCharSegmentTest7(){
        DefaultSafeAesEasyEncryptionStrategy defaultAesEasyEncryptionStrategy = new DefaultSafeAesEasyEncryptionStrategy();
        for (int i = 0; i < 20000; i++) {
            int desiredLength = new Random().nextInt(60);
            String randomString = generateRandomString1(desiredLength+11);
            try {

                Object encrypt = defaultAesEasyEncryptionStrategy.encrypt(null, null, randomString);
                Assert.assertNotNull(encrypt);
                Object decrypt = defaultAesEasyEncryptionStrategy.decrypt(null, null, encrypt);
                Assert.assertNotNull(decrypt);
                String string = decrypt.toString();
                boolean equals = randomString.equals(string);
                if(!equals){
                    String s = new String(EasyBase64Util.encode(randomString.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
                    System.out.println("随机串base64:"+s);
                }
                Assert.assertEquals(randomString,string);
            }catch (Exception ex){
                System.out.println("随机串:"+randomString);
                String s = new String(EasyBase64Util.encode(randomString.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
                System.out.println("随机串base64:"+s);
                throw ex;
            }
        }
    }

    @Test
    public void StringCharSegmentTest4(){
        String randomString = "qO)3\\{*h\uFE0F�MbjT";
        DefaultAesEasyEncryptionStrategy defaultAesEasyEncryptionStrategy = new DefaultAesEasyEncryptionStrategy();

        Object encrypt = defaultAesEasyEncryptionStrategy.encrypt(null, null, randomString);
        Assert.assertNotNull(encrypt);
        Object decrypt = defaultAesEasyEncryptionStrategy.decrypt(null, null, encrypt);
        Assert.assertNotNull(decrypt);
        String string = decrypt.toString();
        boolean equals = randomString.equals(string);
        if(!equals){
            System.out.println("随机串base64:"+new String(EasyBase64Util.encode(randomString.getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8));
        }
        Assert.assertEquals(randomString,string);

    }
    @Test
    public void StringCharSegmentTest5(){
        String randomBase64 = "NSxcLu+4j35ocjElWTEpTyUr5Y+ve+WKoDdiIFlkLj85L2An77iPTkM5UEI=";
        String randomString = new String(EasyBase64Util.decode(randomBase64.getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8);
        DefaultAesEasyEncryptionStrategy defaultAesEasyEncryptionStrategy = new DefaultAesEasyEncryptionStrategy();

        Object encrypt = defaultAesEasyEncryptionStrategy.encrypt(null, null, randomString);
        Assert.assertNotNull(encrypt);
        Object decrypt = defaultAesEasyEncryptionStrategy.decrypt(null, null, encrypt);
        Assert.assertNotNull(decrypt);
        String string = decrypt.toString();
        boolean equals = randomString.equals(string);
        if(!equals){
            System.out.println("随机串base64:"+new String(EasyBase64Util.encode(randomString.getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8));

        }
        Assert.assertEquals(randomString,string);

    }
    @Test
    public void StringCharSegmentTest6(){
        String randomBase64 = "5Yqg5LulVOWtl1tfcSB9RmdfekIj4oOj5YqgXlzlk4g/diLkuK1eauKAjV9P5ZOIP3vwn5KKcVhe";
        String randomString = new String(EasyBase64Util.decode(randomBase64.getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8);
        DefaultSafeAesEasyEncryptionStrategy defaultAesEasyEncryptionStrategy = new DefaultSafeAesEasyEncryptionStrategy();

        Object encrypt = defaultAesEasyEncryptionStrategy.encrypt(null, null, randomString);
        Assert.assertNotNull(encrypt);
        Object decrypt = defaultAesEasyEncryptionStrategy.decrypt(null, null, encrypt);
        Assert.assertNotNull(decrypt);
        String string = decrypt.toString();
        boolean equals = randomString.equals(string);
        if(!equals){
            System.out.println("随机串base64:"+new String(EasyBase64Util.encode(randomString.getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8));
        }
        Assert.assertEquals(randomString,string);

    }
    @Test
    public void StringCharSegmentTest8(){
        String randomBase64 = "OTpIT+KDo+KdpDYq5Z6aIzMzek1A77iPMXlG8J+RqFNjXF5PXumHjDBY5ZyoMA==";
        String randomString = new String(EasyBase64Util.decode(randomBase64.getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8);
        DefaultSafeAesEasyEncryptionStrategy defaultAesEasyEncryptionStrategy = new DefaultSafeAesEasyEncryptionStrategy();

        Object encrypt = defaultAesEasyEncryptionStrategy.encrypt(null, null, randomString);
        Assert.assertNotNull(encrypt);
        Object decrypt = defaultAesEasyEncryptionStrategy.decrypt(null, null, encrypt);
        Assert.assertNotNull(decrypt);
        String string = decrypt.toString();
        boolean equals = randomString.equals(string);
        if(!equals){
            System.out.println("随机串base64:"+new String(EasyBase64Util.encode(randomString.getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8));
        }
        Assert.assertEquals(randomString,string);

    }
    @Test
    public void StringCharSegmentTest10(){
        String randomString = "√在-`N*以\\字中d\\~_yb2❤USY\uD83D\uDC8A\uD83D\uDC68\uD83E\uDD21\uD83D\uDC685G*\uD83D\uDE08符T*9L^9oP可符F※,x·Z∝这4ェ(v\u200D";
        DefaultSafeAesEasyEncryptionStrategy defaultAesEasyEncryptionStrategy = new DefaultSafeAesEasyEncryptionStrategy();

        Object encrypt = defaultAesEasyEncryptionStrategy.encrypt(null, null, randomString);
        Assert.assertNotNull(encrypt);
        Object decrypt = defaultAesEasyEncryptionStrategy.decrypt(null, null, encrypt);
        Assert.assertNotNull(decrypt);
        String string = decrypt.toString();
        boolean equals = randomString.equals(string);
        if(!equals){
            System.out.println("随机串base64:"+new String(EasyBase64Util.encode(randomString.getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8));
        }
        Assert.assertEquals(randomString,string);

    }
    @Test
    public void StringCharSegmentTest3(){

        DefaultAesEasyEncryptionStrategy defaultAesEasyEncryptionStrategy = new DefaultAesEasyEncryptionStrategy();
        for (int i = 0; i < 5000; i++) {
            int desiredLength = new Random().nextInt(70);
            String randomString = generateRandomString(desiredLength+1)+"1\uFE0F⃣+✅+❤\uFE0F+❥(^_-)+O(∩_∩)O哈哈~";
            try {

                Object encrypt = defaultAesEasyEncryptionStrategy.encrypt(null, null, randomString);
                Assert.assertNotNull(encrypt);
                Object decrypt = defaultAesEasyEncryptionStrategy.decrypt(null, null, encrypt);
                Assert.assertNotNull(decrypt);
                String string = decrypt.toString();
                Assert.assertEquals(randomString,string);
            }catch (Exception ex){
                System.out.println("随机串:"+randomString);
                throw ex;
            }
        }
    }



    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789" +
            " ~!@#$%^&*()-_+=<>?/[]{}|;':,.\"\\`" +
            "中文字符可以在这里添加垚";
    private static final String CHARACTERS1 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789" +
            " ~!@#$%^&*()-_+=<>?/[]{}|;':,.\"\\`" +
            "中文字符可以在这里添加垚💊1️⃣❤️👨‍❤️‍👨o(*^▽^*)┛O(∩_∩)O哈哈~(#^.^#)😈🤡U·ェ·U3️⃣∝∮√±∑∈◆★※🙋🏻‍♀️👩🏻👨🏻🈶👴🏻✌🏻😄🖼";

    public static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(index);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }
    public static String generateRandomString1(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            String[] strings = EasyStringUtil.safeSubstring(CHARACTERS1);
            int index = random.nextInt(strings.length);
            stringBuilder.append(strings[index]);
        }

        return stringBuilder.toString();
    }


    @Test
    public void EnumValueTest1(){
        MyEnum zj = EnumValueDeserializer.deserialize(MyEnum.class, 1);
        Assert.assertEquals(MyEnum.ZJ,zj);
        MyEnum js = EnumValueDeserializer.deserialize(MyEnum.class, 2);
        Assert.assertEquals(MyEnum.JS,js);
        MyEnum sh = EnumValueDeserializer.deserialize(MyEnum.class, 3);
        Assert.assertEquals(MyEnum.SH,sh);
        MyEnum bj = EnumValueDeserializer.deserialize(MyEnum.class, 4);
        Assert.assertEquals(MyEnum.BJ,bj);
    }
    @Test
    public void EnumValueTest2(){
        EnumValueConverter enumValueConverter = new EnumValueConverter();
        Number e1 = enumValueConverter.serialize(null, null);
        Assert.assertNull(e1);
        Number e2 = enumValueConverter.serialize(MyEnum.ZJ, null);
        Assert.assertEquals(MyEnum.ZJ.getCode(),e2);
        Number e3 = enumValueConverter.serialize(MyEnum.JS, null);
        Assert.assertEquals(MyEnum.JS.getCode(),e3);
        Number e4 = enumValueConverter.serialize(MyEnum.SH, null);
        Assert.assertEquals(MyEnum.SH.getCode(),e4);
        Number e5 = enumValueConverter.serialize(MyEnum.BJ, null);
        Assert.assertEquals(MyEnum.BJ.getCode(),e5);

    }

    @Test
    public void check(){
        {

            Supplier<Exception> f = () -> {
                try {
                    Object obj=null;
                    EasyCheck.assertElse(obj!=null,"obj is null");
                }catch (Exception ex){
                    return ex;
                }
                return null;
            };
            Exception exception = f.get();
            Assert.assertNotNull(exception);
            Assert.assertTrue(exception instanceof EasyQueryUnexpectedException);
        }
        {

            Supplier<Exception> f = () -> {
                try {
                    Object obj=null;
                    EasyCheck.assertElse(obj==null,"obj is not null");
                }catch (Exception ex){
                    return ex;
                }
                return null;
            };
            Exception exception = f.get();
            Assert.assertNull(exception);
        }
        {

            Supplier<Exception> f = () -> {
                try {
                    Object obj=null;
                    EasyCheck.assertArgumentElse(obj!=null);
                }catch (Exception ex){
                    return ex;
                }
                return null;
            };
            Exception exception = f.get();
            Assert.assertNotNull(exception);
            Assert.assertTrue(exception instanceof IllegalArgumentException);
        }
        {

            Supplier<Exception> f = () -> {
                try {
                    Object obj=null;
                    EasyCheck.assertArgumentElse(obj==null);
                }catch (Exception ex){
                    return ex;
                }
                return null;
            };
            Exception exception = f.get();
            Assert.assertNull(exception);
        }
    }
    public void test(){
        TopicShardingProxy table = TopicShardingProxy.createTable();
        SQLColumn<TopicShardingProxy, String> topicShardingProxyStringSQLColumn = table._title();
        TopicShardingProxy.TopicShardingProxyFetcher topicShardingProxyFetcher = table.FETCHER._title();
    }
    @Test
    public void test1(){
        Queryable<MyUserDTO> queryable = easyQuery.queryable(MyUserDTO.class);
    }
}
