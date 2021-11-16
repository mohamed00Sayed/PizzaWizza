package com.sayed.pizzawizza.flow;

import static com.sayed.pizzawizza.domain.PaymentType.*;
import static org.apache.log4j.Logger.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sayed.pizzawizza.domain.CashOrCheckPayment;
import com.sayed.pizzawizza.domain.CreditCardPayment;
import com.sayed.pizzawizza.domain.Customer;
import com.sayed.pizzawizza.domain.Order;
import com.sayed.pizzawizza.domain.Payment;
import com.sayed.pizzawizza.domain.PaymentDetails;
import com.sayed.pizzawizza.service.CustomerNotFoundException;
import com.sayed.pizzawizza.service.CustomerService;

@Component
public class PizzaFlowActions {
  private static final Logger LOGGER = getLogger(PizzaFlowActions.class);
  
   public Customer lookupCustomer(String phoneNumber) 
         throws CustomerNotFoundException {     
      Customer customer = customerService.lookupCustomer(phoneNumber);
      if(customer != null) {        
        return customer;
      } else {
        throw new CustomerNotFoundException();
      }
   }
   
   public void addCustomer(Customer customer) {
      LOGGER.warn("TODO: Flesh out the addCustomer() method.");
   }

   public Payment verifyPayment(PaymentDetails paymentDetails) {
     Payment payment = null;
     if(paymentDetails.getPaymentType() == CREDIT_CARD) {
       payment = new CreditCardPayment();
     } else {
       payment = new CashOrCheckPayment();
     }
     
     return payment;
   }
   
   public void saveOrder(Order order) {
      LOGGER.warn("TODO: Flesh out the saveOrder() method.");
   }
   
   public boolean checkDeliveryArea(String zipCode) {
     LOGGER.warn("TODO: Flesh out the checkDeliveryArea() method.");
     return "75075".equals(zipCode);
   }

   @Autowired
   CustomerService customerService;
}
