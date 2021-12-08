package com.example.EverExpanding.scheduler;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
public class CacheRemover {

    @CacheEvict(value = "cachedPosts", allEntries = true)
    public void evictAllCacheValue(){}
}
