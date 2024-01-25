package com.easy.query.cache.core.impl;//package com.easy.query.cache.core.impl;
//
//
//import com.easy.query.cache.core.Pair;
//import com.easy.query.cache.core.RedisManager;
//
//import java.time.Duration;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.function.Function;
//
///**
// * create time 2023/5/16 10:34
// * 文件说明
// *
// * @author xuejiaming
// */
//@Component
//public class DefaultRedisManager implements RedisManager {
//    private final RedissonClient redissonClient;
//
//    public DefaultRedisManager(RedissonClient redissonClient) {
//        this.redissonClient = redissonClient;
//    }
//
//    @Override
//    public <T> List<Pair<String, T>> cache(Class<T> clazz,String entityKey,boolean isIndex, Set<String> ids, long timeoutMillisSeconds, long nullValueTimeoutMillisSeconds, Function<Collection<String>, List<Pair<String, T>>> getDataFunc) {
//        if (ids == null || ids.size() == 0) {
//            return new ArrayList<>();
//        }
//        if (timeoutMillisSeconds <= 0) {
//            return getDataFunc.apply(ids);
//        }
//        HashMap<String, Pair<String,T>> ret = new HashMap<>(ids.size());
//        Set<String> notExistsIds = new HashSet<>();
//        for (String id : ids) {
//            String realEntityKey = getRealEntityKey(id, isIndex, entityKey);
//            RBucket<String> entityJsonBucket = redissonClient.getBucket(realEntityKey);
//            if(entityJsonBucket.isExists()){
//                String json = entityJsonBucket.get();
//                if(StringUtils.isNotBlank(json)){
//                    T entity = JsonUtil.jsonStr2Object(json, clazz);
//                    ret.put(id,new Pair<>(id,entity));
//                    continue;
//                }
//            }
//            notExistsIds.add(id);
//        }
//        if(notExistsIds.size()>0){
//
//            long currentTimeMillis = System.currentTimeMillis();
//            Collection<Pair<String,T>> datas = getDataFunc.apply(notExistsIds);
//
//            for (Pair<String,T> data : datas) {
//                String id = data.getObject1();
//                if(!notExistsIds.contains(id)){
//                    throw new RuntimeException("使用 cache 请确认 getDataFunc 返回值 EntityCacheOutput[] 中的 cacheId 值: "+id+" 存在于 输入参数: "+ StringUtils.join(notExistsIds,','));
//                }
//                ret.put(id,new Pair<>(data.getObject1(),data.getObject2()));
//                String json = JsonUtil.object2JsonStr(data.getObject2());
//                String realEntityKey = getRealEntityKey(id, isIndex, entityKey);
//                RBucket<String> entityJsonBucket = redissonClient.getBucket(realEntityKey);
//                entityJsonBucket.set(json, Duration.ofMillis(currentTimeMillis + timeoutMillisSeconds));
//                notExistsIds.remove(id);
//            }
//            for (String cacheId : notExistsIds) {
//
//                String realEntityKey = getRealEntityKey(cacheId, isIndex, entityKey);
//                RBucket<String> entityJsonBucket = redissonClient.getBucket(realEntityKey);
//                entityJsonBucket.set(null, Duration.ofMillis(currentTimeMillis + nullValueTimeoutMillisSeconds));
//            }
//        }
//        return new ArrayList<>(ret.values());
//
//    }
//    private String getRealEntityKey(String id,boolean isIndex,String entityKey){
//        if(isIndex){
//            return entityKey+":INDEX:"+id;
//        }
//        return entityKey+":"+id;
//    }
////
////    @SneakyThrows
////    @Override
////    public <T> List<Pair<String,T>> cache(Class<T> clazz, Function<KeyParams,String> entityKey, Set<String> ids, long timeoutMillisSeconds, long nullValueTimeoutMillisSeconds, Function<Collection<String>, List<Pair<String,T>>> getDataFunc) {
////       if(ids==null||ids.size()==0){
////          return new LinkedList<>();
////       }
////        if (timeoutMillisSeconds <= 0) {
////            return getDataFunc.apply(ids);
////        }
////        HashMap<String, Pair<String,T>> ret = new HashMap<>(ids.size());
////        RMap<String, String> fieldsMap = redissonClient.<String, String>getMap(hKey);
////        Map<String, String> cacheValues = fieldsMap.getAll(ids);
////        Map<String, Integer> fieldsMGet = new LinkedHashMap<>();
////        long currentTimeMillis = System.currentTimeMillis();
////
////        for (String field : ids) {
////            String value = cacheValues.getOrDefault(field, null);
////            if(value!=null){
////                try{
////                    JavaType javaType = JsonUtil.jsonMapper.getTypeFactory().constructParametricType(EntityCacheInput.class, clazz);
////                    EntityCacheInput<T> entityCacheInput = JsonUtil.jsonStr2Object(value, javaType);
////                    if(entityCacheInput.getTimeoutMillisSeconds()>currentTimeMillis){
////                        ret.put(field,new Pair<>(field,entityCacheInput.getData()));
////                        continue;
////                    }
////                }catch (Exception e){
////                    fieldsMap.remove(field);
////                    throw e;
////                }
////            }
////            fieldsMGet.put(field,0);
////        }
////        if(!fieldsMGet.isEmpty()){
////            Set<String> notInRedisKeys = fieldsMGet.keySet();
////            Collection<Pair<String,T>> datas = getDataFunc.apply(notInRedisKeys);
////            Map<String, String> mset = new LinkedHashMap<>(notInRedisKeys.size());
////            for (Pair<String,T> data : datas) {
////                if(!fieldsMGet.containsKey(data.getObject1())){
////                    throw new RuntimeException("使用 cache 请确认 getDataFunc 返回值 EntityCacheOutput[] 中的 cacheId 值: "+data.getObject1()+" 存在于 输入参数: "+ StringUtils.join(notInRedisKeys,','));
////                }
////                ret.put(data.getObject1(),new Pair<>(data.getObject1(),data.getObject2()));
////                EntityCacheInput<T> entityCacheInput = new EntityCacheInput<>(currentTimeMillis + timeoutMillisSeconds, data.getObject2());
////                mset.put(data.getObject1(),JsonUtil.object2JsonStr(entityCacheInput));
////                fieldsMGet.remove(data.getObject1());
////            }
////            for (String cacheId : fieldsMGet.keySet()) {
////                ret.put(cacheId,new Pair<>(cacheId,null));
////                EntityCacheInput<T> entityCacheInput = new EntityCacheInput<>(currentTimeMillis + nullValueTimeoutMillisSeconds, null);
////                mset.put(cacheId,JsonUtil.object2JsonStr(entityCacheInput));
////            }
////            if(!mset.isEmpty()){
////                fieldsMap.putAll(mset);
////            }
////        }
////        return new ArrayList<>(ret.values());
////
////    }
//}
