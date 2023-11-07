package com.easy.query.api4kt.util;

import com.easy.query.api4kt.parser.DefaultKtLambdaParser;
import com.easy.query.api4kt.parser.KtLambdaParser;
import kotlin.reflect.KProperty1;

import java.util.Objects;

/**
 * create time 2023/6/3 14:01
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyKtLambdaUtil {
    private static KtLambdaParser lambdaParser;

    static {
        lambdaParser = new DefaultKtLambdaParser();
    }

    private EasyKtLambdaUtil() {
    }


    public static void replaceParser(KtLambdaParser p) {
        Objects.requireNonNull(p, "replaceParser p is null");
        lambdaParser = p;
    }
    public static <T> String getPropertyName(KProperty1<? super T, ?> property) {
        return lambdaParser.getPropertyName(property);
    }
}
