package org.d2u.base.shared.data;

import org.apache.ibatis.session.ResultHandler;
import org.d2u.base.shared.model.Product;
import org.d2u.base.shared.util.TableDDL;
import org.d2u.base.shared.util.TableDML;

import java.util.List;

public interface ProductMapper extends TableDML<Product> {
    void findAllProducts(ResultHandler<List<Product>> handler);
}
