package org.d2u.base.server.data;

import org.apache.ibatis.session.SqlSession;

/**
 * <P>Provide a Runnable like to facilitate MyBatis framework. with SqlSession parameter to simplify the testing code
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
 *}</pre>
 * <P>Now those "try ... catch" boilerplate was refactored to base class(TestCase.doTest(SQLRunner runner) in this case)</P>
 * <pre>
 *     public class TestCase extends TestBase{
 *         public void testDBAction(){
 *             doTest(session -> {
 *                 //
 *                 // Do testing here
 *                 //
 *             });
 *         }
 *     }
 * </pre>
 * @see TestBase
 * @author CaptainRed
 * @since 1.0 2023
 * @version 1.0
 *
 */
public interface SqlRunnable {
    /**
     *
     * @param session SqlSession of MyBatis framework for SQL database operation
     */
    void run(SqlSession session);
}
