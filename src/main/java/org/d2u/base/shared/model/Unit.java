package org.d2u.base.shared.model;

import org.d2u.base.shared.data.UnitMapper;
import org.d2u.base.shared.util.Cacheable;


public record Unit(String name,Scope scope) implements HasHelper<UnitMapper>, Cacheable {

    @Override
    public UnitMapper helper() {
        return null;
    }

    @Override
    public String cacheKey(){
        return name()+"-"+scope.name();
    }

    public record Scope(String name) implements Cacheable {
        @Override
        public String cacheKey(){
            return name();
        }
    }
}
