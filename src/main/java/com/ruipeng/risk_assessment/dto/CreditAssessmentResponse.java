package com.ruipeng.risk_assessment.dto;

import java.util.List;

public class CreditAssessmentResponse {
    private Long id;
    private Double riskScore;
    private String riskLevel;
    private List<String> keyRiskFactors;

    public CreditAssessmentResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Double riskScore) {
        this.riskScore = riskScore;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public List<String> getKeyRiskFactors() {
        return keyRiskFactors;
    }

    public void setKeyRiskFactors(List<String> keyRiskFactors) {
        this.keyRiskFactors = keyRiskFactors;
    }
}
