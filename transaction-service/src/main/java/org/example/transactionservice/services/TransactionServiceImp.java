package org.example.transactionservice.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.transactionservice.dtos.TransactionRequest;
import org.example.transactionservice.dtos.TransactionResponse;
import org.example.transactionservice.entities.Transaction;
import org.example.transactionservice.enums.Status;
import org.example.transactionservice.mapper.TransactionMapper;
import org.example.transactionservice.repo.TransactionRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImp implements TransactionService {
    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final KafkaProducer kafkaProducer;

    public TransactionServiceImp(TransactionMapper transactionMapper,
                                 TransactionRepository transactionRepository,
                                 KafkaProducer kafkaProducer) {
        this.transactionMapper = transactionMapper;
        this.transactionRepository = transactionRepository;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    @Transactional
    public TransactionResponse createTransaction(TransactionRequest transaction) {
        Transaction t = transactionMapper.toTransaction(transaction);
        transactionRepository.save(t);
        TransactionResponse transactionResponse = transactionMapper.toTransactionResponse(t);
        transactionResponse.setStatus(Status.TRANSACTION_INITIATED);
        kafkaProducer.sendTransactionRequested(transactionResponse);
        return transactionResponse;
    }

    @Override
    @Transactional
    public void deleteTransaction(Long idTransaction) {
        transactionRepository.deleteById(idTransaction);
    }

    @Override
    public TransactionResponse getTransactionByIdForAdmin(Long idTransaction) {
        Transaction transaction = transactionRepository.findById(idTransaction).orElseThrow(
                () -> new EntityNotFoundException("Transaction not found with id " + idTransaction)
        );
        return transactionMapper.toTransactionResponse(transaction);
    }


    @Override
    public List<TransactionResponse> getAllTransactionsForAdmin() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream().map(transactionMapper::toTransactionResponse).collect(Collectors.toList());
    }

    @Override
    public List<TransactionResponse> getAllTransactionsForSender(Long idSenderAccount) {
        List<Transaction> ts = transactionRepository.findByIdFromAccount(idSenderAccount);
        return ts.stream().map(transactionMapper::toTransactionResponse).collect(Collectors.toList());
    }

    @Override
    public List<TransactionResponse> getAllTransactionsForReceiver(Long idReceiverAccount) {
        List<Transaction> ts = transactionRepository.findByIdToAccount(idReceiverAccount);
        return ts.stream().map(transactionMapper::toTransactionResponse).collect(Collectors.toList());
    }

}
