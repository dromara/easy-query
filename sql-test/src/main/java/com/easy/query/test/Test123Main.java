package com.easy.query.test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create time 2024/8/3 21:17
 * 文件说明
 *
 * @author xuejiaming
 */
public class Test123Main {
    public static void main(String[] args) {

        // 输入字符串
        String input = "(String),false(Boolean),[1,2,3](String),";

        // 正则表达式模式，用于匹配值和类型
        String pattern = "(.*?[^,]*)\\(([^)]+?)\\),";

        // 创建Pattern对象
        Pattern r = Pattern.compile(pattern);

        // 创建Matcher对象
        Matcher m = r.matcher(input);

        // 用于存储解析结果
        ArrayList<Map.Entry<String, String>> parameters = new ArrayList<>();

        // 查找所有匹配项
        while (m.find()) {
            String value = m.group(1);
            String type = m.group(2);
            parameters.add(new AbstractMap.SimpleEntry<>(value, type));
        }
        System.out.println(parameters);
    }
}
