package com.mgnt.warehouse.modal.predicate;

import com.mgnt.warehouse.modal.QProduct;
import com.querydsl.core.types.dsl.BooleanExpression;

public class ProductPredicate {

    private static final QProduct Q_PRODUCT = QProduct.product;

    public static BooleanExpression productNameLike(BooleanExpression expression, String value) {
        return expression.and(Q_PRODUCT.name.contains(value));
    }

    public static BooleanExpression productCodeLike(BooleanExpression expression, String value) {
        return expression.and(Q_PRODUCT.productCode.contains(value));
    }

    public static BooleanExpression categoryLike(BooleanExpression expression, String value) {
        return expression.and(Q_PRODUCT.category.name.contains(value));
    }

    public static BooleanExpression supplierNameLike(BooleanExpression expression, String value) {
        return expression.and(Q_PRODUCT.supplier.name.contains(value));
    }

}
