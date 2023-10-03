package org.d2u.base.server.data;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.d2u.base.shared.model.Unit;

import java.util.List;
import java.util.WeakHashMap;

/**
 * @deprecated
 */
public class SingletonEnabledObjectFactory extends DefaultObjectFactory {
    WeakHashMap<String,Unit.Scope> caches = new WeakHashMap<String,Unit.Scope>();
    @Override
    public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        if(Unit.Scope.class.isAssignableFrom(type)) {
            String name = (String) constructorArgs.get(0);
            Unit.Scope scope = caches.get(name);
            if(scope == null){
                caches.put(name,scope = new Unit.Scope(name));
                System.out.println("SingletonEnabledObjectFactory create Unit.Scope of name=" + name );
            }else
                System.out.println("SingletonEnabledObjectFactory reuse Unit.Scope of name=" + name);
            return (T)scope;
        }else if(Unit.class.isAssignableFrom(type)) {
            String name = (String) constructorArgs.get(0);
            Unit.Scope scope = (Unit.Scope) constructorArgs.get(1);
            System.out.println("SingletonEnabledObjectFactory create unit of name=" + name + ",scope=" + scope);
            return (T)new Unit(name, scope);
        }else return super.create(type,constructorArgTypes,constructorArgs);
    }

}
