package org.example.accountservice.mappers;

import org.example.accountservice.Models.UserResponseV2;
import org.example.accountservice.enums.State;
import org.example.accountservice.dtos.AccountRequest;
import org.example.accountservice.dtos.AccountResponse;
import org.example.accountservice.entities.Account;
import org.example.accountservice.web.UserFeignClient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AccountMapperImp implements AccountMapper {

    private final UserFeignClient userFeignClient;
    public AccountMapperImp(UserFeignClient userFeignClient){
        this.userFeignClient = userFeignClient;
    }
    @Override
    public AccountResponse toAccountResponse(Account account) {
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setIdAccount(account.getIdAccount());
        accountResponse.setType(account.getType());
        accountResponse.setBalance(account.getBalance());
        accountResponse.setCreatedAt(account.getCreatedAt());
        accountResponse.setIdUser(account.getIdUser());
        accountResponse.setState(account.getState());
        accountResponse.setRegistrationId(account.getRegistrationId());
        UserResponseV2 userResponse = userFeignClient.getFeignClientUser(account.getIdUser());

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
        account.setState(State.ACTIVATED);
        account.setRegistrationId(generateRegistrationId());
        return account;
    }

    private String generateRegistrationId() {
        return "ACC-"+ System.currentTimeMillis();
    }
}
