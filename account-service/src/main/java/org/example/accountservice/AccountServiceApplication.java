package org.example.accountservice;

import org.example.accountservice.dtos.AccountRequest;
import org.example.accountservice.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableFeignClients
@EnableKafka
@EnableDiscoveryClient
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

    // @Bean
    // CommandLineRunner commandLineRunner(AccountService accountService) {
    //     return args -> {
    //         if (accountService.getAllAccountsForAdmin().isEmpty()) {
    //             accountService.createAccount(new AccountRequest(1L, "SAVINGS"));
    //             accountService.createAccount(new AccountRequest(2L, "CURRENT"));
    //             accountService.createAccount(new AccountRequest(3L, "SAVINGS"));
    //             accountService.createAccount(new AccountRequest(4L, "CURRENT"));
    //         }
    //     };
    // }

}
