package com.mgnt.warehouse.modal.predicate;

import com.mgnt.warehouse.modal.QSupplier;
import com.querydsl.core.types.dsl.BooleanExpression;

public class SupplierPredicate {
    private static final QSupplier Q_SUPPLIER = QSupplier.supplier;

    public static BooleanExpression supplierNameLike(BooleanExpression expression, String value) {
        return expression.and(Q_SUPPLIER.name.contains(value));
    }

    public static BooleanExpression supplierPhoneLike(BooleanExpression expression, String value) {
        return expression.and(Q_SUPPLIER.phone.contains(value));
    }

    public static BooleanExpression addressLike(BooleanExpression expression, String value) {
        return expression.and(Q_SUPPLIER.address.contains(value));
    }

    public static BooleanExpression codeLike(BooleanExpression expression, String value) {
        return expression.and(Q_SUPPLIER.code.contains(value));
    }

}
