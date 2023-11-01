package org.d2u.base.server.model.model;

import org.d2u.base.shared.data.I18NLocaleMapper;
import org.d2u.base.shared.data.I18NMapper;
import org.d2u.base.shared.data.I18NMessageMapper;
import org.d2u.base.shared.util.I18N;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.vm.VM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestI18N extends TestBase{

    static Logger logger;
    static{
        logger = LoggerFactory.getLogger(TestI18N.class);
    }

    @Test
    public void insertDeleteFindMessage(){
        doTransaction(schema1,session->{
            I18NMessageMapper mapper = session.getMapper(I18NMessageMapper.class);
            mapper.insert(new I18N.Message("SAILOR"));
            I18N.Message sailor = mapper.findMessage("SAILOR");
            assertNotNull(sailor,"Queried inserted SAILOR message");
            mapper.delete(sailor);
            sailor = mapper.findMessage("SAILOR");
            assertNull(sailor,"Queried deleted SAILOR message");
        });
    }

    @Test
    public void insertDeleteFindI18N(){
        doTransaction(schema1,session->{
            String messageID = "SAILOR";
            I18N.Locale enLocale = new I18N.Locale("en",null);
            I18N.Locale twLocale = new I18N.Locale("zh","TW");
            I18N.Locale cnLocale = new I18N.Locale("zh","CN");
            I18NMapper i18nMapper = session.getMapper(I18NMapper.class);
            I18NMessageMapper mapper = session.getMapper(I18NMessageMapper.class);
            mapper.insert(new I18N.Message(messageID));
            I18N.Message sailor = mapper.findMessage(messageID);
            assertNotNull(sailor,"Queried inserted "+messageID+" message");
            I18N localizedSailor1 = new I18N(sailor,enLocale,"Sailor {0}");
            I18N localizedSailor2 = new I18N(sailor,twLocale,"船員 {0}");
            I18N localizedSailor3 = new I18N(sailor,cnLocale,"船员 {0}");
            i18nMapper.insert(localizedSailor1);
            i18nMapper.insert(localizedSailor2);
            i18nMapper.insert(localizedSailor3);
            I18N enSailor = i18nMapper.findI18N(messageID,enLocale);
            assertNotNull(enSailor,"Query inserted "+messageID);
            assertEquals("Sailor Red",enSailor.localized("Red"),"Localized EN SAILOR");
            I18N twSailor = i18nMapper.findI18N(messageID,twLocale);
            assertNotNull(twSailor,"Query inserted "+messageID);
            assertEquals("船員 Red",twSailor.localized("Red"),"Localized TW SAILOR");
            I18N cnSailor = i18nMapper.findI18N(messageID,cnLocale);
            assertNotNull(cnSailor,"Query inserted "+messageID);
            assertEquals("船员 Red",cnSailor.localized("Red"),"Localized CN SAILOR");

            mapper.delete(sailor);
            I18N queryDeletedSailor = i18nMapper.findI18N(messageID,enLocale);
            assertNull(queryDeletedSailor,"Queried deleted EN SAILOR");
            queryDeletedSailor = i18nMapper.findI18N(messageID,twLocale);
            assertNull(queryDeletedSailor,"Queried deleted TW SAILOR");
            queryDeletedSailor = i18nMapper.findI18N(messageID,cnLocale);
            assertNull(queryDeletedSailor,"Queried deleted CN SAILOR");
        });
    }

    @Test
    public void findAll(){
        doQuery(schema1, session -> {
            I18NLocaleMapper helper = session.getMapper(I18NLocaleMapper.class);
            List<I18N.Locale> items = helper.findAll();
            items.forEach(u -> logger.debug("findAll() from "+schema1+" return => "+u.toString()));
            assertEquals(4,items.size(),"I18N.Locale count");
        });
        doQuery(schema1, session -> {
            I18NMessageMapper helper = session.getMapper(I18NMessageMapper.class);
            List<I18N.Message> items = helper.findAll();
            items.forEach(u -> logger.debug("findAll() from "+schema1+" return => "+u.toString()));
            assertEquals(11,items.size(),"I18N.Message count");
        });

        doQuery(schema1, session -> {
            I18NMapper helper = session.getMapper(I18NMapper.class);
            List<I18N> items = helper.findAll();
            items.forEach(u -> logger.debug("findAll() from "+schema1+" return => "+u.toString()));
            assertEquals(33,items.size(),"I18N count");
        });
    }

    @Test
    public void findAllI18N(){

        doQuery(schema1, session -> {
            I18NMapper helper = session.getMapper(I18NMapper.class);
            List<I18N> items = helper.findAllI18N(new I18N.Locale("en"));
            items.forEach(u -> logger.debug("findAllI18N() of en locale from "+schema1+" return => "+u.toString()));
            assertEquals(11,items.size(),"I18N count");

            items = helper.findAllI18N(null);
            items.forEach(u -> logger.debug("findAllI18N() of null locale from "+schema1+" return => "+u.toString()));
            assertEquals(33,items.size(),"I18N count");
        });

        doTransaction(schema1,session->{
            I18NMessageMapper mapper = session.getMapper(I18NMessageMapper.class);

            String messageID = "SAILOR";
            I18N.Locale enLocale = new I18N.Locale("en");
            I18N.Locale usLocale = new I18N.Locale("en","US");

            I18NMapper i18nMapper = session.getMapper(I18NMapper.class);

            mapper.insert(new I18N.Message(messageID));
            I18N.Message sailorMsg = mapper.findMessage(messageID);
            assertNotNull(sailorMsg,"Queried inserted "+messageID+" message");
            I18N localizedSailor1 = new I18N(sailorMsg,enLocale,"Skipper");
            i18nMapper.insert(localizedSailor1);
            I18N enSailor = i18nMapper.findI18N(messageID,enLocale);
            assertNotNull(enSailor,"findI18n of "+messageID+" of "+enLocale);

            I18N usSailor = i18nMapper.findI18N(messageID,usLocale);

            assertNotNull(usSailor,"findI18n of "+messageID+" of "+usLocale+" fall back to en locale");
            assertEquals(usSailor,enSailor,"When SAILOR not defined in en/US should fall back to en locale");

            List<I18N> i18nList = i18nMapper.findAllI18N(usLocale);
            assertEquals(12,i18nList.size(),"findAllI18N of "+usLocale);
            for(I18N item:i18nList){
                assertEquals(enLocale,item.locale(),"findAllI18N should include message not defined in "+usLocale+" but defined in "+enLocale);
            }
            mapper.delete(sailorMsg);
        });
    }

    @Test
    public void testRecordWithNullParameter(){
        I18N.Locale locale = new I18N.Locale("en",null);
        assertEquals("",locale.country(),"country");
    }

    @Test
    public void testI18NCacheable() throws Exception{
        final I18N.Locale enLocale = new I18N.Locale("en");
        List<I18N> schema1List = new ArrayList<I18N>();
        List<I18N> schema2List = new ArrayList<I18N>();
        doQuery(schema1, session -> {
            I18NMapper helper = session.getMapper(I18NMapper.class);
            List<I18N> items1 = helper.findAllI18N(enLocale);
            assertEquals(11,items1.size(),"I18N count");
            for(int i=0,size= items1.size();i<size;i++){
                I18N item1 = items1.get(i);
                I18N item2 = helper.findI18N(item1.message().id(),enLocale);
                //logger.debug("-------"+i+"==>"+ VM.current().addressOf(items1)+"  compare to  "+ VM.current().addressOf(item2));
                assertSame(item2,item1,"query I18N should return same object");
                schema1List.add(item1);
            }
        });

        doQuery(schema2, session -> {
            I18NMapper helper = session.getMapper(I18NMapper.class);
            List<I18N> items1 = helper.findAllI18N(enLocale);
            assertEquals(11,items1.size(),"I18N count");
            for(int i=0,size= items1.size();i<size;i++){
                schema2List.add(items1.get(i));
            }
        });
        for(int i=0,size=schema1List.size();i<size;i++){
            I18N item1 = schema1List.get(i);
            I18N item2 = schema2List.get(i);
            assertEquals(item1,item2,"Though different schema but I18N might still the same");
            assertNotSame(item1,item2,"Different schema should not have same equal I18N");
        }
    }
}
