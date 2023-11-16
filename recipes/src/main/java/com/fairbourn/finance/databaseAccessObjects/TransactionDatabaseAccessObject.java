package com.fairbourn.finance.databaseAccessObjects;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fairbourn.finance.model.Transaction;

public interface TransactionDatabaseAccessObject {

	boolean insertTransaction(UUID id, Transaction transaction);
	
	default boolean insertTransaction(Transaction transaction) {
		UUID id = UUID.randomUUID();
		return insertTransaction(id, transaction);
	}
	
	List<Transaction> getAllTransactions();
	
	Optional<Transaction> getTransactionById(UUID id);
	
	boolean deleteTransactionById(UUID id);
	
	boolean updateTransactionById(UUID id, Transaction transaction);
	
}
