package org.d2u.base.server.model.model;

import org.d2u.base.shared.data.UnitDDLMapper;
import org.d2u.base.shared.data.UnitScopeMapper;
import org.d2u.base.shared.model.Unit;
import org.d2u.base.shared.data.UnitMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestUnit extends TestBase{

    static Logger logger = null;
    static{
        logger = LoggerFactory.getLogger(TestUnit.class);
    }

    //@Test
    public void insertUnit(){
        assertEquals(1,0,"Inserted Units count");
    }

    //@Test
    public void insertDuplicateUnits(){
        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                System.err.println("What happened here");
            }
        },"Insert duplicated unit should throw exceptions");
    }



    @Test
    public void insert(){
        doTransaction(schema1, session -> {
            String name="AUD";
            Unit.Scope scope = new Unit.Scope("CUR");
            Unit u = new Unit(name,scope);
            UnitMapper mapper = session.getMapper(UnitMapper.class);
            mapper.insert(u);
            session.commit();
            Unit savedUnit = mapper.findUnitOf(name,scope);
            assertEquals(savedUnit,u,"Retrieve inserted unit");
            mapper.delete(u);
            session.commit();
            savedUnit = mapper.findUnitOf(name,scope);
            assertNull(savedUnit,"Retrieve deleted unit");
        });
    }

    @Test
    public void delete(){

        doTransaction(schema2,session->{
            String name="US Dollar";
            Unit.Scope scope = new Unit.Scope("CUR");
            Unit u = new Unit(name,scope);
            UnitMapper helper = session.getMapper(UnitMapper.class);
            helper.delete(u);
            Unit deletedUnit = helper.findUnitOf(name,scope);
            assertNull(deletedUnit,"Deleted "+u+" of "+schema2);
        });
    }

    @Test
    public void findAllUnit(){
        doQuery(schema1, session -> {

            UnitMapper helper = session.getMapper(UnitMapper.class);
            List<Unit> units = helper.findAll();
            units.forEach(u -> logger.debug("findAllUnit() from "+schema1+" return => "+u.toString()));
            assertEquals(10,units.size(),"Unit count");
        });
    }

    /**
     * @see  org.d2u.base.server.data.SqlRunnable
     */
    @Test
    public void findAllUnitUsingSameSessionButDifferentSchema(){

        doQuery(schema1,session -> {
            UnitMapper helper = session.getMapper(UnitMapper.class);
            List<Unit> units = helper.findAll();
            assertEquals(10,units.size(),"findAllUnit() from "+schema1+" return ");
            try{
                session.getConnection().setSchema(schema2);
                helper = session.getMapper(UnitMapper.class);
                units = helper.findAll();
                assertEquals(10,units.size(),"findAllUnit() by change schema to "+schema2+" will return previous cached result(previous schema) in session");
            }catch(Exception e){
                assertFalse(true,"Something wrong");
                e.printStackTrace();
            }
        });
        // need new session for different schema
        doQuery(schema2,session -> {
            UnitMapper helper = session.getMapper(UnitMapper.class);
            List<Unit> units = helper.findAll();
            assertEquals(9,units.size(),"findAllUnit() from "+schema2+" using new session works and return ");
        });
    }

    @Test
    public void findUnitsOf(){
        doQuery(schema1, session -> {
            UnitMapper helper = session.getMapper(UnitMapper.class);
            List<Unit> units = helper.findUnitsOf(new Unit.Scope("LEN"));
            units.forEach(u -> logger.debug("findUnitsOf(\"Length\") return => "+u.toString()));
            assertEquals(3,units.size(),"Unit count");
        });
    }
    @Test
    public void findAllUnitScope(){
        doQuery(schema1, session -> {
            UnitScopeMapper helper = session.getMapper(UnitScopeMapper.class);
            List<Unit.Scope> scopes = helper.findAllUnitScope();
            scopes.forEach(scope -> logger.debug("findAllUnitScope() return => "+scope.toString()));
            assertEquals(4,scopes.size(),"Unit.Scope count");
        });
    }
}
