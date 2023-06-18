package com.fairbourn.finance.databaseAccessObjects.implementation.postgres;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.fairbourn.finance.databaseAccessObjects.TransactionDatabaseAccessObject;
import com.fairbourn.finance.model.Transaction;

@Repository("postgres")
public class PostgresTransactionDataAccessService implements TransactionDatabaseAccessObject {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean insertTransaction(UUID id, Transaction transaction) {
        String sql = "INSERT INTO transactions (id, amount, merchant, category, transaction_date) VALUES (?, ?, ?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(sql, id, transaction.getAmount(), transaction.getMerchant(), transaction.getCategory(), transaction.getTransactionDate());
        return rowsAffected > 0;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        String sql = "SELECT * FROM transactions";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Transaction(
            rs.getObject("id", UUID.class),
            rs.getString("merchant"),
            rs.getDouble("amount"),
            rs.getString("category"),
            rs.getDate("transaction_date").toLocalDate() // Assuming the transaction_date column is of type DATE
        ));
    }

    @Override
    public Optional<Transaction> getTransactionById(UUID id) {
        String sql = "SELECT * FROM transactions WHERE id = ?";
        PreparedStatementCreator psc = connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, id);
            return ps;
        };
        ResultSetExtractor<Optional<Transaction>> rse = rs -> {
            if (rs.next()) {
                Transaction transaction = new Transaction(
                    rs.getObject("id", UUID.class),
                    rs.getString("merchant"),
                    rs.getDouble("amount"),
                    rs.getString("category"),
                    rs.getDate("transaction_date").toLocalDate() // Assuming the transaction_date column is of type DATE
                );
                return Optional.of(transaction);
            } else {
                return Optional.empty();
            }
        };
        return jdbcTemplate.query(psc, rse);
    }

    @Override
    public boolean deleteTransactionById(UUID id) {
        String sql = "DELETE FROM transactions WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    @Override
    public boolean updateTransactionById(UUID id, Transaction transaction) {
        String sql = "UPDATE transactions SET amount = ?, merchant = ?, category = ?, transaction_date = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, transaction.getAmount(), transaction.getMerchant(), transaction.getCategory(), transaction.getTransactionDate(), id);
        return rowsAffected > 0;
    }
}

