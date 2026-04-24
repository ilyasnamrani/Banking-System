package org.example.transactionservice.mapper;

import org.example.transactionservice.dtos.TransactionRequest;
import org.example.transactionservice.dtos.TransactionResponse;
import org.example.transactionservice.entities.Transaction;
import org.example.transactionservice.enums.Status;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapperImp implements TransactionMapper {
    @Override
    public Transaction toTransaction(TransactionRequest req) {
        Transaction transaction = new Transaction();
        transaction.setAmount(req.getAmount());
        transaction.setTime(req.getTime());
        transaction.setStatus(Status.NULL);
        transaction.setIdToAccount(req.getIdToAccount());
        transaction.setIdFromAccount(req.getIdFromAccount());
        return transaction;
    }

    @Override
    public TransactionResponse toTransactionResponse(Transaction transaction) {
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setIdTransaction(transaction.getIdTransaction());
        transactionResponse.setAmount(transaction.getAmount());
        transactionResponse.setTime(transaction.getTime());
        transactionResponse.setStatus(transaction.getStatus());
        transactionResponse.setIdFromAccount(transaction.getIdFromAccount());
        transactionResponse.setIdToAccount(transaction.getIdToAccount());

        return transactionResponse;
    }
}
