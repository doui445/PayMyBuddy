package com.paymybuddy.controller;

import com.paymybuddy.model.Transaction;
import com.paymybuddy.service.TransactionServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private TransactionServiceImpl transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.saveTransaction(transaction);
    }

    @PutMapping
    public ResponseEntity<Transaction> updateTransaction(@RequestBody Transaction transaction) {
        return transactionService.updateTransaction(transaction);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTransaction(@RequestParam Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.status(HttpStatus.OK).body("Transaction " + id + " deleted successfully!");
    }
}
