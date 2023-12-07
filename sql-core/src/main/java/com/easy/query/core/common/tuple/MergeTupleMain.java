package com.easy.query.core.common.tuple;

/**
 * create time 2023/12/7 11:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class MergeTupleMain {
    public static void main(String[] args) {
        generateSelectTuples();
    }

    private static void generateSelectTuples() {
        for (int i = 3; i <= 10; i++) {
            generateSelectTuple(i);
        }
    }

    private static void generateSelectTuple(int size) {
        StringBuilder code = new StringBuilder();
        code.append("public class MergeSelectTuple").append(size).append("<");
        for (int i = 1; i <= size; i++) {
            code.append("T").append(i);
            if (i < size) {
                code.append(",");
            }
        }
        code.append(", TR> {\n");

        for (int i = 1; i <= size; i++) {
            code.append("    public final T").append(i).append(" t").append(i).append(";\n");
        }
        code.append("    public final TR r;\n");

        code.append("\n");
        code.append("    public MergeSelectTuple").append(size).append("(");

        for (int i = 1; i <= size; i++) {
            code.append("T").append(i).append(" t").append(i);
            if (i < size) {
                code.append(", ");
            }
        }
        code.append(", TR r) {\n");

        for (int i = 1; i <= size; i++) {
            code.append("        this.t").append(i).append(" = t").append(i).append(";\n");
        }
        code.append("        this.r = r;\n");

        code.append("    }\n");
        code.append("}\n");

        System.out.println(code.toString());
    }
}
