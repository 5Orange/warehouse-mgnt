package com.mgnt.warehouse.modal.predicate;

import com.mgnt.warehouse.modal.QProduct;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

import java.time.LocalDateTime;

import static com.mgnt.warehouse.modal.predicate.PredicateUtils.fromDateRange;

public class ProductPredicate {

    private static final QProduct Q_PRODUCT = QProduct.product;

    public static ProductPredicateBuilder builder() {
        return new ProductPredicateBuilder();
    }

    public static class ProductPredicateBuilder {

        private BooleanExpression productFilter = Expressions.asBoolean(true).isTrue();

        public ProductPredicateBuilder productNameLike(String value) {
            this.productFilter = this.productFilter.and(Q_PRODUCT.name.contains(value));
            return this;
        }

        public ProductPredicateBuilder productCodeLike(String value) {
            this.productFilter = this.productFilter.and(Q_PRODUCT.productCode.contains(value));
            return this;
        }

        public ProductPredicateBuilder categoryLike(String value) {
            this.productFilter = this.productFilter.and(Q_PRODUCT.category.name.contains(value));
            return this;
        }

        public ProductPredicateBuilder supplierNameLike(String value) {
            this.productFilter = this.productFilter.and(Q_PRODUCT.supplier.name.contains(value));
            return this;
        }

        public ProductPredicateBuilder productCodeEq(String value) {
            this.productFilter = this.productFilter.and(Q_PRODUCT.productCode.eq(value));
            return this;
        }

        public ProductPredicateBuilder supplierCodeEq(String value) {
            this.productFilter = this.productFilter.and(Q_PRODUCT.supplier.supplierCode.eq(value));
            return this;
        }

        public ProductPredicateBuilder categoryCodeEq(String value) {
            this.productFilter = this.productFilter.and(Q_PRODUCT.category.categoryCode.eq(value));
            return this;
        }

        public ProductPredicateBuilder createDateBetween(LocalDateTime fromDate, LocalDateTime toDate) {
            this.productFilter = this.productFilter.and(fromDateRange(Q_PRODUCT.createDate, fromDate, toDate));
            return this;
        }

        public BooleanExpression build() {
            return this.productFilter;
        }
    }
}
