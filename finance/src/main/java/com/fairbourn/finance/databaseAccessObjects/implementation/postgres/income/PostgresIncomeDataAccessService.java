package com.fairbourn.finance.databaseAccessObjects.implementation.postgres.income;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.fairbourn.finance.databaseAccessObjects.IncomeDatabaseAccessObject;
import com.fairbourn.finance.model.Income;

import java.time.LocalDate;
import java.util.List;

public class PostgresIncomeDataAccessService implements IncomeDatabaseAccessObject {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PostgresIncomeDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insertIncome(Income income) {
        String sql = "INSERT INTO income (source, date, amount) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, income.getSource(), income.getDate(), income.getAmount());
    }

    @Override
    public boolean updateIncome(Income income) {
        String sql = "UPDATE income SET source = ?, date = ?, amount = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, income.getSource(), income.getDate(), income.getAmount(), income.getId());
        return rowsAffected > 0;
    }

    @Override
    public Income getIncomeById(int id) {
        String sql = "SELECT * FROM income WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new IncomeMapper(), id);
    }

    @Override
    public List<Income> getAllIncome() {
        String sql = "SELECT * FROM income";
        return jdbcTemplate.query(sql, new IncomeMapper());
    }

    @Override
    public List<Income> getIncomeByDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM income WHERE date BETWEEN ? AND ?";
        return jdbcTemplate.query(sql, new IncomeMapper(), startDate, endDate);
    }
    
    @Override
    public boolean deleteIncome(int incomeId) {
        String sql = "DELETE FROM income WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, incomeId);
        return rowsAffected > 0;
    }
}

