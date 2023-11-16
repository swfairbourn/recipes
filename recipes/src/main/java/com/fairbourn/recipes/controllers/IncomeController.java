package com.fairbourn.finance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fairbourn.finance.databaseAccessObjects.IncomeDatabaseAccessObject;
import com.fairbourn.finance.model.Income;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/income")
public class IncomeController {
    private final IncomeDatabaseAccessObject incomeDao;

    @Autowired
    public IncomeController(IncomeDatabaseAccessObject incomeDao) {
        this.incomeDao = incomeDao;
    }

    @PostMapping
    public ResponseEntity<String> insertIncome(@RequestBody Income income) {
        incomeDao.insertIncome(income);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<String> updateIncome(@RequestBody Income income) {
        boolean updated = incomeDao.updateIncome(income);
        if (updated) {
            return ResponseEntity.ok("Income updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Income not found");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Income> getIncomeById(@PathVariable("id") int id) {
        Income income = incomeDao.getIncomeById(id);
        if (income != null) {
            return ResponseEntity.ok(income);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Income>> getIncomeByDateRange(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<Income> incomeList = incomeDao.getIncomeByDateRange(start, end);
        return ResponseEntity.ok(incomeList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIncome(@PathVariable("id") int id) {
        boolean deleted = incomeDao.deleteIncome(id);
        if (deleted) {
            return ResponseEntity.ok("Income deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Income not found");
        }
    }
}

