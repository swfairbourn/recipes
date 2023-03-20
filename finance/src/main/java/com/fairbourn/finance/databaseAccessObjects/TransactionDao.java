package com.fairbourn.finance.databaseAccessObjects;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fairbourn.finance.model.Transaction;

public interface TransactionDao {

	int insertTransaction(UUID id, Transaction transaction);
	
	default int insertTransaction(Transaction transaction) {
		UUID id = UUID.randomUUID();
		return insertTransaction(id, transaction);
	}
	
	List<Transaction> getAllTransactions();
	
	Optional<Transaction> getTransactionById(UUID id);
	
	int deleteTransactionById(UUID id);
	
	int updateTransactionById(UUID id, Transaction transaction);
	
}
