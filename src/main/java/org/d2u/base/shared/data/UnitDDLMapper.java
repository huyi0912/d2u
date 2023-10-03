package org.d2u.base.shared.data;

import org.apache.ibatis.annotations.Param;
import org.d2u.base.shared.util.TableDDL;

public interface UnitDDLMapper extends TableDDL {
    void initData(@Param("schema") String schema);
}
