package com.easy.query.test;

import com.easy.query.BaseTest;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.SqlRangeEnum;
import com.easy.query.core.expression.sql.internal.EasyBehavior;
import com.easy.query.core.util.AesUtil;
import com.easy.query.core.util.Base64Util;
import com.easy.query.core.util.StringUtil;
import com.easy.query.encryption.DefaultAesEasyEncryptionStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @FileName: GenericTest.java
 * @Description: 文件说明
 * @Date: 2023/3/17 22:22
 * @author xuejiaming
 */
public class GenericTest extends BaseTest {

    @Test
    public void SqlRangeTest1(){
        boolean openFirst1 = SqlRangeEnum.openFirst(SqlRangeEnum.Open);
        Assert.assertTrue(openFirst1);
        boolean openFirst2 = SqlRangeEnum.openFirst(SqlRangeEnum.Closed);
        Assert.assertFalse(openFirst2);
        boolean openFirst3 = SqlRangeEnum.openFirst(SqlRangeEnum.closedOpen);
        Assert.assertFalse(openFirst3);
        boolean openFirst4 = SqlRangeEnum.openFirst(SqlRangeEnum.openClosed);
        Assert.assertTrue(openFirst4);
        boolean openEnd1 = SqlRangeEnum.openEnd(SqlRangeEnum.Open);
        Assert.assertTrue(openEnd1);
        boolean openEnd2 = SqlRangeEnum.openEnd(SqlRangeEnum.Closed);
        Assert.assertFalse(openEnd2);
        boolean openEnd3 = SqlRangeEnum.openEnd(SqlRangeEnum.closedOpen);
        Assert.assertTrue(openEnd3);
        boolean openEnd4 = SqlRangeEnum.openEnd(SqlRangeEnum.openClosed);
        Assert.assertFalse(openEnd4);
    }
    @Test
    public void base64Test(){
        String uuid = UUID.randomUUID().toString();
        byte[] bytes = uuid.getBytes(StandardCharsets.UTF_8);
        byte[] encode = Base64Util.encode(bytes);
        byte[] decode = Base64Util.decode(encode);
        Assert.assertArrayEquals(bytes,decode);
        Assert.assertEquals(uuid,new String(decode,StandardCharsets.UTF_8));
    }
    private final String key="abcdef1234567890";//16位的秘钥
    private final String iv="A-16-Byte-String";//16位的iv

    @Test
    public void aesTest2(){
        String uuid = UUID.randomUUID().toString();
        String encryptToString = AesUtil.encrypt(uuid,key , iv,StandardCharsets.UTF_8);
        String decryptToString =AesUtil.decrypt(encryptToString, key, iv,StandardCharsets.UTF_8);

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
        Assert.assertTrue(StringUtil.startWith(encryptValue.toString(),encryptValue1.toString()));
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
        Assert.assertTrue(easyBehavior.isDefaultBehavior());
    }
}
