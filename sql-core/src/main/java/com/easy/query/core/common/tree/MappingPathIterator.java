//package com.easy.query.core.common.tree;
//
//import com.easy.query.core.metadata.NavigateFlatMetadata;
//import com.easy.query.core.metadata.NavigateFlatProperty;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * create time 2024/5/14 10:46
// * 文件说明
// *
// * @author xuejiaming
// */
//public class MappingPathIterator {
//    private final List<NavigateFlatMetadata> navigateFlatMetadataList;
//    private int index;
//    private final int maxLength;
//
//    public MappingPathIterator(List<NavigateFlatMetadata> navigateFlatMetadataList) {
//        this.navigateFlatMetadataList = navigateFlatMetadataList;
//        this.maxLength=navigateFlatMetadataList.stream().map(x -> x.getMappingPath().length).max(Integer::compareTo).orElse(0);
//        this.index = 0;
//    }
//
//    public boolean hasNext() {
//        return index < maxLength;
//    }
//
//    public List<NavigateFlatProperty> next() {
//
//        List<NavigateFlatProperty> flatProperties = navigateFlatMetadataList.stream().filter(o -> index < o.getMappingPath().length)
//                .map(o -> new NavigateFlatProperty(o, o.getMappingPath()[index], (o.getMappingPath().length - 1) - index)).collect(Collectors.toList());
//        index++;
//        return flatProperties;
//    }
//
//    public List<NavigateFlatProperty> nextRest() {
//        return navigateFlatMetadataList.stream().filter(o -> index < o.getMappingPath().length)
//                .map(o -> new NavigateFlatProperty(o, o.getMappingPath()[index], (o.getMappingPath().length - 1) - index)).collect(Collectors.toList());
//    }
//
//    public boolean hasOneNext() {
//        return index == navigateFlatMetadataList.size() - 1;
//    }
//
//    public List<NavigateFlatMetadata> getNavigateFlatMetadataList() {
//        return navigateFlatMetadataList;
//    }
//}
