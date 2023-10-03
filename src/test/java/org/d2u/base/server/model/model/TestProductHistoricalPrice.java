package org.d2u.base.server.model.model;

import org.d2u.base.shared.data.ProductDDLMapper;
import org.d2u.base.shared.data.ProductHistoricalPriceDDLMapper;
import org.d2u.base.shared.data.ProductHistoricalPriceManager;
import org.d2u.base.shared.data.ProductMapper;
import org.d2u.base.shared.model.HistoricalQuantity;
import org.d2u.base.shared.model.Product;
import org.d2u.base.shared.model.Quantity;
import org.d2u.base.shared.model.Unit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestProductHistoricalPrice extends TestBase{
    String schema1 = "store1";
    Unit currency = new Unit("Taiwan Dollar",new Unit.Scope("CUR"));
    static Logger logger = null;
    static{
        logger = LoggerFactory.getLogger(TestProductHistoricalPrice.class);
    }


    @Test
    public void insert(){
        doTransaction(schema1, session -> {
            ProductMapper mapper = session.getMapper(ProductMapper.class);
            Product[] newProducts = {
                    new Product("iPhone 15",new HistoricalQuantity(new Quantity(50000d,currency), OffsetDateTime.MIN,OffsetDateTime.now())),
                    new Product("Huawei Mate 60",new HistoricalQuantity(new Quantity(35000d,currency),OffsetDateTime.now()))
            };
            for (Product newProduct : newProducts) {
                mapper.insert(newProduct);
            }
            Product iphone15 = new Product("iPhone 15");
            Quantity currentIPhonePrice = new Quantity(45000d,currency);
            HistoricalQuantity<Product> currentPrice = new HistoricalQuantity<Product>(iphone15,currentIPhonePrice, OffsetDateTime.now());
            ProductHistoricalPriceManager priceManager = session.getMapper(ProductHistoricalPriceManager.class);
            priceManager.insert(currentPrice);

            List<Product> products = mapper.findAll();
            assertEquals(2,products.size(),"Query newly inserted product got");
            for(Product p:products){
                if(p.equals(iphone15))
                    assertEquals(currentIPhonePrice,p.getCurrentPrice().getQuantity(),"IPhone 15 latest price");
            }
        });
    }
}
