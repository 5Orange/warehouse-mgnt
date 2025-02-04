package com.mgnt.warehouse.modal.predicate;

import com.mgnt.warehouse.modal.QSupplier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

public class SupplierPredicate {
    public static SupplierPredicateBuilder builder() {
        return new SupplierPredicateBuilder();
    }

    public static class SupplierPredicateBuilder {
        private static final QSupplier Q_SUPPLIER = QSupplier.supplier;

        private BooleanExpression supplierFilter = Expressions.asBoolean(true).isTrue();

        public void supplierNameLike(String value) {
            this.supplierFilter = supplierFilter.and(Q_SUPPLIER.name.contains(value));
        }

        public void supplierPhoneLike(String value) {
            this.supplierFilter = supplierFilter.and(Q_SUPPLIER.phone.contains(value));
        }

        public void addressLike(String value) {
            this.supplierFilter = supplierFilter.and(Q_SUPPLIER.address.contains(value));
        }

        public void codeLike(String value) {
            this.supplierFilter = supplierFilter.and(Q_SUPPLIER.supplierCode.contains(value));
        }

        public BooleanExpression build() {
            return this.supplierFilter;
        }
    }
}
