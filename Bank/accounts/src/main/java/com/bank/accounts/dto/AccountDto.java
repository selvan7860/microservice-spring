package com.bank.accounts.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDto {

    private String accountNumber;
    private String accountName;
    private String accountType;
    private String branchAddress;
}
