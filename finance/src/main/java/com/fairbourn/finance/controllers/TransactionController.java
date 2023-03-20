package com.fairbourn.finance.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fairbourn.finance.model.Transaction;
import com.fairbourn.finance.services.TransactionService;

@RequestMapping("api/v1/transaction")
@RestController
public class TransactionController {

	private final TransactionService transactionService;

	@Autowired
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}
	
	@PostMapping("/insertTransaction")
	public void insertTransaction(@RequestBody Transaction transaction) {
		transactionService.insertTransaction(transaction);
	}
	
	@GetMapping("/getAllTransactions")
	public List<Transaction> getAllTransactions() {
		return transactionService.getAllTransactions();
	}
	
}
