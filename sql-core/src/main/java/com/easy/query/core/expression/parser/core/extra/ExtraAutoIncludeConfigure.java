package com.easy.query.core.expression.parser.core.extra;

/**
 * create time 2025/4/16 22:31
 * selectAutoInclude时第二层级开始的额外筛选弥补dsl在class下的表达式不足的问题
 * 仅非table下的dto、vo生效 @NavigateFlat属性无法额外筛选但是@NavigateFlat对象支持
 *
 * @author xuejiaming
 */
public interface ExtraAutoIncludeConfigure {
    ExtraWhere getExtraWhere();
    ExtraSelect getExtraSelect();
    ExtraConfigure getExtraConfigure();
    boolean isIgnoreNavigateConfigure();
}
