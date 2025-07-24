package com.jingdiansuifeng.subject.domain.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * 缓存工具类
 */
@Component
public class CacheUtil<K,V> {

    private Cache<String,String> localCache =
            CacheBuilder.newBuilder()
                    .maximumSize(5000)
                    .expireAfterWrite(10, TimeUnit.SECONDS)
                    .build();


    public List<V> getResult(String cachekey, Class<V> clazz,
                             Function<String,List<V>> function){

        List<V> resultList = new ArrayList<>();
        String content = localCache.getIfPresent(cachekey);
        if (StringUtils.isNotBlank(content)) {
            resultList = JSON.parseArray(content, clazz);
        }else{
            resultList = function.apply(cachekey);
            if(!CollectionUtils.isEmpty(resultList)){
                localCache.put(cachekey,JSON.toJSONString(resultList));
            }
        }
        return resultList;
    }

    public Map<K,V> getMapResult(String cachekey, TypeReference<Map<K, V>> map,
                                 Function<String,Map<K,V>> function){

        Map<K,V> resultMap = new HashMap<>();
        String content = localCache.getIfPresent(cachekey);
        if (StringUtils.isNotBlank(content)) {
            resultMap = JSON.parseObject(content, map);
        }else{
            resultMap = function.apply(cachekey);
            if(!CollectionUtils.isEmpty(resultMap)){
                localCache.put(cachekey,JSON.toJSONString(resultMap));
            }
        }
        return resultMap;
    }
}
