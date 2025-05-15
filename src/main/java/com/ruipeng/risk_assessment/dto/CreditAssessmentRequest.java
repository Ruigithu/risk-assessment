package com.ruipeng.risk_assessment.dto;

public class CreditAssessmentRequest {
    private String name;
    private Double income;
    private Double debt;
    private Integer creditHistory;
    private Integer age;

    public CreditAssessmentRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getDebt() {
        return debt;
    }

    public void setDebt(Double debt) {
        this.debt = debt;
    }

    public Integer getCreditHistory() {
        return creditHistory;
    }

    public void setCreditHistory(Integer creditHistory) {
        this.creditHistory = creditHistory;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
