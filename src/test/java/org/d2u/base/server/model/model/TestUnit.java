package org.d2u.base.server.model.model;

import org.d2u.base.shared.model.Unit;
import org.d2u.base.shared.data.UnitMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestUnit extends TestBase{
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

    @BeforeEach
    public void initTableDDL(){
        doTransaction(session -> {
            UnitMapper mapper = session.getMapper(UnitMapper.class);
            mapper.truncateTable();
        });
        doTransaction(session -> {
            UnitMapper mapper = session.getMapper(UnitMapper.class);
            mapper.dropTable();
        });
        doTransaction(session -> {
            UnitMapper mapper = session.getMapper(UnitMapper.class);
            mapper.createTable();
        });
        doTransaction(session -> {
            UnitMapper mapper = session.getMapper(UnitMapper.class);
            mapper.initTable();
        });
    }

    @Test
    public void insert(){
        doTransaction(session -> {
            String name="AUD";
            Unit.Scope scope = new Unit.Scope("CUR");
            Unit u = new Unit(name,scope);
            UnitMapper mapper = session.getMapper(UnitMapper.class);
            mapper.insert(u);
            session.commit();
            Unit savedUnit = mapper.findUnitOf(name,scope);
            assertEquals(savedUnit,u,"Retrieve inserted unit");
            //mapper.delete(u);
            //session.commit();
            //savedUnit = mapper.findUnitOf(name,scope);
            //assertNull(savedUnit,"Retrieve deleted unit");
        });
    }

    @Test
    public void findAllUnit(){
        doQuery(session -> {
            UnitMapper helper = session.getMapper(UnitMapper.class);
            List<Unit> units = helper.findAllUnits();
            units.forEach(u -> debug(u.toString()));
            assertEquals(10,units.size(),"Unit count");
        });
    }

    @Test
    public void findUnitsOf(){
        doQuery(session -> {
            UnitMapper helper = session.getMapper(UnitMapper.class);
            List<Unit> units = helper.findUnitsOf(new Unit.Scope("LEN"));
            units.forEach(u -> debug("findUnitsOf(\"Length\") return "+u.toString()));
            assertEquals(3,units.size(),"Unit count");
        });
    }
    @Test
    public void findAllUnitScope(){
        doQuery(session -> {
            UnitMapper helper = session.getMapper(UnitMapper.class);
            List<Unit.Scope> scopes = helper.findAllUnitScope();
            scopes.forEach(scope -> debug(scope.toString()));
            assertEquals(4,scopes.size(),"Unit.Scope count");
        });
    }
}
