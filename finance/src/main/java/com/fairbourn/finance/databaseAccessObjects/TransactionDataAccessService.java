package com.fairbourn.finance.databaseAccessObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

	@Override
	public Optional<Transaction> getTransactionById(UUID id) {
		return DB.stream()
		.filter(transaction -> transaction.getId().equals(id))
		.findFirst();
	}

	@Override
	public int deleteTransactionById(UUID id) {
		DB.stream().filter(transaction -> transaction.getId().equals(id));
		Optional<Transaction> transactionMaybe = getTransactionById(id);
		if (transactionMaybe.isEmpty()) {
			return 0;
		} else {
			DB.remove(transactionMaybe.get());
			return 1;
		}
	}

	@Override
	public int updateTransactionById(UUID id, Transaction transaction) {
		return getTransactionById(id)
				.map(t -> {
					int indexOfTransactionToDelete = DB.indexOf(transaction);
					if (indexOfTransactionToDelete >= 0) {
						DB.set(indexOfTransactionToDelete, transaction);
						return 1;
					}
					return 0;
				})
				.orElse(0);
	}

}
