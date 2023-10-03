package org.d2u.base.shared.model;

import org.apache.ibatis.annotations.Param;
import org.d2u.base.shared.data.UnitMapper;
import org.d2u.base.shared.util.Cacheable;


public class Unit implements Cacheable {//},HasHelper<UnitMapper> {

    private String name;
    private Scope scope;


    public Unit(String name){
         this(name,null);
    }

    public Unit(@Param("name") String name, @Param("scope") Scope scope) {
        setName(name);
        setScope(scope);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        if (name == null)
            throw new IllegalArgumentException("name cannot be null");
        this.name = name;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    @Override
    public String cacheKey() {
        return getName() + "-" + scope.name();
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (Unit.class.isAssignableFrom(obj.getClass())) {
            Unit u = (Unit) obj;
            if (u.name.equals(name)) {
                if (u.scope == scope) return true;
                return u.scope != null && scope != null && u.scope.equals(scope);
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
    }

    public record Scope(String name) implements Cacheable {
        @Override
        public String cacheKey() {
            return name();
        }
    }
}
