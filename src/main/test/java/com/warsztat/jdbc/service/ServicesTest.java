package com.warsztat.jdbc.service;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import com.warsztat.jdbc.model.Action;
import com.warsztat.jdbc.model.Customer;
import com.warsztat.jdbc.model.Order;
import com.warsztat.jdbc.service.CustomerService;
import com.warsztat.jdbc.service.ActionService;
import com.warsztat.jdbc.service.OrderService;

public class ServicesTest {
	
	CustomerService customerService = new CustomerService();
	ActionService actionService = new ActionService();
	OrderService orderService = new OrderService();
	
	private final static String C_NAME = "Jan";
	private final static long C_PESEL = 93021232394L;
	private final static String C_ADDRESS = "Gdańsk, Głowna 123";
	
	private final static String A_NAME = "Naprawa";
	private final static String A_DESC = "Naprawa skrzyni biegów.";
	private final static double A_PRICE = 1200;
	
	private final static int ORDER_ID = 3;
	private final static int O_CUSTOMER_ID = 3;
	private final static int O_ACTION_ID = 3;
	private final static Date ORDER_DATE = Date.valueOf( "2016-06-21" );
	private final static String O_DETAILS = "";
	
	@Test
	public void checkConnection(){
		assertNotNull(customerService.getConnection());
		assertNotNull(actionService.getConnection());
		assertNotNull(orderService.getConnection());
	}
	
	@Test
	public void checkAdding(){
		
		Customer customer = new Customer(O_CUSTOMER_ID, C_NAME, C_PESEL, C_ADDRESS);
		Action action = new Action(O_ACTION_ID, A_NAME, A_DESC, A_PRICE);
		Order order = new Order(ORDER_ID, O_CUSTOMER_ID, O_ACTION_ID, ORDER_DATE, O_DETAILS);
		
		orderService.deleteOrderAll();
		customerService.deleteCustomerAll();
		actionService.deleteActionAll();
		
		assertEquals(1,customerService.addCustomer(customer));
		assertEquals(1,actionService.addAction(action));
		assertEquals(1,orderService.addOrder(order));
		
		List<Customer> customers = customerService.getCustomer();
		List<Action> actions = actionService.getAction();
		List<Order> orders = orderService.getOrder();
		Customer customerRetrieved = customers.get(0);
		Action actionRetrieved = actions.get(0);
		Order orderRetrieved = orders.get(0);
		
		assertEquals(C_NAME, customerRetrieved.getName());
		assertEquals(C_PESEL, customerRetrieved.getPesel());
		assertEquals(C_ADDRESS, customerRetrieved.getAddress());
		
		assertEquals(A_DESC, actionRetrieved.getDesc());
		assertEquals(A_PRICE, actionRetrieved.getPrice(), 0.01);
		
		assertEquals(ORDER_ID, orderRetrieved.getOrder_id());		
		assertEquals(O_CUSTOMER_ID, orderRetrieved.getCustomer_id());
		assertEquals(O_ACTION_ID, orderRetrieved.getAction_id());
		assertEquals(ORDER_DATE, orderRetrieved.getOrder_date());
		assertEquals(O_DETAILS, orderRetrieved.getDetails());
		
		orderService.deleteOrderAll();
		customerService.deleteCustomerAll();
		actionService.deleteActionAll();
		
	}
	
	@Test
	public void checkDeleting(){
		Customer customer = new Customer(O_CUSTOMER_ID, C_NAME, C_PESEL, C_ADDRESS);
		Action action = new Action(O_ACTION_ID, A_NAME, A_DESC, A_PRICE);
		Order order = new Order(ORDER_ID, O_CUSTOMER_ID, O_ACTION_ID, ORDER_DATE, O_DETAILS);
		
		customerService.addCustomer(customer);
		actionService.addAction(action);
		orderService.addOrder(order);
		
		assertEquals(1,orderService.deleteOrder(order));
		assertEquals(1,customerService.deleteCustomer(customer));
		assertEquals(1,actionService.deleteAction(action));
		
		orderService.deleteOrderAll();
		customerService.deleteCustomerAll();
		actionService.deleteActionAll();
		
	}
	
	@Test
	public void checkUpdating(){
		Customer customer = new Customer(O_CUSTOMER_ID, C_NAME, C_PESEL, C_ADDRESS);
		Action action = new Action(O_ACTION_ID, A_NAME, A_DESC, A_PRICE);
		Order order = new Order(ORDER_ID, O_CUSTOMER_ID, O_ACTION_ID, ORDER_DATE, O_DETAILS);
		
		customerService.addCustomer(customer);
		actionService.addAction(action);
		orderService.addOrder(order);
		
		assertEquals(0,customerService.updateCustomer(customer));
		assertEquals(0,actionService.updateAction(action));
		assertEquals(0,orderService.updateOrder(order));

		
		orderService.deleteOrderAll();
		customerService.deleteCustomerAll();
		actionService.deleteActionAll();
	}

	//TESTING RELATIONS
	@Test
	public void checkTrbelongtoKl(){
		Customer customer = new Customer(O_CUSTOMER_ID, C_NAME, C_PESEL, C_ADDRESS);
		Action action = new Action(O_ACTION_ID, A_NAME, A_DESC, A_PRICE);
		Order order = new Order(ORDER_ID, O_CUSTOMER_ID, O_ACTION_ID, ORDER_DATE, O_DETAILS);
		
		customerService.addCustomer(customer);
		actionService.addAction(action);
		orderService.addOrder(order);
		
		List<Order> transakcje = new ArrayList<Order>();
		Order tran = new Order();
		transakcje = orderService.getOrder2();
		tran = transakcje.get(0);

		assertEquals(order.getOrder_id(), tran.getOrder_id());
		
		orderService.deleteOrderAll();
		customerService.deleteCustomerAll();
		actionService.deleteActionAll();
	}
	
}