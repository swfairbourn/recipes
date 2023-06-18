package com.fairbourn.finance.databaseAccessObjects;

import java.time.LocalDate;
import java.util.List;

import com.fairbourn.finance.model.Income;

public interface IncomeDatabaseAccessObject {
    void insertIncome(Income income);
    
    void updateIncome(Income income);
    
    Income getIncomeById(int id);
    
    List<Income> getAllIncome();
    
    List<Income> getIncomeByDateRange(LocalDate startDate, LocalDate endDate);
    
    void deleteIncome(int id);
}

