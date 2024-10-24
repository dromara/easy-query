package com.easy.query.test;

import com.easy.query.core.util.EasyStringUtil;

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
//        String annotation = "@Navigate(value = RelationTypeEnum.ManyToOne, selfProperty = \"classId\", targetProperty = \"id\")";
//        String replacement = "@Navigate()";
//
//        String regex = "@Navigate\\(.*?\\)";
//        String s = annotation.replaceAll(regex, replacement);
//        System.out.println(s);
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(annotation);
//
//        if (matcher.find()) {
//            String replacedAnnotation = matcher.replaceAll(replacement);
//            System.out.println(replacedAnnotation);
//        }
        String a="  /**\n" +
                "     * {@link com.zjbd.exam.domain.entity.temporary.TpUserCertEntity }\n" +
                "     */";
        String referenceClassName0 = getReferenceClassName0("123", a.replaceAll("@link","@see"));
        System.out.println(referenceClassName0);

        String aa="\n" +
                " aaa" +
                "\n";
        System.out.println(aa);
        System.out.println(replaceBlank(aa));
    }
    private static final String SEE_CLASS_REGEX = "@see\\s+([\\w\\.]+)";
    private static final String LINK_CLASS_REGEX = "\\{@link\\s+([\\w\\.]+)\\}";

    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
    private static String getReferenceClassName0(String className, String docText) {
//        docText = docText.replaceAll("\n", "");
        if (EasyStringUtil.isNotBlank(docText)) {
            Pattern pattern = Pattern.compile(SEE_CLASS_REGEX);

            // 创建匹配器
            Matcher matcher = pattern.matcher(docText);
            if (matcher.find()) {
                String result = matcher.group(1);
                if (EasyStringUtil.isNotBlank(result)) {
                    return result;
                } else {
                    String resultLink = matcher.group(2);
                    if (EasyStringUtil.isNotBlank(resultLink)) {
                        return resultLink;
                    }
                }
            }
        }
        return className;
    }
}
