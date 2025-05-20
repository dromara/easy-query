package com.easy.query.processor.generate;

import com.easy.query.processor.helper.AptConstant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create time 2025/5/20 11:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class ClassProxyGenerator {


    // 预处理后的模板结构
    public final List<String> proxyTemplateStaticParts;
    public final List<String> proxyTemplatePlaceholders;

    public ClassProxyGenerator(String template) {

        List<String> staticParts = new ArrayList<>();
        List<String> placeholders = new ArrayList<>();
        Pattern pattern = Pattern.compile("@\\{(.+?)\\}");
        Matcher matcher = pattern.matcher(template);
        int lastIndex = 0;

        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            staticParts.add(template.substring(lastIndex, start));
            placeholders.add(matcher.group(1));
            lastIndex = end;
        }
        staticParts.add(template.substring(lastIndex)); // 最后一段静态内容

        proxyTemplateStaticParts = Collections.unmodifiableList(staticParts);
        proxyTemplatePlaceholders = Collections.unmodifiableList(placeholders);
    }


    public String generate(Map<String, String> replacements) {

// 使用预处理的模板结构进行高效拼接
        StringBuilder sb = new StringBuilder();
        List<String> staticParts = proxyTemplateStaticParts;
        List<String> placeholders = proxyTemplatePlaceholders;

        for (int i = 0; i < placeholders.size(); i++) {
            sb.append(staticParts.get(i)); // 添加静态内容
            String placeholder = placeholders.get(i);
            String replacement = replacements.getOrDefault(placeholder, "");
            if (replacement == null) {
                throw new IllegalStateException("Missing replacement for placeholder: @" + placeholder);
            }
            sb.append(replacement); // 替换动态内容
        }
        sb.append(staticParts.get(staticParts.size() - 1)); // 添加最后一段静态内容
        return sb.toString();
    }

}
