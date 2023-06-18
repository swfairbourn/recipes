package com.fairbourn.finance.databaseAccessObjects.implementation.postgres.budgetCategory;

import java.util.List;

import com.fairbourn.finance.model.BudgetCategory;

public interface BudgetCategoryDatabaseAccessObject {
    void createCategory(BudgetCategory category);
    void deleteCategory(BudgetCategory category);
    List<BudgetCategory> getAllCategories();
}

