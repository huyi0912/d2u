package org.d2u.base.shared.model;

import org.apache.ibatis.annotations.Param;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class HistoricalQuantity<T> implements HasTimePeriod{
    T owner;
    Quantity quantity;
    OffsetDateTime startTime;
    OffsetDateTime endTime;

    public final static OffsetDateTime END_OF_TIME = OffsetDateTime.of(999999999,12,31,23,59,59,999999999, ZoneOffset.MIN);

    public HistoricalQuantity(@Param("quantity") Quantity quantity,@Param("startTime")  OffsetDateTime startTime) {
        this(null,quantity,startTime,END_OF_TIME);
    }

    public HistoricalQuantity(@Param("quantity") Quantity quantity,@Param("startTime")  OffsetDateTime startTime,@Param("endTime")  OffsetDateTime endTime) {
        this(null,quantity,startTime,endTime);
    }

    public HistoricalQuantity(@Param("owner") T owner,@Param("quantity") Quantity quantity,@Param("startTime")  OffsetDateTime startTime) {
        this(owner,quantity,startTime,END_OF_TIME);
    }

    public HistoricalQuantity(@Param("owner") T owner,@Param("quantity") Quantity quantity,@Param("startTime")  OffsetDateTime startTime,@Param("endTime")  OffsetDateTime endTime){
        if(quantity == null)
            throw new IllegalArgumentException("quantity cannot be null");
        if(startTime == null)
            throw new IllegalArgumentException("startTime cannot be null");
        this.owner = owner;
        this.quantity = quantity;
        this.startTime = startTime;
        setEndTime(endTime);
    }

    public T getOwner(){
        return owner;
    }

    public void setOwner(T owner){
        this.owner = owner;
    }

    public Quantity getQuantity(){
        return quantity;
    }

    public void setQuantity(Quantity quantity){
        if(quantity == null) throw new IllegalArgumentException("quantity cannot be null");
        this.quantity = quantity;
    }
    public OffsetDateTime getStartTime() {
        return startTime;
    }


    public OffsetDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(OffsetDateTime endTime){
        if(endTime == null)
            throw new IllegalArgumentException("endTime cannot be null");
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null) return false;
        if(obj instanceof HistoricalQuantity){
            HistoricalQuantity other = (HistoricalQuantity) obj;
            return other.startTime.equals(startTime) && other.endTime.equals(endTime) && other.quantity.equals(quantity);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return quantity.hashCode()+startTime.hashCode()+endTime.hashCode();
    }

    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "HistoricalQuantity[" +
                quantity +
                "(" +
                startTime.format(formatter) +
                " ~ " +
                endTime.format(formatter) +
                ")]";
    }
}
