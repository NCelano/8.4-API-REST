package com.aninfo.model;

import javax.persistence.*;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private Double sum;

    private Long accountCbu;

    public Transaction(){}

    public Transaction(String type, Double sum){
        this.type = type;
        this.sum = sum;
    }

    public Long getId(){
        return id;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Long getAccountCbu() {
        return accountCbu;
    }

    public void setAccountCbu(Long cbu) {
        this.accountCbu = cbu;
    }
}