package org.d2u.base.shared.model;

import org.apache.ibatis.annotations.Param;

import java.time.ZonedDateTime;

public class Product{
    String name;
    HistoricalQuantity<Product> currentPrice;

    /**
     * No argument constructor is required for MyBatis resultMap which does not using &lt;constructor>...&lt;/constructor>
     */
    private Product(){}
    public Product(@Param("name") String name){
        this.name = name;
    }

//    public Product(String name, Quantity price){
//        this.name = name;
//        currentPrice = new HistoricalQuantity<Product>(this,price, ZonedDateTime.now());
//    }
    public Product(@Param("name") String name,@Param("currentPrice") HistoricalQuantity<Product> currentPrice){
        this.name = name;
        setCurrentPrice(currentPrice);
    }

    public String getName(){
        return name;
    }

    public void setCurrentPrice(HistoricalQuantity<Product> currentPrice){
        if(currentPrice == null) throw new IllegalArgumentException("currentPrice cannot be null");
        this.currentPrice = currentPrice;
        currentPrice.setOwner(this);
    }

    public HistoricalQuantity<Product> getCurrentPrice(){
        return currentPrice;
    }

   // public Quantity getCurrentPrice(){
   //     return currentPrice.getQuantity();
   // }

    @Override
    public boolean equals(Object obj){
        if(obj == this) return true;
        if(obj == null) return false;
        if(Product.class.isAssignableFrom(obj.getClass())){
            Product p = (Product)obj;
            return p.getName().equals(getName());
        }
        return false;
    }

    @Override
    public int hashCode(){
        return getName().hashCode();
    }

    @Override
    public String toString(){
        if(currentPrice == null) return name;
        else return name+" of "+currentPrice;
    }
}
