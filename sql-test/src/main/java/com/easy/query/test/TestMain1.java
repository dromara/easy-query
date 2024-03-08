package com.easy.query.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create time 2024/3/8 14:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class TestMain1 {
    public static void main(String[] args) {
        String annotation = "@Navigate(value = RelationTypeEnum.ManyToOne, selfProperty = \"classId\", targetProperty = \"id\")";
        String replacement = "@Navigate()";

        String regex = "@Navigate\\(.*?\\)";
        String s = annotation.replaceAll(regex, replacement);
        System.out.println(s);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(annotation);

        if (matcher.find()) {
            String replacedAnnotation = matcher.replaceAll(replacement);
            System.out.println(replacedAnnotation);
        }
    }
}
