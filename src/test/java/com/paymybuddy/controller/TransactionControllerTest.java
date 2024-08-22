package com.paymybuddy.controller;

import com.paymybuddy.model.Transaction;
import com.paymybuddy.model.User;
import com.paymybuddy.service.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {
    @Mock
    private TransactionServiceImpl transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        User user1 = User.builder()
                .username("steveLander")
                .build();
        User user2 = User.builder()
                .username("doui445")
                .build();

        transaction = Transaction.builder()
                .amount(20.95)
                .sender(user1)
                .receiver(user2)
                .description("restaurant")
                .build();
    }

    @DisplayName("JUnit test for createTransaction method")
    @Test
    public void givenTransactionObject_whenCreateTransaction_thenReturnTransactionObject() {
        given(transactionService.saveTransaction(transaction))
                .willReturn(transaction);
        Transaction savedTransaction = transactionController.createTransaction(transaction);
        assertThat(savedTransaction).isEqualTo(transaction);
    }

    @DisplayName("JUnit test for updateTransaction method")
    @Test
    public void givenTransactionObject_whenUpdateTransaction_thenReturnResponseEntityOfTransaction() {
        given(transactionService.updateTransaction(transaction))
                .willReturn(new ResponseEntity<>(transaction, HttpStatus.OK));
        ResponseEntity<Transaction> updatedTransaction = transactionController.updateTransaction(transaction);
        assertThat(updatedTransaction.getBody()).isEqualTo(transaction);
        assertThat(updatedTransaction.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("JUnit test for deleteTransaction method")
    @Test
    public void givenTransactionObject_whenDeleteTransaction_thenReturnResponseEntityOK() {
        doNothing().when(transactionService).deleteTransaction(transaction.getId());
        ResponseEntity<String> isDeleted = transactionController.deleteTransaction(transaction.getId());
        assertThat(isDeleted.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
