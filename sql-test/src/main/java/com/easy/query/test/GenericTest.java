package com.easy.query.test;

import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.enums.SQLRangeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLException;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;
import com.easy.query.core.util.EasyAesUtil;
import com.easy.query.core.util.EasyBase64Util;
import com.easy.query.core.util.EasyBitwiseUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.test.encryption.DefaultAesEasyEncryptionStrategy;
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
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`content` FROM `query_large_column_test` t",sql);
    }
    @Test
    public void queryLargeColumnTest2(){
        String sql = easyQuery.queryable(QueryLargeColumnTestEntity.class).queryLargeColumn(false).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `query_large_column_test` t",sql);
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
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLException);
            EasyQuerySQLException ex1 = ((EasyQuerySQLException) ex.getCause());
            Assert.assertEquals("INSERT INTO `query_large_column_test` (`id`) VALUES (?)",ex1.getSQL());
            Assert.assertEquals("java.sql.BatchUpdateException: Table 'easy-query-test.query_large_column_test' doesn't exist",ex1.getMessage());
        }
    }
    @Test
    public void queryLargeColumnTest5(){
        try{

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyQuery.insertable(queryLargeColumnTestEntity).setSQLStrategy(SQLExecuteStrategyEnum.DEFAULT).executeRows();
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLException);
            EasyQuerySQLException ex1 = ((EasyQuerySQLException) ex.getCause());
            Assert.assertEquals("INSERT INTO `query_large_column_test` (`id`) VALUES (?)",ex1.getSQL());
            Assert.assertEquals("java.sql.BatchUpdateException: Table 'easy-query-test.query_large_column_test' doesn't exist",ex1.getMessage());
        }
    }
    @Test
    public void queryLargeColumnTest6(){
        try{

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyQuery.insertable(queryLargeColumnTestEntity).setSQLStrategy(SQLExecuteStrategyEnum.ALL_COLUMNS).executeRows();
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLException);
            EasyQuerySQLException ex1 = ((EasyQuerySQLException) ex.getCause());
            Assert.assertEquals("INSERT INTO `query_large_column_test` (`id`,`name`,`content`) VALUES (?,?,?)",ex1.getSQL());
            Assert.assertEquals("java.sql.BatchUpdateException: Table 'easy-query-test.query_large_column_test' doesn't exist",ex1.getMessage());
        }
    }
    @Test
    public void queryLargeColumnTest7(){
        try{

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyQuery.insertable(queryLargeColumnTestEntity).setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS).executeRows();
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLException);
            EasyQuerySQLException ex1 = ((EasyQuerySQLException) ex.getCause());
            Assert.assertEquals("INSERT INTO `query_large_column_test` (`name`,`content`) VALUES (?,?)",ex1.getSQL());
            Assert.assertEquals("java.sql.BatchUpdateException: Table 'easy-query-test.query_large_column_test' doesn't exist",ex1.getMessage());
        }
    }
    @Test
    public void queryLargeColumnTest8(){
        try{

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            long l = easyQuery.updatable(queryLargeColumnTestEntity).executeRows();
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLException);
            EasyQuerySQLException ex1 = ((EasyQuerySQLException) ex.getCause());
            Assert.assertEquals("UPDATE `query_large_column_test` SET `name` = ?,`content` = ? WHERE `id` = ?",ex1.getSQL());
            Assert.assertEquals("java.sql.BatchUpdateException: Table 'easy-query-test.query_large_column_test' doesn't exist",ex1.getMessage());
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
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLException);
            EasyQuerySQLException ex1 = ((EasyQuerySQLException) ex.getCause());
            Assert.assertEquals("UPDATE `query_large_column_test` SET `name` = ? WHERE `id` = ?",ex1.getSQL());
            Assert.assertEquals("java.sql.BatchUpdateException: Table 'easy-query-test.query_large_column_test' doesn't exist",ex1.getMessage());
        }
    }
    @Test
    public void queryLargeColumnTest11(){
        try{

            QueryLargeColumnTestEntity queryLargeColumnTestEntity = new QueryLargeColumnTestEntity();
            queryLargeColumnTestEntity.setId("123");
            queryLargeColumnTestEntity.setName("123");
            long l = easyQuery.updatable(queryLargeColumnTestEntity).setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS).executeRows();
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLException);
            EasyQuerySQLException ex1 = ((EasyQuerySQLException) ex.getCause());
            Assert.assertEquals("UPDATE `query_large_column_test` SET `content` = ? WHERE `id` = ?",ex1.getSQL());
            Assert.assertEquals("java.sql.BatchUpdateException: Table 'easy-query-test.query_large_column_test' doesn't exist",ex1.getMessage());
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
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof EasyQueryException);
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            Assert.assertTrue(ex.getCause() instanceof SQLException);
            Assert.assertTrue(ex.getCause() instanceof EasyQuerySQLException);
            EasyQuerySQLException ex1 = ((EasyQuerySQLException) ex.getCause());
            Assert.assertEquals("DELETE FROM `query_large_column_test` WHERE `id` = ?",ex1.getSQL());
            Assert.assertEquals("java.sql.BatchUpdateException: Table 'easy-query-test.query_large_column_test' doesn't exist",ex1.getMessage());
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

}
