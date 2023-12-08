package com.easy.query.core.enums;

/**
 * create time 2023/12/8 08:32
 * 插件生成注解配置枚举
 *
 * @author xuejiaming
 */
public enum FileGenerateEnum {
    /**
     * 创建如果存在就跳过
     */
    GENERATE_IF_EXISTS_SKIP,
    /**
     * 仅编译当前文件创建覆盖
     */
    GENERATE_CURRENT_COMPILE_OVERRIDE,
    /**
     * 编译全局时也会覆盖
     */
    GENERATE_COMPILE_ALWAYS_OVERRIDE;
}
