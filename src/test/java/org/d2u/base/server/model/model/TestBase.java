package org.d2u.base.server.model.model;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.d2u.base.server.data.SqlRunnable;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <P>Base class for testing which dependent on MyBatis framework.
 * <P>Usually your testing code looks like following:</P>
 * <pre>
 *     public class TestCase{
 *         public void testDBAction(){
 *             SqlSession session = null;
 *             try{
 *                 session = newSession();
 *                 //
 *                 // Do testing here
 *                 //
 *             }catch(IOException e) {
 *                 // ...
 *             }finally{
 *                 if(session != null)
 *                     session.close();
 *             }
 *         }
 *     }
 * </pre>
 * <P>Now those "try ... catch" boilerplate was refactored to base class(TestCase.doTest(SQLRunner runner) in this case)</P>
 * <pre>
 *     public class TestCase extends TestBase{
 *         public void testDBAction(){
 *             doQueryTest(session -> {
 *                 //
 *                 // Do testing here
 *                 //
 *             });
 *         }
 *     }
 * </pre>
 * @see SqlRunnable
 * @author CaptainRed
 * @since 1.0 2023
 * @version 1.0
 **/
public class TestBase {
    protected SqlSession newSession() throws IOException{
        String resource = "mybatis-config.xml";
        SqlSessionFactory factory = newSQLSessionFactory(resource);
        return factory.openSession();
    }

    protected void doQuery(SqlRunnable sqlRun){
        SqlSession session = null;
        try{
            session = newSession();
            sqlRun.run(session);
        }catch(IOException e) {
            assertTrue(false,"Got IOException "+e.getMessage());
        }finally{
            if(session != null)
                session.close();
        }
    }

    protected void doTransaction(SqlRunnable sqlRun){
        SqlSession session = null;
        try{
            session = newSession();
            sqlRun.run(session);
        }catch(IOException e) {
            assertTrue(false,"Got IOException "+e.getMessage());
        }finally{
            if(session != null) {
                session.commit();
                session.close();
            }
        }
    }

    public static SqlSessionFactory newSQLSessionFactory(String configFile) throws IOException {
        InputStream inputStream = Resources.getResourceAsStream(configFile);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    public void debug(String message){
        System.out.println("[DEBUG]:"+message);
    }
}