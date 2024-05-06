package com.mgnt.warehouse.service;

import java.time.LocalDateTime;

public class ServiceUtils {

    public static String generateProductId() {
        var currentDate = LocalDateTime.now();
        return String.format("P%s%s%s%s", currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth(), currentDate.getMinute());
    }
}
