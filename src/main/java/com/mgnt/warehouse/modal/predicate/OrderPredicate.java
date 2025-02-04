package com.mgnt.warehouse.modal.predicate;

import com.mgnt.warehouse.modal.QOrders;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

import java.time.Instant;

public class OrderPredicate {
    public static OrderPredicateBuilder builder() {
        return new OrderPredicateBuilder();
    }

    public static class OrderPredicateBuilder {

        private static final QOrders Q_ORDERS = QOrders.orders;
        private BooleanExpression booleanExpression = Expressions.asBoolean(true).isTrue();

        public void customerNameLike(String value) {
            this.booleanExpression = this.booleanExpression.and(Q_ORDERS.customerName.contains(value));
        }

        public void createDateBetween(Instant from, Instant to) {
            this.booleanExpression = this.booleanExpression.and(Q_ORDERS.createDate.between(from, to));
        }

        public void productContains(String productCode) {
            this.booleanExpression = this.booleanExpression.and(Q_ORDERS.orderItems.any().product.productCode.eq(productCode));
        }

        public BooleanExpression build() {
            return this.booleanExpression;
        }
    }
}
