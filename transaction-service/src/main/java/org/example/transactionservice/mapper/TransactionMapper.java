package org.example.transactionservice.mapper;

import org.example.transactionservice.dtos.TransactionRequest;
import org.example.transactionservice.dtos.TransactionResponse;
import org.example.transactionservice.entities.Transaction;

public interface TransactionMapper {
    public Transaction toTransaction(TransactionRequest req);
    public TransactionResponse toTransactionResponse(Transaction transaction);
}
