package org.d2u.base.shared.data;

import org.d2u.base.shared.model.Unit;
import org.d2u.base.shared.util.TableDML;

import java.util.List;

public interface UnitScopeMapper extends TableDML<Unit.Scope> {
    List<Unit.Scope> findAllUnitScope();
}
