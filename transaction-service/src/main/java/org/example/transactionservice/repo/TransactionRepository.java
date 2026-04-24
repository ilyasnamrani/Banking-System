package org.example.transactionservice.repo;

import org.example.transactionservice.dtos.TransactionResponse;
import org.example.transactionservice.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository  extends JpaRepository<Transaction, Long> {

    List<Transaction> findByIdFromAccount(Long idSenderAccount);

    List<Transaction> findByIdToAccount(Long idReceiverAccount);
}
