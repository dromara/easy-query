package com.easy.query.core.basic.plugin.track;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * create time 2023/5/31 23:01
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityTrackProperty {
    private final Map<String, TrackDiffEntry> diffProperties = new HashMap<>();
    private final Set<String> sameProperties = new HashSet<>();

    public Map<String, TrackDiffEntry> getDiffProperties() {
        return diffProperties;
    }

    public Set<String> getSameProperties() {
        return sameProperties;
    }
}
