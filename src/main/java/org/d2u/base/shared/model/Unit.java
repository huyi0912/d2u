package org.d2u.base.shared.model;

import org.d2u.base.shared.util.Cacheable;
import org.d2u.base.shared.util.DBField;

public record Unit(@DBField(primaryKey = true,notNull = true) String name,
                   @DBField(primaryKey = true,notNull = true) Scope scope) implements Cacheable,HasOID<Unit.OID>{//},ConditionExample<Unit>,HasOID<Unit.OID>{

    @Override
    public String cacheKey() {
        return name() + "-" + (scope()==null?"":scope().name());
    }

    @Override
    public OID getOID() {
        if(name()==null) return null;
        return new OID(name());
    }

    public record OID(String name){}

    public record Scope(String name) implements Cacheable {
        @Override
        public String cacheKey() {
            return name();
        }
    }
}
