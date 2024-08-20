package com.paymybuddy.service;

import com.paymybuddy.model.Transaction;
import com.paymybuddy.model.User;
import com.paymybuddy.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

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

    @DisplayName("JUnit test for saveTransaction method")
    @Test
    public void givenTransactionObject_whenSaveTransaction_thenReturnTransactionObject() {
        given(transactionRepository.findById(transaction.getId()))
                .willReturn(Optional.empty());

        given(transactionRepository.save(transaction)).willReturn(transaction);

        System.out.println(transactionRepository);
        System.out.println(transactionService);

        Transaction savedTransaction = transactionService.saveTransaction(transaction);

        System.out.println(savedTransaction);
        assertThat(savedTransaction).isNotNull();
    }

    @DisplayName("JUnit test for saveTransaction method which throws exception")
    @Test
    public void givenExistingTransaction_whenSaveTransaction_thenReturnNull() {
        given(transactionRepository.findById(transaction.getId()))
                .willReturn(Optional.ofNullable(transaction));

        System.out.println(transactionRepository);
        System.out.println(transactionService);

        assertNull(transactionService.saveTransaction(transaction));

        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @DisplayName("JUnit test for updateTransaction method")
    @Test
    public void givenTransactionObject_whenUpdateTransaction_thenReturnUpdatedTransaction() {
        Transaction transaction1 = transaction;
        transaction1.setAmount(142);
        transaction1.setDescription("holidays");

        given(transactionRepository.findById(transaction.getId())).willReturn(Optional.ofNullable(transaction));
        ResponseEntity<Transaction> updatedTransaction = transactionService.updateTransaction(transaction1);

        assertThat(updatedTransaction.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("JUnit test for deleteTransaction method")
    @Test
    public void givenTransactionFirstNameAndLastName_whenDeleteTransaction_thenNothing() {
        Long transactionId = transaction.getId();

        willDoNothing().given(transactionRepository).deleteById(transactionId);

        transactionService.deleteTransaction(transactionId);

        verify(transactionRepository, times(1)).deleteById(transactionId);
    }
}
