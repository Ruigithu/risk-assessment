package com.ruipeng.risk_assessment.entity;

import jakarta.persistence.*;

@Entity
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type; // 股票/债券/房地产等
    private Double value;
    private Double historicalVolatility;

    @ManyToOne
    private Portfolio portfolio;

    public Asset() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getHistoricalVolatility() {
        return historicalVolatility;
    }

    public void setHistoricalVolatility(Double historicalVolatility) {
        this.historicalVolatility = historicalVolatility;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
}
