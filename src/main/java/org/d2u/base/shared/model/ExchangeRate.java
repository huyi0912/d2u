package org.d2u.base.shared.model;

/**
 * <P>Record exchange rate for converting between units.</P>
 * <P>Can be used to describe physical definition like one meter is 100 cm.</P>
 * <P>Or packaging of stuff like one box have 12 pieces of chocolate.</P>
 * <P>Or currency exchange rate like one us dollar worth 150 japanese yen.</P>
 * <P>eg:
 * <pre>
 *     new ExchangeRate(new Unit("meter"),100,new Unit("cm"));
 *     new ExchangeRate(new Unit("box"),12,new Unit("piece"));
 *     new ExchangeRate(new Unit("dollar"),150,new Unit("yen"));
 * </pre>
 * @param source source unit that convert from
 * @param rate exchange rate to convert from source unit to target unit
 * @param target target unit that convert to
 */

public record ExchangeRate(Unit source, float rate, Unit target) {

    public ExchangeRate{
        if(source == null) throw new IllegalArgumentException("source unit cannot be null");
        if(target == null) throw new IllegalArgumentException("target unit cannot be null");
        if(rate <= 0 ) throw new IllegalArgumentException("exchange rate("+rate+") should > 0");
    }

    /**
     * Convert quantity to another unit defined in ExchangeRate.Unit must be either defined source unit or target unit otherwise IllegalArgumentException will throwed!
     * @param qty quantity of unit to be converted
     * @return converted quantity
     */
    public Quantity convert(Quantity qty){
        if(qty.unit().equals(source())){
            return new Quantity(qty.value() * rate,target());
        }else if(qty.unit().equals(target())){
            return new Quantity(qty.value() / rate,source());
        }
        throw new IllegalArgumentException("Pass in parameter Quantity("+qty+") can only with unit of "+source()+" or "+target());
    }
}
