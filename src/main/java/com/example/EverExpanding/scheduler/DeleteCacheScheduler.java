package com.example.EverExpanding.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DeleteCacheScheduler {

    private final CacheRemover cacheRemover;

    public DeleteCacheScheduler(CacheRemover cacheRemover) {
        this.cacheRemover = cacheRemover;
    }
    
//    @Scheduled(cron = "0 * * * * *")
    @Scheduled(fixedDelay = 500)
    public void removeCache() {
        this.cacheRemover.evictAllCacheValue();
    }
}
