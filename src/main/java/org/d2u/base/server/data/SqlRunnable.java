package org.d2u.base.server.data;

import org.apache.ibatis.session.SqlSession;

/**
 * <P>Provide a Runnable like with SqlSession parameter to facilitate MyBatis framework.
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
 * <P>Now those "try ... catch" boilerplate was refactored to base class(TestCase.doQuery(String schema,SQLRunner runner) in this case)</P>
 * <pre>
 *     public class TestCase extends TestBase{
 *         public void testDBAction(){
 *             doQuery(schema,session -> { // lambda style equivalent of new SqlRunnable(SqlSession session){}
 *                 //
 *                 // Do testing here
 *                 //
 *             });
 *         }
 *     }
 * </pre>
 * <P>You should always create new SqlSession for each SqlRunnable which suppose you are targeting on specific database schema.</P>
 * <P>That's because MyBatis default setting for localCacheScope is SESSION</P>
 * <P>which will return the cached result if same sql applied again without the respect on schema changing.</P>
 * <P>That will break the hack to achieve multi tenancy by change schema on connection get from the SqlSession.</P>
 * <P>Two workaround:</P>
 * <li>1.Change localCacheScope setting(mybatis-config.xml) from SESSION to STATEMENT.So you won't get cache result of different schema on same sql.
 * <pre>
 *     &lt;settings>
 *          ...
 *          &lt;setting name="localCacheScope" value="STATEMENT"/>
 *     &lt;/settings>
 * </pre>
 * </li>
 * <li>2.Get new SqlSession for each SqlRunnable and set schema immediately before any query.
 * <pre>
 *         ...
 *         SqlSession session = factory.openSession();
 *         session.getConnection().setSchema(schema);
 *         ...
 * </pre>
 * </li>
 * <P>Second approach won't scarify the efficiency so is preferred</P>
 *
 *
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
