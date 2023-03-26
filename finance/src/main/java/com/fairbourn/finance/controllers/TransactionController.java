package com.fairbourn.finance.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fairbourn.finance.model.Transaction;
import com.fairbourn.finance.services.TransactionService;

import jakarta.servlet.http.HttpServletResponse;

@RequestMapping("api/v1/transaction")
@RestController
public class TransactionController {

	private final TransactionService transactionService;

	@Autowired
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}
	
	@PostMapping("/insertTransaction")
	public void insertTransaction(@RequestBody Transaction transaction, HttpServletResponse response) {
		boolean inserted = transactionService.insertTransaction(transaction);
		if (inserted) {
			response.setStatus(HttpServletResponse.SC_CREATED);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to insert transaction");
		}
	}
	
	@GetMapping("/getAllTransactions")
	public List<Transaction> getAllTransactions() {
		List<Transaction> transactions = transactionService.getAllTransactions();
		return transactions;
	}
	
	@GetMapping(path ="/{id}")
	public Transaction getTransactionById(@PathVariable("id") UUID id) {
		Optional<Transaction> transaction = transactionService.getTransactionById(id);
		if (transaction.isPresent()) {
			return transaction.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found");
		}
	}
	
	@DeleteMapping(path = "/{id}")
	public void deleteTransactionById(@PathVariable("id") UUID id) {
		transactionService.deleteTransactionById(id);
	}
	
	@PutMapping(path = "/{id}")
	public void updateTransactionById(@PathVariable("id") UUID id, @RequestBody Transaction transaction) {
		transactionService.updateTransactionById(id, transaction);
	}
	
}
