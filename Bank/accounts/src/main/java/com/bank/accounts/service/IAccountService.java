package com.bank.accounts.service;

import com.bank.accounts.dto.CustomerDto;

public interface IAccountService {


    /**
     *
     * @Param CustomerDto - Customer Details
     */
    void createAccount(CustomerDto customerDto);

    /**
     *
     * @Param String - Mobile Number
     */
    CustomerDto fetchAccount(String mobile);
}
