package com.paymybuddy.repository;

import com.paymybuddy.model.Transaction;
import com.paymybuddy.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TransactionRepositoryTest {
    @Autowired
    TransactionRepository transactionRepository;

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

    @DisplayName("JUnit test for save transaction operation")
    @Test
    public void givenTransactionObject_whenSave_thenReturnSavedTransaction() {
        Transaction savedTransaction = transactionRepository.save(transaction);
        assertThat(savedTransaction).isNotNull();
        assertThat(savedTransaction.getId()).isGreaterThan(0);
    }

    @DisplayName("JUnit test for get transaction by id operation")
    @Test
    public void givenTransactionObject_whenFindById_thenReturnTransactionObject() {
        transactionRepository.save(transaction);
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transaction.getId());
        assertThat(optionalTransaction.isPresent()).isTrue();
        Transaction transactionDB = optionalTransaction.get();
        assertThat(transactionDB).isNotNull();
    }

    @DisplayName("JUnit test for update transaction operation")
    @Test
    public void givenTransactionObject_whenUpdateTransaction_thenReturnUpdatedTransaction() {
        transactionRepository.save(transaction);

        Optional<Transaction> optionalTransaction = transactionRepository.findById(transaction.getId());
        assertThat(optionalTransaction.isPresent()).isTrue();

        Transaction savedTransaction = optionalTransaction.get();
        savedTransaction.setAmount(142);
        savedTransaction.setDescription("holidays");
        Transaction updatedTransaction =  transactionRepository.save(savedTransaction);

        assertThat(updatedTransaction.getAmount()).isEqualTo(142);
        assertThat(updatedTransaction.getDescription()).isEqualTo("holidays");
    }

    @DisplayName("JUnit test for delete transaction operation")
    @Test
    public void givenTransactionObject_whenDelete_thenRemoveTransaction() {

        transactionRepository.save(transaction);

        transactionRepository.deleteById(transaction.getId());
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transaction.getId());

        assertThat(optionalTransaction).isEmpty();
    }
}
