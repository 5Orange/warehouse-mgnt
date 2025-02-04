package com.mgnt.warehouse.modal.predicate;

import com.mgnt.warehouse.modal.QProduct;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

public class ProductPredicate {

    private static final QProduct Q_PRODUCT = QProduct.product;

    public static BooleanExpression findByProductCodeAndCategoryCodeAndSupplierCode(String productCode,
                                                                                    String supplierCode,
                                                                                    String categoryCode) {
        return Q_PRODUCT.productCode.eq(productCode)
                .and(Q_PRODUCT.supplier.supplierCode.eq(supplierCode))
                .and(Q_PRODUCT.category.categoryCode.eq(categoryCode));
    }

    public static ProductPredicateBuilder builder() {
        return new ProductPredicateBuilder();
    }

    public static class ProductPredicateBuilder {

        private BooleanExpression productFilter = Expressions.asBoolean(true).isTrue();

        public void productNameLike(String value) {
            this.productFilter = this.productFilter.and(Q_PRODUCT.name.contains(value));
        }

        public void productCodeLike(String value) {
            this.productFilter = this.productFilter.and(Q_PRODUCT.productCode.contains(value));
        }

        public void categoryLike(String value) {
            this.productFilter = this.productFilter.and(Q_PRODUCT.category.name.contains(value));
        }

        public void supplierNameLike(String value) {
            this.productFilter = this.productFilter.and(Q_PRODUCT.supplier.name.contains(value));
        }

        public BooleanExpression build() {
            return this.productFilter;
        }
    }
}
