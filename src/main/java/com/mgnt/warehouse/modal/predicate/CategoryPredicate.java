package com.mgnt.warehouse.modal.predicate;

import com.mgnt.warehouse.modal.QCategory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

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

        public void codeLike(String value) {
            this.categoryExpression = categoryExpression.and(Q_CATEGORY.categoryCode.contains(value));
        }

        public void categoryNameLike(String value) {
            this.categoryExpression = categoryExpression.and(Q_CATEGORY.name.contains(value));
        }

    }


}
