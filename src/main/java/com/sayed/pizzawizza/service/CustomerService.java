package com.sayed.pizzawizza.service;
import com.sayed.pizzawizza.domain.Customer;

public interface CustomerService {
   Customer lookupCustomer(String phoneNumber) throws CustomerNotFoundException;
}