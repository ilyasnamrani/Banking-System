package org.example.transactionservice.services;

import org.example.transactionservice.dtos.TransactionRequest;
import org.example.transactionservice.dtos.TransactionResponse;
import org.example.transactionservice.entities.Transaction;

import java.util.List;

public interface TransactionService {
    public TransactionResponse createTransaction(TransactionRequest transaction);
    public void deleteTransaction(Long idTransaction);
    public TransactionResponse getTransactionByIdForAdmin(Long idTransaction);
    public List<TransactionResponse> getAllTransactionsForAdmin();
    public List<TransactionResponse> getAllTransactionsForSender(Long idSenderAccount);
    public List<TransactionResponse> getAllTransactionsForReceiver(Long idReceiverAccount);

}
