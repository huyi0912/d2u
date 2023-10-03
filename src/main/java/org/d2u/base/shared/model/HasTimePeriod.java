package org.d2u.base.shared.model;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

public interface HasTimePeriod {
    OffsetDateTime getStartTime();
    OffsetDateTime getEndTime();
}
