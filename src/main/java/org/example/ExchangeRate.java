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