package org.d2u.base.shared.model;

/**
 * <P>Record quantity of specific unit.</P>
 * <P>Can be used to describe physical definition like one meter is 100 cm,int this case quantity is 100 cm,unit is meter.</P>
 * <P>Or packaging of stuff like one box have 12 pieces of chocolate,in this case quantity is 12 pieces,unit is box</P>
 * <P>Or currency exchange rate like one us dollar worth 150 japanese yen,in this case quantity is 150 yen,unit is dollar</P>
 * @param quantity
 * @param unit
 * @deprecated use ExchangeRate instead
 */
public record QuantityOfUnit(Quantity quantity,Unit unit) {
}
