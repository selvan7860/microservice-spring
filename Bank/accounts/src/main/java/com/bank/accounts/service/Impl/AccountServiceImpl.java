package com.bank.accounts.service.Impl;


import com.bank.accounts.constants.AccountConstants;
import com.bank.accounts.dto.AccountDto;
import com.bank.accounts.dto.CustomerDto;
import com.bank.accounts.exception.CustomerAlreadyExistsException;
import com.bank.accounts.exception.ResourceNotFoundException;
import com.bank.accounts.mapper.AccountMapper;
import com.bank.accounts.mapper.CustomerMapper;
import com.bank.accounts.model.Account;
import com.bank.accounts.model.Customer;
import com.bank.accounts.repository.AccountRepository;
import com.bank.accounts.repository.CustomerRepository;
import com.bank.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private static final String CUSTOMER_ALREADY_EXISTS = "Customer already exists";

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;


    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobile(customerDto.getMobile());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException(CUSTOMER_ALREADY_EXISTS);
        }
        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createAccount(savedCustomer));
    }

    @Override
    public CustomerDto fetchAccount(String mobile) {
        Customer customer =customerRepository
                .findByMobile(mobile)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "Mobile", mobile));
        Account account = accountRepository.
                findByCustomerID(customer.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "CustomerID", customer.getId().toString()));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountDto(AccountMapper.mapToAccountDto(account, new AccountDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdate = false;
        AccountDto accountDto = customerDto.getAccountDto();
        if(accountDto != null){
            Account account = accountRepository.findByAccountNumber(accountDto.getAccountNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Account", "AccountNumber", accountDto.getAccountNumber()));
            AccountMapper.mapToAccount(accountDto, account);
            account = accountRepository.save(account);

            String customerID = account.getCustomerID();
            System.out.println(customerID);
            Customer customer = customerRepository.findById(customerID)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", "ID", customerID));
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdate = true;
        }
        return isUpdate;
    }

    @Override
    public boolean deleteAccount(String mobile) {
        Customer customer = customerRepository.findByMobile(mobile).orElseThrow(() -> new ResourceNotFoundException("Customer", "Mobile", mobile));
        accountRepository.deleteByCustomerID(customer.getId());
        customerRepository.deleteById(customer.getId());
        return true;
    }

    private Account createAccount(Customer customer) {
        Account account = new Account();
        account.setCustomerID(customer.getId());
        String randomNumber = "ACC" + (int) (Math.random() * 10000);
        account.setAccountNumber(randomNumber);
        account.setAccountType(AccountConstants.SAVINGS);
        account.setBranchAddress(AccountConstants.ADDRESS);
        return account;
    }
}
