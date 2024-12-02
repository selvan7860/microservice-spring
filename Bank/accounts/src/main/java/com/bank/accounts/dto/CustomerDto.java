package com.bank.accounts.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDto {

    private String name;
    private String email;
    private String mobile;
    private AccountDto accountDto;
}
