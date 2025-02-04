package com.mgnt.warehouse.modal.predicate;

import com.mgnt.warehouse.modal.QSupplier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

import java.time.LocalDateTime;

import static com.mgnt.warehouse.modal.predicate.PredicateUtils.fromDateRange;

public class SupplierPredicate {
    public static SupplierPredicateBuilder builder() {
        return new SupplierPredicateBuilder();
    }

    public static class SupplierPredicateBuilder {
        private static final QSupplier Q_SUPPLIER = QSupplier.supplier;

        private BooleanExpression supplierFilter = Expressions.asBoolean(true).isTrue();

        public SupplierPredicateBuilder supplierNameLike(String value) {
            this.supplierFilter = supplierFilter.and(Q_SUPPLIER.name.contains(value));
            return this;
        }

        public SupplierPredicateBuilder supplierPhoneLike(String value) {
            this.supplierFilter = supplierFilter.and(Q_SUPPLIER.phone.contains(value));
            return this;
        }

        public SupplierPredicateBuilder addressLike(String value) {
            this.supplierFilter = supplierFilter.and(Q_SUPPLIER.address.contains(value));
            return this;
        }

        public SupplierPredicateBuilder codeLike(String value) {
            this.supplierFilter = supplierFilter.and(Q_SUPPLIER.supplierCode.contains(value));
            return this;
        }

        public SupplierPredicateBuilder createDateBetween(LocalDateTime fromDate, LocalDateTime toDate) {
            this.supplierFilter = this.supplierFilter.and(fromDateRange(Q_SUPPLIER.createDate, fromDate, toDate));
            return this;
        }

        public BooleanExpression build() {
            return this.supplierFilter;
        }
    }
}
