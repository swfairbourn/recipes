package com.fairbourn.finance.databaseAccessObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.fairbourn.finance.model.Transaction;

@Repository("inMemory")
public class InMemoryTransactionDataAccessService implements TransactionDao {

	private static List<Transaction> DB = new ArrayList<>();
	
	@Override
	public boolean insertTransaction(UUID id, Transaction transaction) {
		DB.add(new Transaction(id, transaction.getMerchant(), transaction.getCost()));
		return true;
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
	public boolean deleteTransactionById(UUID id) {
		DB.stream().filter(transaction -> transaction.getId().equals(id));
		Optional<Transaction> transactionMaybe = getTransactionById(id);
		if (transactionMaybe.isEmpty()) {
			return false;
		} else {
			DB.remove(transactionMaybe.get());
			return true;
		}
	}

	@Override
	public boolean updateTransactionById(UUID id, Transaction transaction) {
		return getTransactionById(id)
				.map(t -> {
					int indexOfTransactionToDelete = DB.indexOf(t);
					if (indexOfTransactionToDelete >= 0) {
						DB.set(indexOfTransactionToDelete, new Transaction(id, transaction.getMerchant(), transaction.getCost()));
						return true;
					}
					return false;
				})
				.orElse(false);
	}

}
