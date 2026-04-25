package com.example.order.model;

public class Order {
    private Long id;
    private String orderNo;
    private User user;
    private Double amount;

    public Order() {
    }

    public Order(Long id, String orderNo, User user, Double amount) {
        this.id = id;
        this.orderNo = orderNo;
        this.user = user;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
