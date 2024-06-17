package com.easy.query.api.lambda;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main
{
    public static void main(String[] args)
    {
        int i = 0;
        String input = "{}";
        // 正则表达式匹配 {}
        Pattern pattern = Pattern.compile("\\{}");
        Matcher matcher = pattern.matcher(input);
        // 使用StringBuilder来构建新的字符串
        StringBuilder sb = new StringBuilder(input.length());
        // 遍历输入字符串
        int prevEnd = 0; // 上一个匹配的结束位置
        while (matcher.find())
        {
            // 将从上一个匹配结束到当前匹配开始的部分添加到StringBuilder中
            sb.append(input.substring(prevEnd, matcher.start()));
            // 替换 {} 为 {index}
            sb.append("{").append(i++).append("}");
            // 更新上一个匹配的结束位置为当前匹配的结束位置
            prevEnd = matcher.end();
        }
        // 如果输入字符串的末尾有未匹配的部分，将其添加到StringBuilder中
        if (prevEnd < input.length())
        {
            sb.append(input.substring(prevEnd));
        }

        System.out.println(sb.toString());
    }
}
