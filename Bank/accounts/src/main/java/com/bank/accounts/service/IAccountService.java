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


    /**
     * Update existing customer account.
     *
     * @param customerDto - Customer details. Account number is required.
     * @return true if account is updated, false otherwise.
     */
    boolean updateAccount(CustomerDto customerDto);

    /**
     * Delete existing customer account.
     *
     * @param mobile - Mobile number of customer.
     * @return true if account is deleted, false otherwise.
     */
    boolean deleteAccount(String mobile);
}
