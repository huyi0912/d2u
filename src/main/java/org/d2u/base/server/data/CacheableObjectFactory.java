package org.d2u.base.server.data;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.d2u.base.server.util.TenantCache;
import org.d2u.base.server.util.TenantInfo;
import org.d2u.base.shared.util.Cacheable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * <p>A customized objectFactory used in MyBatis to reuse immutable object instance beyond session scope.</p>
 *
 *  @author CaptainRed
 *  @version 1.0
 */
public class CacheableObjectFactory extends DefaultObjectFactory {
    @Serial
    private final static long serialVersionUID = 20231011122300L;
    static Logger logger = null;
    static{
        logger = LoggerFactory.getLogger(CacheableObjectFactory.class);
    }
    static Cache<String,TenantCache> caches = CacheBuilder.newBuilder().build();
    @Override
    public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        if(Cacheable.class.isAssignableFrom(type)) {
            try {
                TenantCache tenantCache = caches.get(TenantInfo.getSchema(), new Callable<TenantCache>() {
                    @Override
                    public TenantCache call() throws Exception {
                        return new TenantCache();
                    }
                });
                // need to create instance of object so can get the cacheKey
                Cacheable obj = (Cacheable) super.create(type, constructorArgTypes, constructorArgs);
                Cacheable cached = tenantCache.get(type, obj.cacheKey());
                if (cached == null) {
                    tenantCache.put(cached = obj);
                    logger.debug("CacheableObjectFactory create "+type.getSimpleName()+" : " + cached + " and place in cache of tenant "+TenantInfo.getSchema());
                } else {
                    logger.debug("CacheableObjectFactory reuse "+type.getSimpleName()+ " : "+ cached +" from cache of tenant "+TenantInfo.getSchema());
                }
                return (T) cached;
            }catch (ExecutionException e){
                logger.error("CacheableObjectFactory failed to get cache for tenant "+TenantInfo.getSchema(),e);
                return super.create(type, constructorArgTypes, constructorArgs);
            }
        }else{
            return super.create(type,constructorArgTypes,constructorArgs);
        }
    }

}
