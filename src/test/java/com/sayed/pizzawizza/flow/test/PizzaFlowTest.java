package com.sayed.pizzawizza.flow.test;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;
import org.springframework.webflow.config.FlowDefinitionResource;
import org.springframework.webflow.config.FlowDefinitionResourceFactory;
import org.springframework.webflow.test.MockExternalContext;
import org.springframework.webflow.test.MockFlowBuilderContext;
import org.springframework.webflow.test.execution.AbstractXmlFlowExecutionTests;

import com.sayed.pizzawizza.domain.Customer;
import com.sayed.pizzawizza.domain.Order;
import com.sayed.pizzawizza.domain.Pizza;
import com.sayed.pizzawizza.flow.PizzaFlowActions;
import com.sayed.pizzawizza.service.CustomerNotFoundException;

public class PizzaFlowTest extends AbstractXmlFlowExecutionTests {
  protected FlowDefinitionResource getResource(FlowDefinitionResourceFactory resourceFactory) {
    return resourceFactory.createResource("file:src/test/java/pizza-flow.xml");
  }
  
  @Test
  protected void configureFlowBuilderContext(MockFlowBuilderContext builderContext) {
        
    PizzaFlowActions pizzaFlowActions = mock(PizzaFlowActions.class);
    try {
      when(pizzaFlowActions.lookupCustomer("01552724553")).thenReturn(new Customer("01552724553"));
      when(pizzaFlowActions.lookupCustomer("5051231234")).thenThrow(new CustomerNotFoundException());
    } catch (CustomerNotFoundException e) {}
    
    builderContext.registerBean("pizzaFlowActions", pizzaFlowActions);    
  }

  @Test
  public void testStartPizzaFlow() {
    startFlow(new MockExternalContext());
    assertCurrentStateEquals("welcome");
  }
  
  @Test
  public void testKnownPhoneEnteredEventFromWelcomeState(){
    startFlow(new MockExternalContext());
    
    MockExternalContext context = new MockExternalContext();
    context.putRequestParameter("phoneNumber", "01552724553");
    context.setEventId("phoneEntered");
      
    setCurrentState("welcome");
    resumeFlow(context);
    assertCurrentStateEquals("showOrder");

    Order order = (Order) getFlowAttribute("order");
    assertEquals("01552724553", order.getCustomer().getPhoneNumber());
  }
  
  @Test
  public void testUnknownPhoneEnteredEventFromWelcomeState(){
    startFlow(new MockExternalContext());
    
    MockExternalContext context = new MockExternalContext();
    context.putRequestParameter("phoneNumber", "5051231234");
    context.setEventId("phoneEntered");
      
    setCurrentState("welcome");
    resumeFlow(context);
    assertCurrentStateEquals("registrationForm");
  }
  
  @Test
  public void testShouldTransitionFromShowOrderToCreatePizza(){
    startFlow(new MockExternalContext());
    
    MockExternalContext context = new MockExternalContext();
    context.setEventId("createPizza");
      
    setCurrentState("showOrder");
    resumeFlow(context);
    assertCurrentStateEquals("createPizza");
    assertNotNull(getFlowAttribute("pizza"));
  }
  
  @Test
  public void testShouldAddPizzaOnAddPizzaEvent(){
    startFlow(new MockExternalContext());
    setCurrentState("createPizza");
    getFlowScope().put("pizza", new Pizza());
    MockExternalContext context = new MockExternalContext();
    context.putRequestParameter("toppings", "TOMATO");
    context.setEventId("addPizza");
    resumeFlow(context);
    
    assertCurrentStateEquals("showOrder");
    Order order = (Order) getFlowAttribute("order");
    List<Pizza> pizzas = order.getPizzas();
    assertEquals(1, pizzas.size());
    assertEquals(0, pizzas.get(0).getToppings().size());
  }
  
  @Test
  public void testShouldNotAddPizzaOnCancelEvent() {
    startFlow(new MockExternalContext());
    setCurrentState("createPizza");
    MockExternalContext context = new MockExternalContext();
    context.setEventId("cancel");
    resumeFlow(context);
    
    assertCurrentStateEquals("showOrder");
    Order order = (Order) getFlowAttribute("order");
    assertEquals(0, order.getPizzas().size());
  } 
  
  
}
