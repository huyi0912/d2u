package org.d2u.base.shared.data;

import org.d2u.base.shared.model.Helper;
import org.d2u.base.shared.model.Unit;

import java.util.List;

public interface UnitHelper extends Helper {
    List<Unit> findAllUnits();
    List<Unit> findUnitsOf(Unit.Scope scope);
    Unit findUnitOf(String unitName,Unit.Scope scope);
    List<Unit.Scope> findAllUnitScope();
}
