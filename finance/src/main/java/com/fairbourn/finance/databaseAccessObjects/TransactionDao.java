package com.fairbourn.finance.databaseAccessObjects;

import java.util.List;
import java.util.UUID;

import com.fairbourn.finance.model.Transaction;

public interface TransactionDao {

	int insertTransaction(UUID id, Transaction transaction);
	
	default int insertTransaction(Transaction transaction) {
		UUID id = UUID.randomUUID();
		return insertTransaction(id, transaction);
	}
	
	List<Transaction> getAllTransactions();
	
}
