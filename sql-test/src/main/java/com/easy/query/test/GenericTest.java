package com.easy.query.test;

import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.SQLRangeEnum;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;
import com.easy.query.core.util.EasyAesUtil;
import com.easy.query.core.util.EasyBase64Util;
import com.easy.query.core.util.EasyBitwiseUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.test.encryption.DefaultAesEasyEncryptionStrategy;
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
public class GenericTest {

    @Test
    public void SqlRangeTest1(){
        boolean openFirst1 = SQLRangeEnum.openFirst(SQLRangeEnum.Open);
        Assert.assertTrue(openFirst1);
        boolean openFirst2 = SQLRangeEnum.openFirst(SQLRangeEnum.Closed);
        Assert.assertFalse(openFirst2);
        boolean openFirst3 = SQLRangeEnum.openFirst(SQLRangeEnum.closedOpen);
        Assert.assertFalse(openFirst3);
        boolean openFirst4 = SQLRangeEnum.openFirst(SQLRangeEnum.openClosed);
        Assert.assertTrue(openFirst4);
        boolean openEnd1 = SQLRangeEnum.openEnd(SQLRangeEnum.Open);
        Assert.assertTrue(openEnd1);
        boolean openEnd2 = SQLRangeEnum.openEnd(SQLRangeEnum.Closed);
        Assert.assertFalse(openEnd2);
        boolean openEnd3 = SQLRangeEnum.openEnd(SQLRangeEnum.closedOpen);
        Assert.assertTrue(openEnd3);
        boolean openEnd4 = SQLRangeEnum.openEnd(SQLRangeEnum.openClosed);
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

}
