package org.example.accountservice.services;

import org.example.accountservice.dtos.AccountRequest;
import org.example.accountservice.dtos.AccountResponse;
import org.springframework.security.oauth2.jwt.Jwt;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface AccountService {
    public AccountResponse createAccount(AccountRequest account);
    public AccountResponse updateAccount(AccountRequest account, Long idAccount, Jwt jwt);
    public void deleteAccount(Long idAccount);
    public AccountResponse getAccountByIdForAdmin(Long idAccount);
    public AccountResponse getAccountForUser(Long idAccount , Jwt jwt) throws AccessDeniedException;
    public String findAccountByRegistrationId(String registrationId);
    public void debit(Long idAccount , Double amount) throws Exception;
    public void credit(Long idAccount , Double amount);
    public void refund(Long idAccount , Double amount);
    public String activateAccount(Long idAccount);
    public String deactivateAccount(Long idAccount);
    public List<AccountResponse> getAllAccountsForUser(Long idUser , Jwt jwt);
    public List<AccountResponse> getAllAccountsForAdmin();
    //public Boolean transaction(Long fromIdAccount, Long toIdAccount, Double amount);
    public String registrationId(Long idAccount);

}
