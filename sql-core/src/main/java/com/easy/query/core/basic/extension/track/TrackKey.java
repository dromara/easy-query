package com.easy.query.core.basic.extension.track;

import java.util.List;

/**
 * @FileName: TrackKey.java
 * @Description: 文件说明
 * create time 2023/3/19 16:15
 * @author xuejiaming
 */
public interface TrackKey {
    List<String> getProperties();
    String getKeyValue();
}
