package org.d2u.base.shared.data;

import org.d2u.base.shared.model.HistoricalQuantity;

import java.time.ZonedDateTime;

public interface HistoricalQuantityManager <T>{

    /**
     * find previous quantity record if exist
     * @return previous quantity record.Null if not found
     */
    HistoricalQuantity<T> findPrevious();

    /**
     * find history quantity record of specific time
     * @param dateTime the time that HistoricalQuantity include
     * @return history quantity record of specific time
     */
    HistoricalQuantity<T> findAt(ZonedDateTime dateTime);

}
