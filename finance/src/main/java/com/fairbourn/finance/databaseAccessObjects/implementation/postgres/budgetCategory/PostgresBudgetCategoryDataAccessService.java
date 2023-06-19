package com.fairbourn.finance.databaseAccessObjects.implementation.postgres.budgetCategory;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.fairbourn.finance.databaseAccessObjects.BudgetCategoryDatabaseAccessObject;
import com.fairbourn.finance.model.BudgetCategory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PostgresBudgetCategoryDataAccessService implements BudgetCategoryDatabaseAccessObject {
    private final JdbcTemplate jdbcTemplate;

    public PostgresBudgetCategoryDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insertCategory(BudgetCategory category) {
        String sql = "INSERT INTO budget_category (value) VALUES (?)";
        jdbcTemplate.update(sql, category.getValue());
    }

    @Override
    public void deleteCategory(int categoryId) {
        String sql = "DELETE FROM budget_category WHERE id = ?";
        jdbcTemplate.update(sql, categoryId);
    }

    @Override
    public List<BudgetCategory> getAllCategories() {
        String sql = "SELECT * FROM budget_category";
        return jdbcTemplate.query(sql, new BudgetCategoryMapper());
    }
    
    private static class BudgetCategoryMapper implements RowMapper<BudgetCategory> {
        @Override
        public BudgetCategory mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            int id = resultSet.getInt("id");
            String value = resultSet.getString("value");
            return new BudgetCategory(id, value);
        }
    }
}

