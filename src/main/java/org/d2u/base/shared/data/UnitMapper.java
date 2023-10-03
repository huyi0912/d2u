package org.d2u.base.shared.data;

import org.apache.ibatis.annotations.Param;
import org.d2u.base.shared.model.Helper;
import org.d2u.base.shared.model.Unit;
import org.d2u.base.shared.util.TableDML;

import java.time.ZonedDateTime;
import java.util.List;

public interface UnitMapper extends Helper, TableDML<Unit> {
    //List<Unit> findAll();
    List<Unit> findUnitsOf(Unit.Scope scope);
    Unit findUnitOf(@Param("name") String unitName,@Param("scope") Unit.Scope scope);

}
