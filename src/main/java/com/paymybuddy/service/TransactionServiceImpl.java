package com.paymybuddy.service;

import com.paymybuddy.model.Transaction;
import com.paymybuddy.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository;

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        Optional<Transaction> savedTransaction = transactionRepository.findById(transaction.getId());
        if(savedTransaction.isPresent()){
            System.out.println("Transaction already exist with given amount:" + savedTransaction.get().getAmount());
            return null;
        }
        return transactionRepository.save(transaction);
    }

    @Override
    public ResponseEntity<Transaction> updateTransaction(Transaction transaction) {
        return transactionRepository.findById(transaction.getId())
                .map(savedTransaction -> {
                    savedTransaction.setAmount(transaction.getAmount());
                    savedTransaction.setSender(transaction.getSender());
                    savedTransaction.setReceiver(transaction.getReceiver());
                    savedTransaction.setDescription(transaction.getDescription());

                    Transaction updatedTransaction = transactionRepository.save(savedTransaction);
                    return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
