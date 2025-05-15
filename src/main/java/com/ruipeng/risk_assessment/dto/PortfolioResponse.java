package com.ruipeng.risk_assessment.dto;

import java.util.List;

public class PortfolioResponse {
    private Long id;
    private Double riskScore;
    private String riskLevel;
    private List<String> riskAnalysisPoints;

    public PortfolioResponse() {
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

    public List<String> getRiskAnalysisPoints() {
        return riskAnalysisPoints;
    }

    public void setRiskAnalysisPoints(List<String> riskAnalysisPoints) {
        this.riskAnalysisPoints = riskAnalysisPoints;
    }
}
