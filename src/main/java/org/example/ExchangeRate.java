package org.example;

import javax.persistence.*;

@Entity
public class ExchangeRate {
    @Id
    @GeneratedValue
    private Integer id;
    private String baseCurrency;
    private String currency;
    private Float saleRateNB;
    private Float purchaseRateNB;
    private Float saleRate;
    private Float purchaseRate;

    @ManyToOne
    @JoinColumn(name= "result_id")
    private Result result;

    public ExchangeRate() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Float getSaleRateNB() {
        return saleRateNB;
    }

    public void setSaleRateNB(Float saleRateNB) {
        this.saleRateNB = saleRateNB;
    }

    public Float getPurchaseRateNB() {
        return purchaseRateNB;
    }

    public void setPurchaseRateNB(Float purchaseRateNB) {
        this.purchaseRateNB = purchaseRateNB;
    }

    public Float getSaleRate() {
        return saleRate;
    }

    public void setSaleRate(Float saleRate) {
        this.saleRate = saleRate;
    }

    public Float getPurchaseRate() {
        return purchaseRate;
    }

    public void setPurchaseRate(Float purchaseRate) {
        this.purchaseRate = purchaseRate;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "id=" + id +
                ", baseCurrency='" + baseCurrency + '\'' +
                ", currency='" + currency + '\'' +
                ", saleRateNB=" + saleRateNB +
                ", purchaseRateNB=" + purchaseRateNB +
                ", saleRate=" + saleRate +
                ", purchaseRate=" + purchaseRate +
                ", result=" + result +
                '}';
    }
}