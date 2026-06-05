package org.example.accountservice.web;

import org.example.accountservice.dtos.AccountRequest;
import org.example.accountservice.dtos.AccountResponse;
import org.example.accountservice.services.AccountService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    @GetMapping("/admin/{id}")
    public AccountResponse getAccountForAdmin(@PathVariable Long id){
        return accountService.getAccountByIdForAdmin(id) ;
    }

    @GetMapping("/user/account/{idAccount}")
    public AccountResponse getAccountForUser(@PathVariable Long idAccount, @AuthenticationPrincipal Jwt jwt) throws AccessDeniedException {
        return accountService.getAccountForUser(idAccount,jwt);
    }

    @GetMapping("/admin")
    public List<AccountResponse> getAllAccountsForAdmin(){
        return accountService.getAllAccountsForAdmin();
    }

    @GetMapping("/user/{idUser}")
    public List<AccountResponse> getAllAccountsForUser(@PathVariable Long idUser ,@AuthenticationPrincipal Jwt jwt){
        return accountService.getAllAccountsForUser(idUser,jwt);
    }

    @PostMapping("/create")
    public AccountResponse createAccount(@RequestBody @jakarta.validation.Valid AccountRequest accountRequest){
        return accountService.createAccount(accountRequest);
    }

    @PutMapping("/update/{idAccount}")
    public  AccountResponse updateAccount(@PathVariable Long idAccount, @RequestBody @jakarta.validation.Valid AccountRequest accountRequest,@AuthenticationPrincipal Jwt jwt){
        return accountService.updateAccount(accountRequest,idAccount,jwt);
    }

    @DeleteMapping("/delete/{idAccount}")
    public void deleteAccount(@PathVariable Long  idAccount){
        accountService.deleteAccount(idAccount);
    }

    @PatchMapping("/deactivate/{idAccount}")
    public String  deactivateAccount(@PathVariable Long idAccount){
        return accountService.deactivateAccount(idAccount);
    }

    @PatchMapping("/activate/{idAccount}")
    public String  activateAccount(@PathVariable Long idAccount){
        return accountService.activateAccount(idAccount);
    }

    @GetMapping("/registration/{regId}")
    public String getAccountByRegId(@PathVariable String regId){
        return accountService.findAccountByRegistrationId(regId);
    }

    @GetMapping("/registrationId/{idAccount}")
    public String getRegistrationId(@PathVariable Long idAccount){
        return accountService.registrationId(idAccount);
    }




}
