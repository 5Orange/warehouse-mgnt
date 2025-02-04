package com.mgnt.warehouse.modal.common;


import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record MetricFilter(String filterField,
                           String value,
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime from,
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime to) {
}
