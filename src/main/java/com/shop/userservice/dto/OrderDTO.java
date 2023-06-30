package com.shop.userservice.dto;

import java.util.HashSet;
import java.util.Set;

import com.shop.userservice.model.Item;
import com.shop.userservice.model.OrderStatus;

public class OrderDTO 
{
	private long id;
	private String billingAddress;
	private double totalPrice;
	private OrderStatus status;
	private Set<Item> items = new HashSet<>();
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public Set<Item> getItems() {
		return items;
	}
	public void setItems(Set<Item> items) {
		this.items = items;
	}
	
	
	

}
