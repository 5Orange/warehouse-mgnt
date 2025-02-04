package com.mgnt.warehouse.modal.predicate;

import com.mgnt.warehouse.modal.QCategory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

import java.time.LocalDateTime;

import static com.mgnt.warehouse.modal.predicate.PredicateUtils.fromDateRange;

public class CategoryPredicate {

    public static CategoryPredicateBuilder builder() {
        return new CategoryPredicateBuilder();
    }

    public static class CategoryPredicateBuilder {
        private BooleanExpression categoryExpression = Expressions.asBoolean(true).isTrue();

        public BooleanExpression build() {
            return this.categoryExpression;
        }

        public static final QCategory Q_CATEGORY = QCategory.category;

        public CategoryPredicateBuilder codeLike(String value) {
            this.categoryExpression = categoryExpression.and(Q_CATEGORY.categoryCode.contains(value));
            return this;
        }

        public CategoryPredicateBuilder categoryNameLike(String value) {
            this.categoryExpression = categoryExpression.and(Q_CATEGORY.name.contains(value));
            return this;
        }

        public CategoryPredicateBuilder createDateBetween(LocalDateTime fromDate, LocalDateTime toDate) {
            this.categoryExpression = this.categoryExpression.and(fromDateRange(Q_CATEGORY.createDate, fromDate, toDate));
            return this;
        }

    }


}
