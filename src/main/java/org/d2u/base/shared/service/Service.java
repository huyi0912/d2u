package org.d2u.base.shared.service;

import org.d2u.base.shared.model.HasOID;

import java.util.List;

public interface Service<T extends HasOID<U>,U>{
    U insert(T target);

    boolean update(U oid, T target);

    boolean deleteBy(U oid);

    int deleteByExample(T example);

    T findBy(U oid);

    List<T> findByExample(T example);
}
