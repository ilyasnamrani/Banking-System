package org.example.transactionservice.web;

import org.example.transactionservice.dtos.TransactionRequest;
import org.example.transactionservice.dtos.TransactionResponse;
import org.example.transactionservice.services.TransactionService;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableMethodSecurity
@RequestMapping("/transactions")
public class TransactionController {
    private  final TransactionService transactionService ;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @GetMapping("/all/admin")
    public List<TransactionResponse> getAllTransactionsForAdmin(){
        return transactionService.getAllTransactionsForAdmin();
    }
    @GetMapping("/{id}/admin")
    public TransactionResponse  getTransactionByIdForAdmin(@PathVariable Long id){
        return transactionService.getTransactionByIdForAdmin(id);
    }

    @GetMapping("/sender/{id}")
    public List<TransactionResponse>  getTransactionsByIdSender(@PathVariable Long id){
        return transactionService.getAllTransactionsForSender(id);
    }

    @GetMapping("/receiver/{id}")
    public List<TransactionResponse>  getTransactionsByIdReceiver(@PathVariable Long id){
        return transactionService.getAllTransactionsForReceiver(id);
    }

    @PostMapping("/create")
    public TransactionResponse createTransaction(@RequestBody TransactionRequest transactionRequest){
        return transactionService.createTransaction(transactionRequest);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTransactionById(@PathVariable Long id){
        transactionService.deleteTransaction(id);
    }
}
