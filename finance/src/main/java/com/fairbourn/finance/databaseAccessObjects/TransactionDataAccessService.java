package com.fairbourn.finance.databaseAccessObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.fairbourn.finance.model.Transaction;

@Repository("inMemory")
public class TransactionDataAccessService implements TransactionDao {

	private static List<Transaction> DB = new ArrayList<>();
	
	@Override
	public int insertTransaction(UUID id, Transaction transaction) {
		DB.add(new Transaction(id, transaction.getMerchant(), transaction.getCost()));
		return 1;
	}

	@Override
	public List<Transaction> getAllTransactions() {
		return DB;
	}

}
