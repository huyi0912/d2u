package org.d2u.base.shared.data;

import org.apache.ibatis.annotations.Param;
import org.d2u.base.shared.model.Helper;
import org.d2u.base.shared.model.Unit;

import java.util.List;

public interface UnitMapper extends Helper, TableDDL,TableDML<Unit> {
    List<Unit> findAllUnits();
    List<Unit> findUnitsOf(Unit.Scope scope);
    Unit findUnitOf(@Param("name") String unitName,@Param("scope") Unit.Scope scope);
    List<Unit.Scope> findAllUnitScope();
}
