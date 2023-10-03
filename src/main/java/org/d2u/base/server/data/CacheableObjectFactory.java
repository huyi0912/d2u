package org.d2u.base.server.data;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.d2u.base.server.util.Cache;
import org.d2u.base.shared.util.Cacheable;

import java.util.List;

public class CacheableObjectFactory extends DefaultObjectFactory {
    static Cache cache = new Cache();
    @Override
    public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        if(Cacheable.class.isAssignableFrom(type)) {
            // need to create instance of object so can get the cacheKey
            Cacheable obj = (Cacheable)super.create(type,constructorArgTypes,constructorArgs);
            Cacheable cached = cache.get(type,obj.cacheKey());
            if(cached == null) {
                cache.put(cached = obj);
                //System.out.println("CacheableObjectFactory create "+type.getSimpleName()+" : " + cached + " and place in cache");
            }else{
                //System.out.println("CacheableObjectFactory reuse "+type.getSimpleName()+ " : "+ cached +" from cache");
            }
            return (T)cached;
        }else return super.create(type,constructorArgTypes,constructorArgs);
    }

}
