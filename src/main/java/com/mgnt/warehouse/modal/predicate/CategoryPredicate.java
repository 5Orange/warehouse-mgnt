package com.mgnt.warehouse.modal.predicate;

import com.mgnt.warehouse.modal.QCategory;
import com.querydsl.core.types.dsl.BooleanExpression;

public class CategoryPredicate {
    public static final QCategory CATEGORY_EXPRESSION = QCategory.category;

    public static BooleanExpression codeLike(BooleanExpression expression, String value) {
        return expression.and(CATEGORY_EXPRESSION.categoryCode.contains(value));
    }

    public static BooleanExpression categoryNameLike(BooleanExpression expression, String value) {
        return expression.and(CATEGORY_EXPRESSION.name.contains(value));
    }

}
