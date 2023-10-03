package org.d2u.base.shared.data;

import org.d2u.base.shared.model.HistoricalQuantity;
import org.d2u.base.shared.model.Product;
import org.d2u.base.shared.util.TableDML;

public interface ProductHistoricalPriceManager extends HistoricalQuantityManager<Product>, TableDML<HistoricalQuantity<Product>> {
}
