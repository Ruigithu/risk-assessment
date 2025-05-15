package com.ruipeng.risk_assessment.dto;

public class AssetDto {
    private String name;
    private String type;
    private Double value;
    private Double historicalVolatility;

    public AssetDto() {
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
}
