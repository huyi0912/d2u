package org.d2u.base.shared.data;

import org.apache.ibatis.annotations.Param;

public interface TableDML<T> {
    void insert(@Param("target") T t);
    void update(@Param("target")T t);
    void delete(@Param("target")T t);
}
