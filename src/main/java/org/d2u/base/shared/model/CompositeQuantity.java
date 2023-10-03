package org.d2u.base.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//@Serial
public class CompositeQuantity implements Serializable {
    //private static final long serialVersionUID = 20231002112900L; /* synthetic field */
    //public static final int MAX_QUANTITY_COUNT = 5;

    protected List<Quantity> quantities;

    public CompositeQuantity(){}

    public CompositeQuantity(Quantity qty){
        addQuantity(qty);
    }

    public CompositeQuantity(Quantity qty1,Quantity qty2){
        addQuantity(qty1);
        addQuantity(qty2);
    }

    public void addQuantity(Quantity qty)
    {
        if(isUnitAlreadyExist(qty))
            throw new IllegalArgumentException("Quantity of unit "+ qty.unit()+" already exist!");
        else quantities().add(qty);
    }

    private boolean isUnitAlreadyExist(Quantity qty) {
        for(Quantity q:quantities()){
            if(q.unit().equals(qty.unit()))
                return true;
        }
        return false;
    }

    public boolean removeQuantity(Quantity qty)
    {
        return quantities().remove(qty);
    }

    public void insertQuantity(int index, Quantity qty)
    {
        if(isUnitAlreadyExist(qty))
            throw new IllegalArgumentException("Quantity of unit "+ qty.unit()+" already exist!");
        else quantities().add(index, qty);
    }

    public int getQuantityCount()
    {
        return quantities().size();
    }

    public List<Quantity> getAllQuantites()
    {
        return Collections.unmodifiableList(quantities());
    }

    private List<Quantity> quantities(){
        if(quantities == null)
            quantities = new ArrayList<Quantity>();
        return quantities;
    }
    public Quantity getQuantity(Unit unit)
    {
        for(Quantity qty:quantities)
        {
            if(qty != null && qty.unit().equals(unit))
                return qty;
        }
        return null;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for(Quantity qty:quantities())
        {
            if(qty != null && qty.value() != 0.0D)
            {
                if(!first)
                    sb.append(" ");
                else
                    first = false;
                sb.append(qty.toString());
            }
        } ;
        if(sb.isEmpty() && !quantities().isEmpty())
            sb.append(quantities().get(quantities().size() - 1));
        return sb.toString();
    }
}
