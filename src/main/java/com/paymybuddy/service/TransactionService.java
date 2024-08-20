package com.paymybuddy.service;

import com.paymybuddy.model.Transaction;
import org.springframework.http.ResponseEntity;

public interface TransactionService {
    Transaction saveTransaction(Transaction transaction);
    ResponseEntity<Transaction> updateTransaction(Transaction transaction);
    void deleteTransaction(Long id);
}
