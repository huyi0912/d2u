package org.d2u.base.shared.data;

import org.apache.ibatis.annotations.Param;

public interface TableDDL {
    void createTable();
    void dropTable();
    void truncateTable();
    void initTable();
}
