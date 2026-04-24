package org.example.accountservice.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.accountservice.Models.UserResponseV2;
import org.example.accountservice.dtos.AccountRequest;
import org.example.accountservice.dtos.AccountResponse;
import org.example.accountservice.entities.Account;
import org.example.accountservice.mappers.AccountMapper;
import org.example.accountservice.repo.AccountRepository;
import org.example.accountservice.web.UserFeignClient;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AccountServiceImp implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final UserFeignClient userFeignClient;

     public AccountServiceImp(AccountRepository accountRepository,
                              AccountMapper accountMapper,
                              UserFeignClient userFeignClient) {
         this.accountRepository = accountRepository;
         this.accountMapper = accountMapper;
         this.userFeignClient = userFeignClient;
     }
    @Override
    public AccountResponse createAccount(AccountRequest account) {
        return accountMapper.toAccountResponse(accountMapper.toAccount(account),null);
    }

    @Override
    public AccountResponse updateAccount(AccountRequest account, Long idAccount, Jwt jwt) {
        UserResponseV2 user = userFeignClient.getFeignClientUser(account.getIdUser(),jwt);
        Account updated = accountRepository.findById(idAccount)
                .map(existing -> {
                    if(user.getKeycloakId().equals(jwt.getSubject())) {

                        if (account.getType() != null && !account.getType().isBlank()) {
                            existing.setType(account.getType());
                        }
                    } else {
                        System.out.println("You cannot Update Another Account !");
                        return null;
                    }
                    return accountRepository.save(existing);
                })
                .orElseThrow(() -> new EntityNotFoundException("Account Not Found with Id : " + idAccount));
        return accountMapper.toAccountResponse(updated,null);
    }

    @Override
    public void deleteAccount(Long idAccount) {
       accountRepository.deleteById(idAccount);
    }

    @Override
    public AccountResponse getAccountByIdForAdmin(Long idAccount) {
         Account acc = accountRepository.findById(idAccount).orElseThrow(
                 () -> new EntityNotFoundException("Account Not Found with Id : " + idAccount)
         );
        return accountMapper.toAccountResponse(acc,null);
    }

    @Override
    public AccountResponse getAccountForUser(Long idAccount, Jwt jwt) throws AccessDeniedException {
          Account acc = accountRepository.findById(idAccount).orElseThrow(
                  () -> new EntityNotFoundException("Account Not Found with Id : " + idAccount)
          );
          UserResponseV2 user = userFeignClient.getFeignClientUser(acc.getIdUser(),jwt);
          if(!user.getKeycloakId().equals(jwt.getSubject())) {
              throw new AccessDeniedException("Access Denied!");
              }
          else  acc.setUser(user);
        return accountMapper.toAccountResponse(acc,null);
    }

    @Override
    public String findAccountByRegistrationId(String registrationId) {
         Account acc = accountRepository.findByRegistrationId(registrationId).orElseThrow(
                 () -> new EntityNotFoundException("Account Not Found with Registration Id : " + registrationId));
         UserResponseV2 user = acc.getUser();
        return "Name :" + user.getFirstName() + " Lastname" + user.getLastName();
    }

    @Override
    public void debit(Long idAccount, Double amount) {
         Account acc = accountRepository.findById(idAccount).orElseThrow(
                 () -> new EntityNotFoundException("Account Not Found with Id : " + idAccount)
         );
         if(acc.getBalance() >= amount) {
             acc.setBalance(acc.getBalance() - amount);
         }
    }

    @Override
    public void credit(Long idAccount, Double amount) {
         Account acc = accountRepository.findById(idAccount).orElseThrow(
                 () -> new EntityNotFoundException("Account Not Found with Id : " + idAccount)
         );
         acc.setBalance(acc.getBalance()+amount);
    }

    @Override
    public void refund(Long idAccount, Double amount) {
         Account acc = accountRepository.findById(idAccount).orElseThrow(
                 () -> new EntityNotFoundException("Account Not Found with Id : " + idAccount)
         );
         acc.setBalance(acc.getBalance()+amount);
    }

    @Override
    public String deactivateAccount(Long idAccount) {
         Account acc = accountRepository.findById(idAccount).orElseThrow(
                 () -> new EntityNotFoundException("Account Not Found with Id : " + idAccount)
         );
         acc.setStatus("DEACTIVATED");
        return acc.getStatus();
    }

    @Override
    public String activateAccount(Long idAccount) {
         Account acc = accountRepository.findById(idAccount).orElseThrow(
                 () -> new EntityNotFoundException("Account Not Found with Id : " + idAccount)
         );
         acc.setStatus("ACTIVATED");
        return acc.getStatus() ;
    }

    @Override
    public List<AccountResponse> getAllAccountsForUser(Long idUser, Jwt jwt) {

        UserResponseV2 user = userFeignClient.getFeignClientUser(idUser, jwt);

        List<Account> accounts = accountRepository.findByIdUser(idUser);

        return accounts.stream()
                .map(acc -> accountMapper.toAccountResponse(acc, user))
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountResponse> getAllAccountsForAdmin() {

        List<UserResponseV2> users = userFeignClient.getAllFeignClientUsers();

        Map<Long, UserResponseV2> userMap = users.stream()
                .collect(Collectors.toMap(UserResponseV2::getIdUser, u -> u));

        List<Account> accounts = accountRepository.findAll();

        return accounts.stream()
                .map(account -> {
                    UserResponseV2 user = userMap.get(account.getIdUser());
                    return accountMapper.toAccountResponse(account,user);
                })
                .collect(Collectors.toList());
    }

}
