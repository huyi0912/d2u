package org.d2u.base.shared.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.d2u.base.server.data.SqlRunnable;
import org.d2u.base.server.util.TenantInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class BaseSQLService {
    SqlSessionFactory factory;
    Logger logger = null;

    public BaseSQLService(SqlSessionFactory factory) {
        if(factory == null) throw new IllegalArgumentException("factory cannot be null");
        this.factory = factory;
        logger = LoggerFactory.getLogger(getClass().getSimpleName());
    }

    protected void doQuery(String schema, SqlRunnable sqlRun) throws SQLException {

        SqlSession session = null;
        try {
            logger.trace("doQuery begin get new session of schema(" + schema + ")");
            session = newSession(schema);
            sqlRun.run(session);
        } finally {
            if (session != null)
                session.close();
            logger.trace("doQuery end close session of schema(" + schema + ")");
        }
    }

    protected void doTransaction(String schema, SqlRunnable sqlRun) throws SQLException {
        SqlSession session = null;
        try {
            logger.trace("doTransaction begin get new session of schema(" + schema + ")");
            session = newSession(schema);
            sqlRun.run(session);
        } finally {
            if (session != null) {
                session.commit();
                session.close();
            }
            logger.trace("doTransaction end commit and close session of schema(" + schema + ")");
        }
    }

    protected  SqlSession newSession(String schema) throws SQLException {
        SqlSession session = factory.openSession();
        session.getConnection().setSchema(schema);
        logger.debug("newSession of schema("+schema+")");
        return session;
    }

    public String getSchema(){
        return TenantInfo.getSchema();
    };
}
