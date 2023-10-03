package org.d2u.base.server.model.model;

import org.d2u.base.shared.data.*;
import org.d2u.base.shared.model.HistoricalQuantity;
import org.d2u.base.shared.model.Product;
import org.d2u.base.shared.model.Quantity;
import org.d2u.base.shared.model.Unit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestProduct extends TestBase{
        String schema1 = "store1";
        Unit currency = new Unit("Taiwan Dollar",new Unit.Scope("CUR"));
        static Logger logger = null;
        static{
            logger = LoggerFactory.getLogger(TestProduct.class);
        }

        @BeforeEach
        void initData(){
            delete();
            insert();
        }

        void insert(){
            doTransaction(schema1, session -> {
                ProductMapper mapper = session.getMapper(ProductMapper.class);
                Product[] newProducts = {
                        new Product("iPhone 15",new HistoricalQuantity(new Quantity(50000d,currency), OffsetDateTime.MIN,OffsetDateTime.now())),
                        new Product("Huawei Mate 60",new HistoricalQuantity(new Quantity(35000d,currency),OffsetDateTime.now())),
                        new Product("Sharp")
                };
                for (Product newProduct : newProducts) {
                    mapper.insert(newProduct);
                }

                List<Product> products = mapper.findAll();
                assertEquals(3,products.size(),"Query newly inserted product got");
                for(Product p:products){
                    if(p.getName().equals("Sharp")){
                        assertNull(p.getCurrentPrice(), "Product(" + p + ") does not have currentPrice set");
                    }else {
                        assertNotNull(p.getCurrentPrice(), "Product(" + p + ") currentPrice ");
                        assertSame(p,p.getCurrentPrice().getOwner(), "Product(" + p + ") currentPrice should have owner set bidirectional");
                    }
                }

            });
        }

        @Test
        public void delete(){
            doTransaction(schema1,session->{
                ProductMapper mapper = session.getMapper(ProductMapper.class);
                List<Product> products = mapper.findAll();
                //assertEquals(2,products.size(),"Query all products got");
                for (Product newProduct : products) {
                    mapper.delete(newProduct);
                }
                products = mapper.findAll();
                assertEquals(0,products.size(),"Query all products after delete got");
            });
        }

        @Test
        public void findAll(){
            doQuery(schema1, session -> {
                ProductMapper helper = session.getMapper(ProductMapper.class);
                List<Product> items = helper.findAll();
                items.forEach(u -> logger.debug("findAll() from "+schema1+" return => "+u.toString()));
                assertEquals(3,items.size(),"Product count");
            });
        }

}
