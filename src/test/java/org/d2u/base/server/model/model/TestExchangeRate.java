package org.d2u.base.server.model.model;

import org.d2u.base.shared.model.ExchangeRate;
import org.d2u.base.shared.model.Quantity;
import org.d2u.base.shared.model.Unit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class TestExchangeRate {
    Unit.Scope scope = new Unit.Scope("Length");
    Unit m = new Unit("M",scope);
    Unit cm = new Unit("CM",scope);

    @Test
    public void testConstructor(){
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                ExchangeRate rate = new ExchangeRate(null,100,cm);
            }
        });
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                ExchangeRate rate = new ExchangeRate(m,100,null);
            }
        });
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                ExchangeRate rate = new ExchangeRate(m,0,cm);
            }
        });
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                ExchangeRate rate = new ExchangeRate(m,-1,cm);
            }
        });

    }
    @Test
    public void testConvert(){
        final ExchangeRate rate = new ExchangeRate(m,100,cm);
        Quantity cm500 = rate.convert(new Quantity(5d,m));
        Assertions.assertEquals(500,cm500.value(),"5M convert to CM");
        Assertions.assertEquals(cm,cm500.unit(),"After convert unit");

        Quantity m3dot5 = rate.convert(new Quantity(350d,cm));
        Assertions.assertEquals(3.5,m3dot5.value(),"350CM convert to M");
        Assertions.assertEquals(m,m3dot5.unit(),"After convert unit");

        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Unit miles = new Unit("Miles",scope);
                rate.convert(new Quantity(100d,miles));
            }
        });
    }
}
