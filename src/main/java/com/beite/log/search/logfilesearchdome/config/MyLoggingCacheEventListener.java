package com.beite.log.search.logfilesearchdome.config;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyLoggingCacheEventListener implements CacheEventListener<Object, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyLoggingCacheEventListener.class);

    @Override
    public void onEvent(CacheEvent<? extends Object, ? extends Object> cacheEvent) {
        LOGGER.info("Cache event: {}, Key: {}",
                cacheEvent.getType(), cacheEvent.getKey());
    }
}