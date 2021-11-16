package com.sayed.pizzawizza.service;

import com.sayed.pizzawizza.domain.Order;

public interface PricingEngine {
  public float calculateOrderTotal(Order order);
}
