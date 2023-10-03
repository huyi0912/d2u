package org.d2u.base.server.model.model;

import org.d2u.base.shared.model.Unit;
import org.d2u.base.shared.data.UnitHelper;
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

    @Test
    public void findAllUnit(){
        doQueryTest(session -> {
            UnitHelper helper = session.getMapper(UnitHelper.class);
            List<Unit> units = helper.findAllUnits();
            units.forEach(u -> debug(u.toString()));
            assertEquals(8,units.size(),"Unit count");
        });
    }

    //@Test
    public void findUnitsOf(){
        doQueryTest(session -> {
            UnitHelper helper = session.getMapper(UnitHelper.class);
            List<Unit> units = helper.findUnitsOf(new Unit.Scope("Length"));
            units.forEach(u -> debug("findUnitsOf(\"Length\") return "+u.toString()));
            assertEquals(3,units.size(),"Unit count");
        });
    }
    @Test
    public void findAllUnitScope(){
        doQueryTest(session -> {
            UnitHelper helper = session.getMapper(UnitHelper.class);
            List<Unit.Scope> scopes = helper.findAllUnitScope();
            scopes.forEach(scope -> debug(scope.toString()));
            assertEquals(3,scopes.size(),"Unit.Scope count");
        });
    }
}
