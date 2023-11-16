package com.fairbourn.finance.services;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fairbourn.finance.databaseAccessObjects.BudgetCategoryDatabaseAccessObject;
import com.fairbourn.finance.model.BudgetCategory;

@Service
public class BudgetCategoryService {

    private final BudgetCategoryDatabaseAccessObject categoryDao;
    private final AtomicInteger nextId;

    @Autowired
    public BudgetCategoryService(BudgetCategoryDatabaseAccessObject categoryDao) {
        this.categoryDao = categoryDao;
        this.nextId = new AtomicInteger(1);
    }

    public BudgetCategory createBudgetCategory(BudgetCategory budgetCategory) {
        int id = nextId.getAndIncrement();
        budgetCategory.setId(id);
        categoryDao.insertCategory(budgetCategory);
        return budgetCategory;
    }

    public void deleteBudgetCategory(int id) {
        categoryDao.deleteCategory(id);
    }

    public List<BudgetCategory> getAllBudgetCategories() {
        return categoryDao.getAllCategories();
    }
}
