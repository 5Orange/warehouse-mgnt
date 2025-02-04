package com.mgnt.warehouse.modal.predicate;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class PredicateUtils {

    public static BooleanExpression fromDateRange(DateTimePath<Instant> instantDateTimePath, LocalDateTime fromDate, LocalDateTime toDate) {
        Instant from, to;
        if (fromDate != null) {
            from = ZonedDateTime.of(fromDate, ZoneId.systemDefault()).toInstant();
        } else {
            from = Instant.now().minus(1, ChronoUnit.DAYS);
        }
        if (toDate != null) {
            to = ZonedDateTime.of(toDate, ZoneId.systemDefault()).toInstant();
        } else {
            to = Instant.now();
        }
        return instantDateTimePath.between(from, to);
    }
}
