package com.fairbourn.finance.databaseAccessObjects.implementation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fairbourn.finance.databaseAccessObjects.TransactionDao;
import com.fairbourn.finance.model.Transaction;

public class PostgresTransactionDataAccessService implements TransactionDao {

	@Override
	public boolean insertTransaction(UUID id, Transaction transaction) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Transaction> getAllTransactions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Transaction> getTransactionById(UUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public boolean deleteTransactionById(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateTransactionById(UUID id, Transaction transaction) {
		// TODO Auto-generated method stub
		return false;
	}

}
