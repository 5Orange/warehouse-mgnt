package com.mgnt.warehouse.modal.predicate;

import com.mgnt.warehouse.modal.QOrders;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

import java.time.LocalDateTime;

import static com.mgnt.warehouse.modal.predicate.PredicateUtils.fromDateRange;

public class OrderPredicate {
    public static OrderPredicateBuilder builder() {
        return new OrderPredicateBuilder();
    }

    public static class OrderPredicateBuilder {

        private static final QOrders Q_ORDERS = QOrders.orders;
        private BooleanExpression booleanExpression = Expressions.asBoolean(true).isTrue();

        public OrderPredicateBuilder customerNameLike(String value) {
            this.booleanExpression = this.booleanExpression.and(Q_ORDERS.customerName.contains(value));
            return this;
        }

        public OrderPredicateBuilder createDateBetween(LocalDateTime fromDate, LocalDateTime toDate) {
            this.booleanExpression = this.booleanExpression.and(fromDateRange(Q_ORDERS.createDate, fromDate, toDate));
            return this;
        }

        public OrderPredicateBuilder productContains(String productCode) {
            this.booleanExpression = this.booleanExpression.and(Q_ORDERS.orderItems.any().product.productCode.eq(productCode));
            return this;
        }

        public BooleanExpression build() {
            return this.booleanExpression;
        }
    }
}
