package org.motechproject.carereporting.cache;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

@Aspect
public class ClearCacheAspect {

    private static final String[] INDICATOR_CACHES = {"indicatorValues", "map", "chart", "trend"};

    @Autowired
    private CacheManager cacheManager;

    @Before("execution(* org.motechproject.carereporting.service.IndicatorService.calculate*(..))")
    public void clearIndicatorCache() {
        for (String cacheName: INDICATOR_CACHES) {
            cacheManager.getCache(cacheName).clear();
        }
    }
}
