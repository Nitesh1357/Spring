package com.nkm.bankapp.controller;

import com.nkm.bankapp.entity.Account;
import com.nkm.bankapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController{
    @Autowired
    AccountService service;
//    create the Account
    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody Account account){
       Account createAccount = service.createAccount(account);

    return ResponseEntity.status(HttpStatus.CREATED).body(createAccount);
    }

    @GetMapping("/{accountNumber}")
    public Account getAccountByAccountNumber(@PathVariable Long accountNumber){
        Account account =service.getAccountDetailsByAccountNumber(accountNumber);
    return account;
    }
    @GetMapping("/getallaccounts")
  public List<Account> getAllAccountDetails() {
    List<Account> allAccountDetails = service.getAllAccountDetails();
    return allAccountDetails;

    }

    @PutMapping("/withdraw/{accountNumber}/{amount}")
    public Account withdrawAccount(@PathVariable Long accountNumber, @PathVariable Double amount){
        Account account = service.withdrawAmount(accountNumber,amount);
        return account;
    }

    @PutMapping("/deposit/{accountNumber}/{amount}")
    public Account depositAccount(@PathVariable Long accountNumber,@PathVariable Double amount){
       Account account = service.depositAmount(accountNumber, amount);
        return account;
    }
    @DeleteMapping("/delete/{accountNumber}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long accountNumber){
        service.closeAccount(accountNumber);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Account closed");
    }


}
