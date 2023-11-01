package org.d2u.base.shared.util;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TableDML<T> {
    void insert(@Param("target") T t);
    void update(@Param("target") T t);
    void delete(@Param("target") T t);
    List<T> findAll();
}
