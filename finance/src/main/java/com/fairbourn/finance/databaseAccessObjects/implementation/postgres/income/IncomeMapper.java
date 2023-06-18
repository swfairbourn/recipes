package com.fairbourn.finance.databaseAccessObjects.implementation.postgres.income;

import org.springframework.jdbc.core.RowMapper;

import com.fairbourn.finance.model.Income;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class IncomeMapper implements RowMapper<Income> {

    @Override
    public Income mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String source = rs.getString("source");
        LocalDate date = rs.getDate("date").toLocalDate();
        double amount = rs.getDouble("amount");

        return new Income(id, source, date, amount);
    }
}

