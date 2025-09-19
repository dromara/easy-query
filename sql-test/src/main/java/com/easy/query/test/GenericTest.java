package com.easy.query.test;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.util.JdbcConstants;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.common.LinkedCaseInsensitiveMap;
import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.configuration.nameconversion.impl.LowerCamelCaseNameConversion;
import com.easy.query.core.configuration.nameconversion.impl.UnderlinedNameConversion;
import com.easy.query.core.configuration.nameconversion.impl.UpperCamelCaseNameConversion;
import com.easy.query.core.configuration.nameconversion.impl.UpperUnderlinedNameConversion;
import com.easy.query.core.enums.ContextTypeEnum;
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
import com.easy.query.core.expression.sql.builder.EasyExpressionContext;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.ColumnOption;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.util.EasyAesUtil;
import com.easy.query.core.util.EasyBase64Util;
import com.easy.query.core.util.EasyBeanUtil;
import com.easy.query.core.util.EasyBitwiseUtil;
import com.easy.query.core.util.EasyCheck;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyDatabaseUtil;
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
import com.easy.query.test.entity.UnknownTable;
import com.easy.query.test.entity.notable.QueryLargeColumnTestEntity;
import com.easy.query.test.entity.proxy.TopicShardingProxy;
import com.easy.query.test.increment.MyDatabaseIncrementSQLColumnGenerator;
import com.easy.query.test.interceptor.MyEntityInterceptor;
import com.easy.query.test.logicdel.MyLogicDelStrategy;
import com.easy.query.test.testvo.MyUserDTO;
import io.agroal.api.AgroalDataSource;
import io.agroal.api.configuration.AgroalConnectionFactoryConfiguration;
import io.agroal.api.configuration.AgroalConnectionPoolConfiguration;
import io.agroal.api.configuration.AgroalDataSourceConfiguration;
import io.agroal.api.configuration.supplier.AgroalDataSourceConfigurationSupplier;
import io.agroal.api.security.SimplePassword;
import org.junit.Assert;
import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.attribute.UserPrincipal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * @author xuejiaming
 * @FileName: GenericTest.java
 * @Description: 文件说明
 * create time 2023/3/17 22:22
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
        String sql = easyEntityQuery.queryable(QueryLargeColumnTestEntity.class).toSQL();
        Assert.assertEquals("SELECT `id`,`name`,`content` FROM `query_large_column_test`", sql);
    }

    @Test
    public void queryLargeColumnTest3() {
        try {

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            long l = easyEntityQuery.insertable(queryLargeColumnTestEntity).executeRows();
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
    public void queryLargeColumnTest9() {
        QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
        queryLargeColumnTestEntity.setId("123");
        long l = easyEntityQuery.updatable(queryLargeColumnTestEntity).setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS).executeRows();
        Assert.assertEquals(0, l);
    }

    @Test
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

    @Test
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

    @Test
    public void queryLargeColumnTest12() {
        QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
        queryLargeColumnTestEntity.setId("123");
        queryLargeColumnTestEntity.setName("123");
        queryLargeColumnTestEntity.setContent("123");
        long l = easyEntityQuery.updatable(queryLargeColumnTestEntity).setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS).executeRows();
        Assert.assertEquals(0, l);
    }

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
    public void queryLargeColumnTest15() {
        QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
        queryLargeColumnTestEntity.setId("123");
        String sql = easyEntityQuery.deletable(queryLargeColumnTestEntity).toSQL();
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
    public void createTest1() {
        FastBean fastBean = EasyBeanUtil.getFastBean(BlogEntity.class);
        Supplier<Object> lambdaCreate = fastBean.getBeanConstructorCreator();
        Object o = lambdaCreate.get();
        Assert.assertNotNull(o);
        Assert.assertTrue(o instanceof BlogEntity);
        BlogEntity blogEntity = (BlogEntity) o;
        Assert.assertNotNull(blogEntity);
        {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 100000; i++) {
                BlogEntity blogEntity1 = EasyClassUtil.newInstance(BlogEntity.class);
            }
            long end = System.currentTimeMillis();
            System.out.println((end - start) + "(ms)");
        }
        {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 100000; i++) {
                BlogEntity blogEntity1 = EasyObjectUtil.typeCastNullable(lambdaCreate.get());
            }
            long end = System.currentTimeMillis();
            System.out.println((end - start) + "(ms)");
        }
        Object o1 = lambdaCreate.get();
        Object o2 = lambdaCreate.get();
        Assert.assertFalse(o1 == o2);
    }

    @Test
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


    @Test
    public void nameConversionTest1() {
        {
            LowerCamelCaseNameConversion lowerCamelCaseNameConversion = new LowerCamelCaseNameConversion();
            {
                String nameConversion = "NameConversion";
                String convert = lowerCamelCaseNameConversion.convert(nameConversion);
                Assert.assertEquals("nameConversion", convert);
            }
            {
                String nameConversion = "nameConversion";
                String convert = lowerCamelCaseNameConversion.convert(nameConversion);
                Assert.assertEquals("nameConversion", convert);
            }
        }
        {

            UpperCamelCaseNameConversion upperCamelCaseNameConversion = new UpperCamelCaseNameConversion();
            {
                String nameConversion = "NameConversion";
                String convert = upperCamelCaseNameConversion.convert(nameConversion);
                Assert.assertEquals("NameConversion", convert);
            }
            {
                String nameConversion = "nameConversion";
                String convert = upperCamelCaseNameConversion.convert(nameConversion);
                Assert.assertEquals("NameConversion", convert);
            }
        }
        {
            UnderlinedNameConversion underlinedNameConversion = new UnderlinedNameConversion();
            {
                String nameConversion = "NameConversion";
                String convert = underlinedNameConversion.convert(nameConversion);
                Assert.assertEquals("name_conversion", convert);
            }
            {
                String nameConversion = "nameConversion";
                String convert = underlinedNameConversion.convert(nameConversion);
                Assert.assertEquals("name_conversion", convert);
            }
        }
        {
            UpperUnderlinedNameConversion upperUnderlinedNameConversion = new UpperUnderlinedNameConversion();
            {
                String nameConversion = "NameConversion";
                String convert = upperUnderlinedNameConversion.convert(nameConversion);
                Assert.assertEquals("NAME_CONVERSION", convert);
            }
            {
                String nameConversion = "nameConversion";
                String convert = upperUnderlinedNameConversion.convert(nameConversion);
                Assert.assertEquals("NAME_CONVERSION", convert);
            }
        }
    }

    @Test
    public void StringCharSegmentTest1() {
        List<String> a = EasyStringUtil.getStringCharSegments("aaa啊啊a", 4, 1, 2);
        String as = a.toString();
        Assert.assertEquals("[aaa啊, aa啊, a啊啊, 啊啊, 啊a]", as);
        List<String> a1 = EasyStringUtil.getStringCharSegments("aaa啊啊", 4, 1, 2);
        String a1s = a1.toString();
        Assert.assertEquals("[aaa啊, aa啊, a啊啊, 啊啊]", a1s);
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
    public void StringCharSegmentTest2() {
        DefaultAesEasyEncryptionStrategy defaultAesEasyEncryptionStrategy = new DefaultAesEasyEncryptionStrategy();
        for (int i = 0; i < 8000; i++) {
            int desiredLength = new Random().nextInt(40);
            String randomString = generateRandomString(desiredLength + 1);
            try {

                Object encrypt = defaultAesEasyEncryptionStrategy.encrypt(null, null, randomString);
                Assert.assertNotNull(encrypt);
                Object decrypt = defaultAesEasyEncryptionStrategy.decrypt(null, null, encrypt);
                Assert.assertNotNull(decrypt);
                String string = decrypt.toString();
                boolean equals = randomString.equals(string);
                if (!equals) {
                    String s = new String(EasyBase64Util.encode(randomString.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
                    System.out.println("随机串base64:" + s);
                }
                Assert.assertEquals(randomString, string);
            } catch (Exception ex) {
                System.out.println("随机串:" + randomString);
                throw ex;
            }
        }
    }

    @Test
    public void StringCharSegmentTest7() {
        DefaultSafeAesEasyEncryptionStrategy defaultAesEasyEncryptionStrategy = new DefaultSafeAesEasyEncryptionStrategy();
        for (int i = 0; i < 20000; i++) {
            int desiredLength = new Random().nextInt(60);
            String randomString = generateRandomString1(desiredLength + 11);
            try {

                Object encrypt = defaultAesEasyEncryptionStrategy.encrypt(null, null, randomString);
                Assert.assertNotNull(encrypt);
                Object decrypt = defaultAesEasyEncryptionStrategy.decrypt(null, null, encrypt);
                Assert.assertNotNull(decrypt);
                String string = decrypt.toString();
                boolean equals = randomString.equals(string);
                if (!equals) {
                    String s = new String(EasyBase64Util.encode(randomString.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
                    System.out.println("随机串base64:" + s);
                }
                Assert.assertEquals(randomString, string);
            } catch (Exception ex) {
                System.out.println("随机串:" + randomString);
                String s = new String(EasyBase64Util.encode(randomString.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
                System.out.println("随机串base64:" + s);
                throw ex;
            }
        }
    }

    @Test
    public void StringCharSegmentTest4() {
        String randomString = "qO)3\\{*h\uFE0F�MbjT";
        DefaultAesEasyEncryptionStrategy defaultAesEasyEncryptionStrategy = new DefaultAesEasyEncryptionStrategy();

        Object encrypt = defaultAesEasyEncryptionStrategy.encrypt(null, null, randomString);
        Assert.assertNotNull(encrypt);
        Object decrypt = defaultAesEasyEncryptionStrategy.decrypt(null, null, encrypt);
        Assert.assertNotNull(decrypt);
        String string = decrypt.toString();
        boolean equals = randomString.equals(string);
        if (!equals) {
            System.out.println("随机串base64:" + new String(EasyBase64Util.encode(randomString.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
        }
        Assert.assertEquals(randomString, string);

    }

    @Test
    public void StringCharSegmentTest5() {
        String randomBase64 = "NSxcLu+4j35ocjElWTEpTyUr5Y+ve+WKoDdiIFlkLj85L2An77iPTkM5UEI=";
        String randomString = new String(EasyBase64Util.decode(randomBase64.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        DefaultAesEasyEncryptionStrategy defaultAesEasyEncryptionStrategy = new DefaultAesEasyEncryptionStrategy();

        Object encrypt = defaultAesEasyEncryptionStrategy.encrypt(null, null, randomString);
        Assert.assertNotNull(encrypt);
        Object decrypt = defaultAesEasyEncryptionStrategy.decrypt(null, null, encrypt);
        Assert.assertNotNull(decrypt);
        String string = decrypt.toString();
        boolean equals = randomString.equals(string);
        if (!equals) {
            System.out.println("随机串base64:" + new String(EasyBase64Util.encode(randomString.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));

        }
        Assert.assertEquals(randomString, string);

    }

    @Test
    public void StringCharSegmentTest6() {
        String randomBase64 = "5Yqg5LulVOWtl1tfcSB9RmdfekIj4oOj5YqgXlzlk4g/diLkuK1eauKAjV9P5ZOIP3vwn5KKcVhe";
        String randomString = new String(EasyBase64Util.decode(randomBase64.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        DefaultSafeAesEasyEncryptionStrategy defaultAesEasyEncryptionStrategy = new DefaultSafeAesEasyEncryptionStrategy();

        Object encrypt = defaultAesEasyEncryptionStrategy.encrypt(null, null, randomString);
        Assert.assertNotNull(encrypt);
        Object decrypt = defaultAesEasyEncryptionStrategy.decrypt(null, null, encrypt);
        Assert.assertNotNull(decrypt);
        String string = decrypt.toString();
        boolean equals = randomString.equals(string);
        if (!equals) {
            System.out.println("随机串base64:" + new String(EasyBase64Util.encode(randomString.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
        }
        Assert.assertEquals(randomString, string);

    }

    @Test
    public void StringCharSegmentTest8() {
        String randomBase64 = "OTpIT+KDo+KdpDYq5Z6aIzMzek1A77iPMXlG8J+RqFNjXF5PXumHjDBY5ZyoMA==";
        String randomString = new String(EasyBase64Util.decode(randomBase64.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        DefaultSafeAesEasyEncryptionStrategy defaultAesEasyEncryptionStrategy = new DefaultSafeAesEasyEncryptionStrategy();

        Object encrypt = defaultAesEasyEncryptionStrategy.encrypt(null, null, randomString);
        Assert.assertNotNull(encrypt);
        Object decrypt = defaultAesEasyEncryptionStrategy.decrypt(null, null, encrypt);
        Assert.assertNotNull(decrypt);
        String string = decrypt.toString();
        boolean equals = randomString.equals(string);
        if (!equals) {
            System.out.println("随机串base64:" + new String(EasyBase64Util.encode(randomString.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
        }
        Assert.assertEquals(randomString, string);

    }

    @Test
    public void StringCharSegmentTest10() {
        String randomString = "√在-`N*以\\字中d\\~_yb2❤USY\uD83D\uDC8A\uD83D\uDC68\uD83E\uDD21\uD83D\uDC685G*\uD83D\uDE08符T*9L^9oP可符F※,x·Z∝这4ェ(v\u200D";
        DefaultSafeAesEasyEncryptionStrategy defaultAesEasyEncryptionStrategy = new DefaultSafeAesEasyEncryptionStrategy();

        Object encrypt = defaultAesEasyEncryptionStrategy.encrypt(null, null, randomString);
        Assert.assertNotNull(encrypt);
        Object decrypt = defaultAesEasyEncryptionStrategy.decrypt(null, null, encrypt);
        Assert.assertNotNull(decrypt);
        String string = decrypt.toString();
        boolean equals = randomString.equals(string);
        if (!equals) {
            System.out.println("随机串base64:" + new String(EasyBase64Util.encode(randomString.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
        }
        Assert.assertEquals(randomString, string);

    }

    @Test
    public void StringCharSegmentTest3() {

        DefaultAesEasyEncryptionStrategy defaultAesEasyEncryptionStrategy = new DefaultAesEasyEncryptionStrategy();
        for (int i = 0; i < 5000; i++) {
            int desiredLength = new Random().nextInt(70);
            String randomString = generateRandomString(desiredLength + 1) + "1\uFE0F⃣+✅+❤\uFE0F+❥(^_-)+O(∩_∩)O哈哈~";
            try {

                Object encrypt = defaultAesEasyEncryptionStrategy.encrypt(null, null, randomString);
                Assert.assertNotNull(encrypt);
                Object decrypt = defaultAesEasyEncryptionStrategy.decrypt(null, null, encrypt);
                Assert.assertNotNull(decrypt);
                String string = decrypt.toString();
                Assert.assertEquals(randomString, string);
            } catch (Exception ex) {
                System.out.println("随机串:" + randomString);
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
    public void EnumValueTest1() {
        MyEnum zj = EnumValueDeserializer.deserialize(MyEnum.class, 1);
        Assert.assertEquals(MyEnum.ZJ, zj);
        MyEnum js = EnumValueDeserializer.deserialize(MyEnum.class, 2);
        Assert.assertEquals(MyEnum.JS, js);
        MyEnum sh = EnumValueDeserializer.deserialize(MyEnum.class, 3);
        Assert.assertEquals(MyEnum.SH, sh);
        MyEnum bj = EnumValueDeserializer.deserialize(MyEnum.class, 4);
        Assert.assertEquals(MyEnum.BJ, bj);
    }

    @Test
    public void EnumValueTest2() {
        EnumValueConverter enumValueConverter = new EnumValueConverter();
        ColumnOption columnOption = new ColumnOption(false, new EntityMetadata(Object.class), "", "", "");
        columnOption.setSetterCaller((a, b) -> {
        });
        columnOption.setGetterCaller(s -> null);
        ColumnMetadata columnMetadata = new ColumnMetadata(columnOption);
        Number e1 = enumValueConverter.serialize(null, columnMetadata);
        Assert.assertNull(e1);
        Number e2 = enumValueConverter.serialize(MyEnum.ZJ, columnMetadata);
        Assert.assertEquals(MyEnum.ZJ.getCode(), e2);
        Number e3 = enumValueConverter.serialize(MyEnum.JS, columnMetadata);
        Assert.assertEquals(MyEnum.JS.getCode(), e3);
        Number e4 = enumValueConverter.serialize(MyEnum.SH, columnMetadata);
        Assert.assertEquals(MyEnum.SH.getCode(), e4);
        Number e5 = enumValueConverter.serialize(MyEnum.BJ, columnMetadata);
        Assert.assertEquals(MyEnum.BJ.getCode(), e5);

    }

    @Test
    public void check() {
        {

            Supplier<Exception> f = () -> {
                try {
                    Object obj = null;
                    EasyCheck.assertElse(obj != null, "obj is null");
                } catch (Exception ex) {
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
                    Object obj = null;
                    EasyCheck.assertElse(obj == null, "obj is not null");
                } catch (Exception ex) {
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
                    Object obj = null;
                    EasyCheck.assertArgumentElse(obj != null);
                } catch (Exception ex) {
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
                    Object obj = null;
                    EasyCheck.assertArgumentElse(obj == null);
                } catch (Exception ex) {
                    return ex;
                }
                return null;
            };
            Exception exception = f.get();
            Assert.assertNull(exception);
        }
    }

    public void test() {
        TopicShardingProxy table = TopicShardingProxy.createTable();
        SQLColumn<TopicShardingProxy, String> topicShardingProxyStringSQLColumn = table._title();
        TopicShardingProxy.TopicShardingProxyFetcher topicShardingProxyFetcher = table.FETCHER._title();
    }

    @Test
    public void test1() {

        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(MyUserDTO.class, Object.class);
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        Map<String, Field> staticFields = new HashMap<>();
        Collection<Field> allFields = EasyClassUtil.getAllFields(MyUserDTO.class, staticFields);

    }

    @Test
    public void testPrintSQL1() {
        EasyExpressionContext easyExpressionContext = new EasyExpressionContext(easyEntityQuery.getRuntimeContext(), ContextTypeEnum.QUERY);
        easyExpressionContext.setPrintSQL(false);
        easyExpressionContext.setPrintNavSQL(false);
        ExpressionContext expressionContext = easyExpressionContext.cloneExpressionContext();
        Assert.assertEquals(false, expressionContext.getPrintSQL());
        Assert.assertEquals(false, expressionContext.getPrintNavSQL());
    }

    @Test
    public void testPrintSQL2() {
        EasyExpressionContext easyExpressionContext = new EasyExpressionContext(easyEntityQuery.getRuntimeContext(), ContextTypeEnum.QUERY);
        easyExpressionContext.setPrintSQL(null);
        easyExpressionContext.setPrintNavSQL(true);
        ExpressionContext expressionContext = easyExpressionContext.cloneExpressionContext();
        Assert.assertNull(expressionContext.getPrintSQL());
        Assert.assertEquals(true, expressionContext.getPrintNavSQL());
    }

    @Test
    public void sqlFormat() {
        SQLUtils.FormatOption opt = new SQLUtils.FormatOption(true, true); // (ucase, pretty)
        String s1 = SQLUtils.format("SELECT IFNULL(t2.`__sum2__`,0) AS `value1`,IFNULL(t2.`__sum3__`,0) AS `value2` FROM `m8_province` t LEFT JOIN (SELECT t1.`pid` AS `pid`,SUM(IFNULL(t4.`__count2__`,0)) AS `__sum2__`,SUM(IFNULL(t4.`__count3__`,0)) AS `__sum3__` FROM `m8_city` t1 LEFT JOIN (SELECT t3.`cid` AS `cid`,COUNT((CASE WHEN t3.`name` LIKE CONCAT('%',?,'%') THEN ? ELSE NULL END)) AS `__count2__`,COUNT((CASE WHEN t3.`name` LIKE CONCAT('%',?,'%') THEN ? ELSE NULL END)) AS `__count3__` FROM `m8_area` t3 WHERE t3.`id` LIKE CONCAT('%',?,'%') GROUP BY t3.`cid`) t4 ON t4.`cid` = t1.`id` GROUP BY t1.`pid`) t2 ON t2.`pid` = t.`id` WHERE t.`name` LIKE CONCAT('%',?,'%')", JdbcConstants.KINGBASE, opt);
        System.out.println(s1);
    }

    @Test
    public void testLinkedCaseInsensitiveMap1() {
        LinkedCaseInsensitiveMap<Object> objectLinkedCaseInsensitiveMap = new LinkedCaseInsensitiveMap<>();
        objectLinkedCaseInsensitiveMap.put("ID", "123");
        objectLinkedCaseInsensitiveMap.put("Name", "123");
        int i = 0;
        for (String s : objectLinkedCaseInsensitiveMap.keySet()) {
            if (i == 0) {
                Assert.assertEquals("ID", s);
            } else {
                Assert.assertEquals("Name", s);
            }
            i++;
        }
    }


    @Test
     public void testAgroalGetUrl() throws SQLException {

        // 1. 配置数据源
        AgroalDataSourceConfigurationSupplier configurationSupplier = new AgroalDataSourceConfigurationSupplier();

        // 设置 JDBC 连接 URL、用户名和密码
        configurationSupplier
                .connectionPoolConfiguration(cp -> cp
                        .maxSize(10) // 连接池最大连接数
                        .minSize(3)  // 连接池最小连接数
                        .connectionFactoryConfiguration(cf -> cf
                                .jdbcUrl("jdbc:mysql://127.0.0.1:3316/easy-query-test?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true&allowPublicKeyRetrieval=true")
                                .connectionProviderClassName("com.mysql.cj.jdbc.Driver")
                                .principal(new UserPrincipal() {
                                    @Override
                                    public String getName() {
                                        return "root";
                                    }
                                })
                                .credential(new SimplePassword("root"))
                        )
                );

        // 2. 根据配置创建数据源
        AgroalDataSourceConfiguration dataSourceConfiguration = configurationSupplier.get();


         // 4. 创建数据源
         try (AgroalDataSource dataSource = AgroalDataSource.from(dataSourceConfiguration)) {
             // 5. 正常使用 JDBC
             try (Connection conn = dataSource.getConnection();
                  Statement stmt = conn.createStatement();
                  ResultSet rs = stmt.executeQuery("SELECT 1")) {
                 while (rs.next()) {
                     System.out.println("Result = " + rs.getInt(1));
                 }
             }
             String jdbcUrlByReflection = EasyDatabaseUtil.getJdbcUrlByReflection(dataSource);
             Assert.assertEquals("",jdbcUrlByReflection);
         }
     }
}
