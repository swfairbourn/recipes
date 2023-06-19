package com.fairbourn.finance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fairbourn.finance.model.BudgetCategory;
import com.fairbourn.finance.services.BudgetCategoryService;

import java.util.List;

@RestController
@RequestMapping("api/v1/budgetcategories")
public class BudgetCategoryController {

    private final BudgetCategoryService budgetCategoryService;

    @Autowired
    public BudgetCategoryController(BudgetCategoryService budgetCategoryService) {
        this.budgetCategoryService = budgetCategoryService;
    }

    @PostMapping
    public BudgetCategory createBudgetCategory(@RequestBody BudgetCategory budgetCategory) {
        return budgetCategoryService.createBudgetCategory(budgetCategory);
    }

    @DeleteMapping("/{id}")
    public void deleteBudgetCategory(@PathVariable int id) {
        budgetCategoryService.deleteBudgetCategory(id);
    }

    @GetMapping
    public List<BudgetCategory> getAllBudgetCategories() {
        return budgetCategoryService.getAllBudgetCategories();
    }
}

