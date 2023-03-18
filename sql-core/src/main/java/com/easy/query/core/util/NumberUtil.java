package com.easy.query.core.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @FileName: NumberUtil.java
 * @Description: 文件说明
 * @Date: 2023/3/18 13:53
 * @Created by xuejiaming
 */
public class NumberUtil {

    /**
     * 将Number转化为期望类型
     * @param number
     * @param targetClass
     * @return
     */
    public static Number convertNumberToTargetClass(Number number, Class<?> targetClass) {
        if(number == null){
            throw new IllegalArgumentException("Number不能为空");
        }
        if(targetClass == null){
            throw new IllegalArgumentException("TargetClass不能为空");
        }

        if (targetClass.isInstance(number)) {
            return  number;
        }
        else if (Byte.class == targetClass||byte.class==targetClass) {
            long value = number.longValue();
            if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) {
                throw new IllegalArgumentException(number.getClass().getName()+"无法转化为目标对象"+targetClass.getName());
            }
            return new Byte(number.byteValue());
        }
        else if (Short.class == targetClass||short.class == targetClass) {
            long value = number.longValue();
            if (value < Short.MIN_VALUE || value > Short.MAX_VALUE) {
                throw new IllegalArgumentException(number.getClass().getName()+"无法转化为目标对象"+targetClass.getName());
            }
            return new Short(number.shortValue());
        }
        else if (Integer.class == targetClass||int.class==targetClass) {
            long value = number.longValue();
            if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
                throw new IllegalArgumentException(number.getClass().getName()+"无法转化为目标对象"+targetClass.getName());
            }
            return new Integer(number.intValue());
        }
        else if (Long.class == targetClass||long.class == targetClass) {
            BigInteger bigInt = null;
            if (number instanceof BigInteger) {
                bigInt = (BigInteger) number;
            }
            else if (number instanceof BigDecimal) {
                bigInt = ((BigDecimal) number).toBigInteger();
            }
            // Effectively analogous to JDK 8's BigInteger.longValueExact()
            if (bigInt != null && (bigInt.compareTo(BigInteger.valueOf(Long.MIN_VALUE)) < 0 || bigInt.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0)) {
                throw new IllegalArgumentException(number.getClass().getName()+"无法转化为目标对象"+targetClass.getName());
            }
            return new Long(number.longValue());
        }
        else if (BigInteger.class == targetClass) {
            if (number instanceof BigDecimal) {
                // do not lose precision - use BigDecimal's own conversion
                return  ((BigDecimal) number).toBigInteger();
            }
            else {
                // original value is not a Big* number - use standard long conversion
                return  BigInteger.valueOf(number.longValue());
            }
        }
        else if (Float.class == targetClass||float.class == targetClass) {
            return new Float(number.floatValue());
        }
        else if (Double.class == targetClass||double.class == targetClass) {
            return  new Double(number.doubleValue());
        }
        else if (BigDecimal.class == targetClass) {
            // always use BigDecimal(String) here to avoid unpredictability of BigDecimal(double)
            // (see BigDecimal javadoc for details)
            return new BigDecimal(number.toString());
        }
        else {
            throw new IllegalArgumentException(number.getClass().getName()+"无法转化为目标对象"+targetClass.getName());
        }
    }
    /**
     * 将String转化为期望类型
     * @param text
     * @param targetClass
     * @return
     */
    public static <T extends Number> T parseNumber(String text, Class<T> targetClass) {
        if(text == null){
            throw new IllegalArgumentException("转化值不能为空");
        }

        if(targetClass == null){
            throw new IllegalArgumentException("期望类型不能为空");
        }

        String trimmed = StringUtil.trimAllWhitespace(text);

        if (targetClass.equals(Byte.class)) {
            return (T) (isHexNumber(trimmed) ? Byte.decode(trimmed) : Byte.valueOf(trimmed));
        }
        else if (targetClass.equals(Short.class)) {
            return (T) (isHexNumber(trimmed) ? Short.decode(trimmed) : Short.valueOf(trimmed));
        }
        else if (targetClass.equals(Integer.class)) {
            return (T) (isHexNumber(trimmed) ? Integer.decode(trimmed) : Integer.valueOf(trimmed));
        }
        else if (targetClass.equals(Long.class)) {
            return (T) (isHexNumber(trimmed) ? Long.decode(trimmed) : Long.valueOf(trimmed));
        }
        else if (targetClass.equals(BigInteger.class)) {
            return (T) (isHexNumber(trimmed) ? decodeBigInteger(trimmed) : new BigInteger(trimmed));
        }
        else if (targetClass.equals(Float.class)) {
            return (T) Float.valueOf(trimmed);
        }
        else if (targetClass.equals(Double.class)) {
            return (T) Double.valueOf(trimmed);
        }
        else if (targetClass.equals(BigDecimal.class) || targetClass.equals(Number.class)) {
            return (T) new BigDecimal(trimmed);
        }
        else {
            throw new IllegalArgumentException("无法将["+text+"]转化为期望类型:["+targetClass.getName()+"]");
        }
    }

    /**
     * Signopt 0x HexDigits
     Signopt 0X HexDigits
     Signopt # HexDigits
     是否为十六进制
     * @param value
     * @return
     */
    private static boolean isHexNumber(String value) {
        int index = (value.startsWith("-") ? 1 : 0);
        return (value.startsWith("0x", index) || value.startsWith("0X", index) || value.startsWith("#", index));
    }


    /**
     * 解码BigInteger
     * @param value
     * @return
     */
    private static BigInteger decodeBigInteger(String value) {
        int radix = 10; //进制
        int index = 0;  //脚标
        boolean negative = false; //负标记

        // 处理减
        if (value.startsWith("-")) {
            negative = true;
            index++;
        }

        // 处理进制：16进制，脚标移动2
        if (value.startsWith("0x", index) || value.startsWith("0X", index)) {
            index += 2;
            radix = 16;
        }
        // 处理进制：16进制，脚标移动1
        else if (value.startsWith("#", index)) {
            index++;
            radix = 16;
        }
        // 处理进制：8进制，脚标移动1
        else if (value.startsWith("0", index) && value.length() > 1 + index) {
            index++;
            radix = 8;
        }

        //返回BigInteger
        BigInteger result = new BigInteger(value.substring(index), radix);
        return (negative ? result.negate() : result);
    }
}
