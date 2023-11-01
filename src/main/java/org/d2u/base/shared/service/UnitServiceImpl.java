package org.d2u.base.shared.service;

import jakarta.inject.Inject;
import org.apache.ibatis.session.SqlSessionFactory;
import org.d2u.base.server.util.TenantInfo;
import org.d2u.base.shared.data.UnitMapper;
import org.d2u.base.shared.model.Unit;

import java.sql.SQLException;
import java.util.List;

public class UnitServiceImpl extends BaseSQLService implements UnitService{
    @Inject
    UnitMapper mapper;

    public UnitServiceImpl(SqlSessionFactory factory){
        super(factory);
    }
    @Override
    public Unit.OID insert(Unit target) {
        try {
            doTransaction(TenantInfo.getSchema(), session -> {
                mapper.insert(target);
            });
            return target.getOID();
        }catch(SQLException e){
            return null;
        }
    }

    @Override
    public boolean update(Unit.OID oid, Unit target) {
        return false;
    }

    @Override
    public boolean deleteBy(Unit.OID oid) {
        return false;
    }

    @Override
    public int deleteByExample(Unit example) {
        return 0;
    }

    @Override
    public Unit findBy(Unit.OID oid) {
        return null;
    }

    @Override
    public List<Unit> findByExample(Unit example) {
        return null;
    }
}
