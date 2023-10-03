package org.d2u.base.server.data;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.d2u.base.shared.model.Unit;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @deprecated
 */
public class UnitScopeTypeHandler implements TypeHandler<Unit.Scope> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Unit.Scope parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i,parameter.name());
    }

    @Override
    public Unit.Scope getResult(ResultSet rs, String columnName) throws SQLException {
        return new Unit.Scope(rs.getString(columnName));
    }

    @Override
    public Unit.Scope getResult(ResultSet rs, int columnIndex) throws SQLException {
        return new Unit.Scope(rs.getString(columnIndex));
    }

    @Override

    public Unit.Scope getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return new Unit.Scope(cs.getString(columnIndex));
    }
}
