package com.mgnt.warehouse.utils;

import java.time.LocalDateTime;

public class ServiceUtils {

    public static String generateProductId() {
        return generateCode("PD");
    }

    public static String generateCategoryCode() {
        return generateCode("CA");
    }

    public static String generateSupplierCode() {
        return generateCode("SP");
    }

    public static String generateOrderCode() {
        return generateCode("ORD");
    }

    private static String generateCode(String prefix) {
        var currentDate = LocalDateTime.now();
        return String.format("%s%s%s%s%s%s", prefix, currentDate.getYear(), currentDate.getMonth(),
            currentDate.getDayOfMonth(), currentDate.getMinute(), currentDate.getSecond());
    }
}
