package org.d2u.base.shared.model;

import org.d2u.base.shared.data.UnitHelper;

import java.util.Objects;


public record Unit(String name,Scope scope) implements HasHelper<UnitHelper>{

    @Override
    public UnitHelper helper() {
        return null;
    }

    public record Scope(String name){}
}
