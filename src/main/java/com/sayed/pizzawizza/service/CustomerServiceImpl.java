package com.sayed.pizzawizza.service;

import com.sayed.pizzawizza.domain.Customer;

public class CustomerServiceImpl implements CustomerService {
  public CustomerServiceImpl() {}
  
  public Customer lookupCustomer(String phoneNumber) throws CustomerNotFoundException {
    if("01552724553".equals(phoneNumber)) {
      Customer customer = new Customer();
      customer.setId(123);
      customer.setName("Mohammad Sayed");
      customer.setAddress("3700 OmarMakram Mq");
      customer.setCity("Assiut");
      customer.setState("AS");
      customer.setZipCode("76210");
      customer.setPhoneNumber(phoneNumber);
      return customer;
    } else {
      throw new CustomerNotFoundException();
    }
  }
}
