package com.fairbourn.finance.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fairbourn.finance.databaseAccessObjects.BudgetCategoryDatabaseAccessObject;
import com.fairbourn.finance.databaseAccessObjects.IncomeDatabaseAccessObject;
import com.fairbourn.finance.databaseAccessObjects.TransactionDatabaseAccessObject;
import com.fairbourn.finance.databaseAccessObjects.implementation.postgres.budgetCategory.PostgresBudgetCategoryDataAccessService;
import com.fairbourn.finance.databaseAccessObjects.implementation.postgres.income.PostgresIncomeDataAccessService;
import com.fairbourn.finance.databaseAccessObjects.implementation.postgres.transaction.PostgresTransactionDataAccessService;

@Configuration
public class ModelsConfig {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
    @Bean
    public IncomeDatabaseAccessObject incomeDatabaseAccessObject() {
        return new PostgresIncomeDataAccessService(jdbcTemplate);
    }

    @Bean
    public BudgetCategoryDatabaseAccessObject budgetCategoryDatabaseAccessObject() {
        return new PostgresBudgetCategoryDataAccessService(jdbcTemplate);
    }
	
    @Bean
    public TransactionDatabaseAccessObject transactionDatabaseAccessObject() {
        return new PostgresTransactionDataAccessService(jdbcTemplate);
    }
	
}
