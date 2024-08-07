package com.easy.query.test;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create time 2024/6/10 14:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class Test {
    public static void main(String[] args) {



        String comment = "/**\n" +
                " * @see SchoolStudent\n" +
                " */";
        // 定义正则表达式模式
        String regex = "@see\\s+(\\w+)";

        // 编译正则表达式
        Pattern pattern = Pattern.compile(regex);

        // 创建匹配器
        Matcher matcher = pattern.matcher(comment);

        // 查找匹配的子字符串
        if (matcher.find()) {
            // 提取第一个捕获组，即SchoolStudent
            String result = matcher.group(1);
            System.out.println(result);
        } else {
            System.out.println("没有找到匹配的注解");
        }
    }
}
