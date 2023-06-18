package com.fairbourn.finance.databaseAccessObjects.implementation.postgres.budgetCategory;

import org.springframework.jdbc.core.JdbcTemplate;

import com.fairbourn.finance.model.BudgetCategory;

import java.util.List;

public class PostgresBudgetCategoryDataAccessService implements BudgetCategoryDatabaseAccessObject {
    private final JdbcTemplate jdbcTemplate;

    public PostgresBudgetCategoryDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createCategory(BudgetCategory category) {
        String sql = "INSERT INTO budget_categories (category_name) VALUES (?)";
        jdbcTemplate.update(sql, category.name());
    }

    @Override
    public void deleteCategory(BudgetCategory category) {
        String sql = "DELETE FROM budget_categories WHERE category_name = ?";
        jdbcTemplate.update(sql, category.name());
    }

    @Override
    public List<BudgetCategory> getAllCategories() {
        String sql = "SELECT category_name FROM budget_categories";
        return jdbcTemplate.query(sql, (rs, rowNum) -> BudgetCategory.valueOf(rs.getString("category_name")));
    }
}

