package org.d2u.base.server.util;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import org.d2u.base.shared.util.Cacheable;

/**
 * <p>Provide thread safed cache for Cacheable object.</p>
 * <p>Originally designed for a customized objectFactory used in MyBatis to achieve singleton for record type instance.</p>
 * <p>Each instance of Cache object is independent of each other.</p>
 *
 * @see org.d2u.base.shared.util.Cacheable
 * @see org.d2u.base.server.data.CacheableObjectFactory
 *
 * @author CaptainRed
 * @since 0.1 2023
 * @version 0.1
 */
public class Cache {
    Map<Class,Map<String,Cacheable>> caches =  Collections.synchronizedMap(new WeakHashMap<Class,Map<String,Cacheable>>());

    public Cacheable get(Class type,String key){
        Map<String, Cacheable> classCache = getClassCache(type);
        return classCache.get(key);
    }

    public <T extends Cacheable> void put(T type){
        Map<String, Cacheable> classCache = getClassCache(type.getClass());
        classCache.put(type.cacheKey(),type);
    }

    public void clear(){
        caches.clear();
    }

    public void clear(Class type){
        getClassCache(type).clear();;
    }

    private Map<String, Cacheable> getClassCache(Class type) {
        Map<String,Cacheable> classCache = caches.get(type.getClass());
        if(classCache == null)
            caches.put(type.getClass(),classCache = Collections.synchronizedMap(new WeakHashMap<String,Cacheable>()));
        return classCache;
    }
}
