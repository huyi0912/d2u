package org.d2u.base.shared.util;

import org.apache.ibatis.annotations.Param;

public interface TableDDL {
    void createTable(@Param("schema") String schema);
    void dropTable(@Param("schema") String schema);
    //void truncateTable();

}
