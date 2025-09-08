package com.easy.query.core.enums;

/**
 * create time 2023/3/25 09:01
 * 文件说明
 *
 * @author xuejiaming
 */
public enum EasyBehaviorEnum {
    /**
     * 使用逻辑删除
     */
    LOGIC_DELETE(1),
    /**
     * 使用拦截器
     */
    USE_INTERCEPTOR(1 << 1),
    /**
     * 使用数据追踪查询
     */
    USE_TRACKING(1 << 2),
    /**
     * 查询大列
     */
//    QUERY_LARGE_COLUMN(1<<3),
    ON_DUPLICATE_KEY_IGNORE(1 << 4),
    ON_DUPLICATE_KEY_UPDATE(1 << 5),
    EXECUTE_BATCH(1 << 6),
    EXECUTE_NO_BATCH(1 << 7),
    /**
     * 更新删除无版本号报错
     */
    @Deprecated
    NO_VERSION_ERROR(1 << 8),
    /**
     * 更新删除忽略版本号
     */
    IGNORE_VERSION(1 << 9),
    /**
     * 本次执行是否使用jdbc监听器
     */
    JDBC_LISTEN(1 << 10),
    /**
     * 所有的子查询转GroupJoin
     */
    ALL_SUB_QUERY_GROUP_JOIN(1 << 11),
    /**
     * 分片使用UNION_ALL
     */
    SHARDING_UNION_ALL(1 << 12),
    /**
     * 子查询转GroupJoin不自动合并
     */
    GROUP_JOIN_NOT_ALLOW_AUTO_MERGE(1 << 13),
    /**
     * 智能断言尽可能where穿透到子查询内部
     */
    SMART_PREDICATE(1<<14);

    private final int code;

    EasyBehaviorEnum(int code) {

        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
