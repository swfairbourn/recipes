package com.fairbourn.finance.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fairbourn.finance.databaseAccessObjects.TransactionDao;
import com.fairbourn.finance.model.Transaction;

@Service
public class TransactionService {

	private final TransactionDao transactionDao;
	
	@Autowired
	public TransactionService(@Qualifier("inMemory") TransactionDao transactionDao) { //@Qualifier("inMemory") points to the TransactionDataAccessService. //TODO: Replace with mySQL or mongoDB
		this.transactionDao = transactionDao;
	}

	public boolean insertTransaction(Transaction transaction) {
		return transactionDao.insertTransaction(transaction);
	}

	public List<Transaction> getAllTransactions() {
		return transactionDao.getAllTransactions();
	}
	
	public Optional<Transaction> getTransactionById(UUID id) {
		return transactionDao.getTransactionById(id);
	}
	
	public boolean deleteTransactionById(UUID id) {
		return transactionDao.deleteTransactionById(id);
	}
	
	public boolean updateTransactionById(UUID id, Transaction transaction) {
		return transactionDao.updateTransactionById(id, transaction);
	}
}
