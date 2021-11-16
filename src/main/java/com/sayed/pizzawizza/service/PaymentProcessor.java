package com.sayed.pizzawizza.service;

import com.sayed.pizzawizza.PaymentException;

/**
 * Simple payment processor implementation.
 * @author Mohammad Sayed
 */
public class PaymentProcessor {
   public PaymentProcessor() {}

   public void approveCreditCard(String creditCardNumber, String expMonth,
                     String expYear, float amount) throws PaymentException {
      if (amount > 20.00) {
         throw new PaymentException();
      }
   }
}
