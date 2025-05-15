package com.ruipeng.risk_assessment.dto;

import java.util.List;

public class PortfolioRequest {
    private String name;
    private String ownerName;
    private List<AssetDto> assets;

    public PortfolioRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public List<AssetDto> getAssets() {
        return assets;
    }

    public void setAssets(List<AssetDto> assets) {
        this.assets = assets;
    }
}