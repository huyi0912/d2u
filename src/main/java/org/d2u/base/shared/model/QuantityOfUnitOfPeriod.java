package org.d2u.base.shared.model;

import java.time.LocalDateTime;

/**
 * @deprecated
 * @param quantityOfUnit
 * @param startTime
 * @param endTime
 */
public record QuantityOfUnitOfPeriod (QuantityOfUnit quantityOfUnit, LocalDateTime startTime, LocalDateTime endTime){ }
