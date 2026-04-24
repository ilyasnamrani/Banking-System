package org.example.accountservice.mappers;

import org.example.accountservice.Models.UserResponseV2;
import org.example.accountservice.dtos.AccountRequest;
import org.example.accountservice.dtos.AccountResponse;
import org.example.accountservice.entities.Account;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AccountMapperImp implements AccountMapper {
    @Override
    public AccountResponse toAccountResponse(Account account, UserResponseV2 user) {
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setIdAccount(account.getIdAccount());
        accountResponse.setType(account.getType());
        accountResponse.setBalance(account.getBalance());
        accountResponse.setCreatedAt(account.getCreatedAt());
        accountResponse.setIdUser(account.getIdUser());
        accountResponse.setRegistrationId(account.getRegistrationId());
        accountResponse.setUser(user);

        return accountResponse;
    }

    @Override
    public Account toAccount(AccountRequest accountRequest) {
        Account account = new Account();
        account.setType(accountRequest.getType());
        account.setBalance(0.00);
        account.setCreatedAt(LocalDate.now());
        account.setUpdatedAt(LocalDate.now());
        account.setIdUser(accountRequest.getIdUser());
        account.setStatus("Created");
        account.setRegistrationId(generateRegistrationId());
        return account;
    }

    private String generateRegistrationId() {
        return "ACC-"+ System.currentTimeMillis();
    }
}
