package com.bank.accounts.controller;


import com.bank.accounts.constants.AccountConstants;
import com.bank.accounts.dto.CustomerDto;
import com.bank.accounts.dto.ResponseDto;
import com.bank.accounts.service.IAccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@Validated
public class AccountsController {

    private final IAccountService accountService;

    public AccountsController(IAccountService accountService) {
        this.accountService = accountService;
    }


    @PostMapping
    public ResponseEntity<ResponseDto> createAccount(@Valid  @RequestBody CustomerDto customerDto) {
        accountService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));

    }

    @GetMapping
    public ResponseEntity<CustomerDto> fetchAccount(@RequestParam
                                                        @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
                                                        String mobile) {
        CustomerDto customerDto = accountService.fetchAccount(mobile);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDto);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateAccount(@RequestBody CustomerDto customerDto) {
        boolean isUpdate = accountService.updateAccount(customerDto);
        if(isUpdate){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        }
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDto(AccountConstants.STATUS_500, AccountConstants.MESSAGE_500));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam
                                                         @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
                                                         String mobile) {
        boolean isDelete = accountService.deleteAccount(mobile);
        if(isDelete){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        }
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDto(AccountConstants.STATUS_500, AccountConstants.MESSAGE_500));
    }
}
