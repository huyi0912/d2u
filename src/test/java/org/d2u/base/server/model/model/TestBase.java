package org.d2u.base.server.model.model;

import com.google.inject.Guice;
import com.google.inject.Injector;
import jakarta.inject.Inject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.d2u.base.server.data.SqlRunnable;
import org.d2u.base.shared.data.*;
import org.d2u.base.shared.model.Unit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;
import org.mybatis.guice.MyBatisModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <P>Base class for testing which dependent on MyBatis framework.
 *
 *
 * @author CaptainRed
 * @version 1.0
 * @see SqlRunnable
 * @since 1.0 2023
 **/
public class TestBase {
    static SqlSessionFactory factory = null;
    //String schema = "store2";
    static Logger logger = null;

    static{
        logger = LoggerFactory.getLogger(TestBase.class);
    }
    static String schema1 = "store1";
    static String schema2 = "store2";
    @BeforeAll
    public static void init() throws Exception {

        logger.trace("init newSQLSessionFactory...");
        String resource = "mybatis-config.xml";
        factory = newSQLSessionFactory(resource);
        dropTableDDL();
        initTableDDL();
    }

    protected static SqlSession newSession(String schema) throws Exception {

        SqlSession session = factory.openSession();
        session.getConnection().setSchema(schema);
        logger.debug("newSession of schema("+schema+")");
        return session;
    }

    protected static void doQuery(String schema,SqlRunnable sqlRun) {
        assertDoesNotThrow(() -> {
            SqlSession session = null;
            try {
                logger.trace("doQuery begin get new session of schema("+schema+")");
                session = newSession(schema);
                sqlRun.run(session);
            } finally {
                if (session != null)
                    session.close();
                logger.trace("doQuery end close session of schema("+schema+")");
            }
        });
    }

    protected static void doTransaction(String schema,SqlRunnable sqlRun){
        assertDoesNotThrow(() -> {
            SqlSession session = null;
            try {
                logger.trace("doTransaction begin get new session of schema("+schema+")");
                session = newSession(schema);
                sqlRun.run(session);
            } finally {
                if (session != null) {
                    session.commit();
                    session.close();
                }
                logger.trace("doTransaction end commit and close session of schema("+schema+")");
            }
        });
    }

    public static SqlSessionFactory newSQLSessionFactory(String configFile) throws IOException {
        logger.debug("newSQLSessionFactory of config(("+configFile+")");
        InputStream inputStream = Resources.getResourceAsStream(configFile);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    public static void dropTableDDL() throws Exception {
        String[] schemas = new String[]{schema1, schema2};

        for (String schema : schemas) {
            doTransaction(schema, session -> {
                ProductDDLMapper productMapper = session.getMapper(ProductDDLMapper.class);
                ProductHistoricalPriceDDLMapper productHistoricalPriceMapper = session.getMapper(ProductHistoricalPriceDDLMapper.class);
                UnitDDLMapper unitMapper = session.getMapper(UnitDDLMapper.class);
                I18NDDLMapper i18nMapper = session.getMapper(I18NDDLMapper.class);

                productMapper.dropTable(schema);
                productHistoricalPriceMapper.dropTable(schema);
                unitMapper.dropTable(schema);
                i18nMapper.dropTable(schema);
            });

        }
    }
    public static void initTableDDL() throws Exception{
        String[] schemas = new String[]{schema1,schema2};

        for(String schema:schemas) {
            doTransaction(schema, session -> {
                ProductDDLMapper productMapper = session.getMapper(ProductDDLMapper.class);
                ProductHistoricalPriceDDLMapper productHistoricalPriceMapper = session.getMapper(ProductHistoricalPriceDDLMapper.class);
                UnitDDLMapper unitMapper = session.getMapper(UnitDDLMapper.class);
                I18NDDLMapper i18nMapper = session.getMapper(I18NDDLMapper.class);

                unitMapper.createTable(schema);
                productMapper.createTable(schema);
                productHistoricalPriceMapper.createTable(schema);
                i18nMapper.createTable(schema);

                unitMapper.initData(schema);
                i18nMapper.initData(schema);
            });

        }

        doTransaction(schema2, session -> {
            UnitMapper m = session.getMapper(UnitMapper.class);
            m.delete(new Unit("Taiwan Dollar",new Unit.Scope("CUR")));
        });
    }
}