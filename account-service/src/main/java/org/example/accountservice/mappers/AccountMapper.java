package org.example.accountservice.mappers;

import org.example.accountservice.Models.UserResponseV2;
import org.example.accountservice.dtos.AccountRequest;
import org.example.accountservice.dtos.AccountResponse;
import org.example.accountservice.entities.Account;

public interface AccountMapper {
    public AccountResponse  toAccountResponse(Account account);
    public Account toAccount(AccountRequest accountRequest);
}
