package org.d2u.base.server.util;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheStats;
import org.d2u.base.shared.util.Cacheable;

/**
 * <p>Provide thread safe cache for Cacheable object in multi tenancy environment.</p>
 * <p>Designed for a customized objectFactory used in MyBatis to reuse immutable object instance beyond session scope.</p>
 * <p>Key to cached value will be modified to include schema get from TenantInfo</p>
 * <p>Under the hood is Guava cache.</p>
 *
 * @see org.d2u.base.shared.util.Cacheable
 * @see org.d2u.base.server.data.CacheableObjectFactory
 *
 * @author CaptainRed
 * @version 1.0
 */
public class TenantCache implements Serializable {
    @Serial
    private final static long serialVersionUID = 20231011122300L;
    public final static int DEFAULT_CONCURRENCY_LEVEL = 10; // max 10 thread to access concurrently
    public final static int DEFAULT_MAXIMUM_SIZE = 1000; // 1000 items to cache
    public final static int DEFAULT_EXPIRE_SECONDS = 60*15; // expire time 15 minutes

    Cache<String,Cacheable> caches;


    public TenantCache(){
        this(DEFAULT_CONCURRENCY_LEVEL,DEFAULT_MAXIMUM_SIZE,DEFAULT_EXPIRE_SECONDS);
    }

    public TenantCache(int concurrencyLevel, int maximumSize,int expireAfterSeconds){
       caches = CacheBuilder.newBuilder().concurrencyLevel(concurrencyLevel).maximumSize(maximumSize).softValues().expireAfterWrite(expireAfterSeconds, TimeUnit.SECONDS).build();
    }

    public Cacheable get(Class objectClass,String key){
        return caches.getIfPresent(tenantKey(objectClass,key));
    }

    public <T extends Cacheable> void put(T object){
        if(object == null)
            throw new IllegalArgumentException("object cannot be null");
        caches.put(tenantKey(object.getClass(),object.cacheKey()),object);
    }

    protected String tenantKey(Class objectClass,String key){
        return TenantInfo.getSchema()+"-"+objectClass.getSimpleName()+"-"+key;
    }

    public void clear(){
        caches.cleanUp();;
    }

    public CacheStats getState(){
        return caches.stats();
    }
}
