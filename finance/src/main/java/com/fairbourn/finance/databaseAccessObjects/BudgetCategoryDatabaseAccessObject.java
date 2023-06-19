package com.fairbourn.finance.databaseAccessObjects;

import java.util.List;

import com.fairbourn.finance.model.BudgetCategory;

public interface BudgetCategoryDatabaseAccessObject {
    void insertCategory(BudgetCategory category);
    void deleteCategory(int categoryId);
    List<BudgetCategory> getAllCategories();
}

