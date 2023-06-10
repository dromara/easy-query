package com.easy.query.test;

import com.easy.query.core.common.LinkedCaseInsensitiveMap;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.enums.SQLRangeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.exception.EasyQueryNoPrimaryKeyException;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;
import com.easy.query.core.util.EasyAesUtil;
import com.easy.query.core.util.EasyBase64Util;
import com.easy.query.core.util.EasyBitwiseUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.test.encryption.DefaultAesEasyEncryptionStrategy;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.NoKeyEntity;
import com.easy.query.test.entity.UnknownTable;
import com.easy.query.test.entity.notable.QueryLargeColumnTestEntity;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.UUID;

/**
 * @FileName: GenericTest.java
 * @Description: 文件说明
 * @Date: 2023/3/17 22:22
 * @author xuejiaming
 */
public class GenericTest extends BaseTest{

    @Test
    public void SqlRangeTest1(){
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
    public void base64Test(){
        String uuid = UUID.randomUUID().toString();
        byte[] bytes = uuid.getBytes(StandardCharsets.UTF_8);
        byte[] encode = EasyBase64Util.encode(bytes);
        byte[] decode = EasyBase64Util.decode(encode);
        Assert.assertArrayEquals(bytes,decode);
        Assert.assertEquals(uuid,new String(decode,StandardCharsets.UTF_8));
    }
    private final String key="abcdef1234567890";//16位的秘钥
    private final String iv="A-16-Byte-String";//16位的iv

    @Test
    public void aesTest2(){
        String uuid = UUID.randomUUID().toString();
        String encryptToString = EasyAesUtil.encrypt(uuid,key , iv,StandardCharsets.UTF_8);
        String decryptToString = EasyAesUtil.decrypt(encryptToString, key, iv,StandardCharsets.UTF_8);

        Assert.assertEquals(uuid,decryptToString);

    }
    @Test
    public void easyEncryptionTest(){

        DefaultAesEasyEncryptionStrategy aesEasyEncryptionStrategy = new DefaultAesEasyEncryptionStrategy();
        String xx = "❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️垚垚垚垚垚垚";

        Object encryptValue = aesEasyEncryptionStrategy.encrypt(null,null,xx);
        Object decryptValue = aesEasyEncryptionStrategy.decrypt(null,null,encryptValue);
        Assert.assertEquals(xx,decryptValue);

        String phone="18888888888";
        Object encrypt = aesEasyEncryptionStrategy.encrypt(null,null,phone);
        Object decrypt = aesEasyEncryptionStrategy.decrypt(null,null,encrypt);
        Assert.assertEquals(phone,decrypt);
    }
    @Test
    public void easyEncryptionTest1(){
        DefaultAesEasyEncryptionStrategy aesEasyEncryptionStrategy = new DefaultAesEasyEncryptionStrategy();
        String xx = "188888881212";

        Object encryptValue = aesEasyEncryptionStrategy.encrypt(null,null,xx);
        Object decryptValue = aesEasyEncryptionStrategy.decrypt(null,null,encryptValue);
        Assert.assertEquals(xx,decryptValue);
        Object encryptValue1 = aesEasyEncryptionStrategy.encrypt(null,null,"1888888812");
        Assert.assertTrue(EasyStringUtil.startsWith(encryptValue.toString(),encryptValue1.toString()));
    }
    @Test
    public void behavior(){
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
        Assert.assertTrue(easyBehavior.isDefaultBehavior());
    }

    @Test
    public void executeMethodBehaviorTest1(){
        int code = ExecuteMethodEnum.UNKNOWN.getCode();
        code = EasyBitwiseUtil.addBit(code, ExecuteMethodEnum.ANY.getCode());
        Assert.assertNotEquals(code,ExecuteMethodEnum.UNKNOWN.getCode());
        Assert.assertTrue(EasyBitwiseUtil.hasBit(code, ExecuteMethodEnum.ANY.getCode()));
        Assert.assertFalse(EasyBitwiseUtil.hasBit(code, ExecuteMethodEnum.ALL.getCode()));
        code = EasyBitwiseUtil.removeBit(code, ExecuteMethodEnum.ALL.getCode());
        Assert.assertNotEquals(code,ExecuteMethodEnum.UNKNOWN.getCode());
        Assert.assertTrue(EasyBitwiseUtil.hasBit(code, ExecuteMethodEnum.ANY.getCode()));
        code = EasyBitwiseUtil.removeBit(code, ExecuteMethodEnum.ANY.getCode());
        Assert.assertFalse(EasyBitwiseUtil.hasBit(code, ExecuteMethodEnum.ANY.getCode()));
        Assert.assertEquals(code,ExecuteMethodEnum.UNKNOWN.getCode());
    }
    @Test
    public void queryLargeColumnTest1(){
        String sql = easyQuery.queryable(QueryLargeColumnTestEntity.class).toSQL();
        Assert.assertEquals("SELECT `id`,`name`,`content` FROM `query_large_column_test`",sql);
    }
    @Test
    public void queryLargeColumnTest2(){
        String sql = easyQuery.queryable(QueryLargeColumnTestEntity.class).queryLargeColumn(false).toSQL();
        Assert.assertEquals("SELECT `id`,`name` FROM `query_large_column_test`",sql);
    }
    @Test
    public void queryLargeColumnTest3(){
        try{

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            long l = easyQuery.insertable(queryLargeColumnTestEntity).executeRows();
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex.getMessage().startsWith("not found insert columns :"));
        }
    }
    @Test
    public void queryLargeColumnTest4(){
        try{

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyQuery.insertable(queryLargeColumnTestEntity).executeRows();
        }catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("INSERT INTO `query_large_column_test` (`id`) VALUES (?)", ex1.getSQL());
            Assert.assertEquals("java.sql.BatchUpdateException: Table 'easy-query-test.query_large_column_test' doesn't exist", ex1.getMessage());
        }
    }
    @Test
    public void queryLargeColumnTest5(){
        try{

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyQuery.insertable(queryLargeColumnTestEntity).setSQLStrategy(SQLExecuteStrategyEnum.DEFAULT).executeRows();
        }catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("INSERT INTO `query_large_column_test` (`id`) VALUES (?)", ex1.getSQL());
            Assert.assertEquals("java.sql.BatchUpdateException: Table 'easy-query-test.query_large_column_test' doesn't exist", ex1.getMessage());
        }
    }
    @Test
    public void queryLargeColumnTest6(){
        try{

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyQuery.insertable(queryLargeColumnTestEntity).setSQLStrategy(SQLExecuteStrategyEnum.ALL_COLUMNS).executeRows();
        }catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("INSERT INTO `query_large_column_test` (`id`,`name`,`content`) VALUES (?,?,?)", ex1.getSQL());
            Assert.assertEquals("java.sql.BatchUpdateException: Table 'easy-query-test.query_large_column_test' doesn't exist", ex1.getMessage());
        }
    }
    @Test
    public void queryLargeColumnTest7(){
        try{

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyQuery.insertable(queryLargeColumnTestEntity).setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS).executeRows();
        }catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("INSERT INTO `query_large_column_test` (`name`,`content`) VALUES (?,?)", ex1.getSQL());
            Assert.assertEquals("java.sql.BatchUpdateException: Table 'easy-query-test.query_large_column_test' doesn't exist", ex1.getMessage());
        }
    }
    @Test
    public void queryLargeColumnTest8(){
        try{

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyQuery.updatable(queryLargeColumnTestEntity).executeRows();
        }catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("UPDATE `query_large_column_test` SET `name` = ?,`content` = ? WHERE `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.BatchUpdateException: Table 'easy-query-test.query_large_column_test' doesn't exist", ex1.getMessage());
        }
    }
    @Test
    public void queryLargeColumnTest9(){
        QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
        queryLargeColumnTestEntity.setId("123");
        long l = easyQuery.updatable(queryLargeColumnTestEntity).setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS).executeRows();
        Assert.assertEquals(0,l);
    }
    @Test
    public void queryLargeColumnTest10(){
        try{

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            queryLargeColumnTestEntity.setName("123");
            long l = easyQuery.updatable(queryLargeColumnTestEntity).setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS).executeRows();
        }catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("UPDATE `query_large_column_test` SET `name` = ? WHERE `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.BatchUpdateException: Table 'easy-query-test.query_large_column_test' doesn't exist", ex1.getMessage());
        }
    }
    @Test
    public void queryLargeColumnTest11(){
        try{

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            queryLargeColumnTestEntity.setName("123");
            long l = easyQuery.updatable(queryLargeColumnTestEntity).setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS).executeRows();
        }catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("UPDATE `query_large_column_test` SET `content` = ? WHERE `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.BatchUpdateException: Table 'easy-query-test.query_large_column_test' doesn't exist", ex1.getMessage());
        }
    }
    @Test
    public void queryLargeColumnTest12(){
        QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
        queryLargeColumnTestEntity.setId("123");
        queryLargeColumnTestEntity.setName("123");
        queryLargeColumnTestEntity.setContent("123");
        long l = easyQuery.updatable(queryLargeColumnTestEntity).setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS).executeRows();
        Assert.assertEquals(0,l);
    }
    @Test
    public void queryLargeColumnTest13(){
        try {
            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyQuery.deletable(queryLargeColumnTestEntity).executeRows();
        }catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException ex1 = ((EasyQuerySQLStatementException) ex.getCause());
            Assert.assertEquals("DELETE FROM `query_large_column_test` WHERE `id` = ?", ex1.getSQL());
            Assert.assertEquals("java.sql.BatchUpdateException: Table 'easy-query-test.query_large_column_test' doesn't exist", ex1.getMessage());
        }
    }
    @Test
    public void queryLargeColumnTest14(){
        try {
            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyQuery.deletable(queryLargeColumnTestEntity).allowDeleteStatement(false).executeRows();
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQueryInvalidOperationException);
            Assert.assertEquals("can't execute delete statement",ex.getMessage());
        }
    }
    @Test
    public void queryLargeColumnTest15(){
        QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
        queryLargeColumnTestEntity.setId("123");
        String sql = easyQuery.deletable(queryLargeColumnTestEntity).toSQL();
        Assert.assertEquals("DELETE FROM `query_large_column_test` WHERE `id` = ?",sql);
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
            Assert.assertEquals("java.sql.BatchUpdateException: Table 'xxx.abc' doesn't exist", ex1.getMessage());
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
            Assert.assertEquals("java.sql.BatchUpdateException: Table 'xcv.query_large_column_testabc' doesn't exist", ex1.getMessage());
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

}
