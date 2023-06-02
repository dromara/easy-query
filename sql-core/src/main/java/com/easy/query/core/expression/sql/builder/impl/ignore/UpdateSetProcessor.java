package com.easy.query.core.expression.sql.builder.impl.ignore;

import com.easy.query.core.basic.extension.track.TrackDiffEntry;

/**
 * create time 2023/5/31 21:41
 * 文件说明
 *
 * @author xuejiaming
 */
public interface UpdateSetProcessor {
    boolean shouldRemove(String property);

    TrackDiffEntry trackValue(String property);
}
