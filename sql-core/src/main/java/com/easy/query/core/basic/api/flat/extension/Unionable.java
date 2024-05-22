package com.easy.query.core.basic.api.flat.extension;

import com.easy.query.core.basic.api.flat.MapQueryable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * create time 2024/5/22 23:09
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Unionable {
    default MapQueryable union(MapQueryable mapQueryable){
        return union(Collections.singleton(mapQueryable));
    }
   default MapQueryable union(MapQueryable mapQueryable1,MapQueryable mapQueryable2){
        return union(Arrays.asList(mapQueryable1,mapQueryable2));
   }
    MapQueryable union(Collection<MapQueryable> mapQueryables);
    default MapQueryable unionAll(MapQueryable mapQueryable){
        return unionAll(Collections.singleton(mapQueryable));
    }
   default MapQueryable unionAll(MapQueryable mapQueryable1,MapQueryable mapQueryable2){
        return unionAll(Arrays.asList(mapQueryable1,mapQueryable2));
   }
    MapQueryable unionAll(Collection<MapQueryable> mapQueryables);
}
