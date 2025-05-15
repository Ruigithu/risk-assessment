package com.ruipeng.risk_assessment.service;


import com.ruipeng.risk_assessment.dto.AssetDto;
import com.ruipeng.risk_assessment.dto.PortfolioRequest;
import com.ruipeng.risk_assessment.dto.PortfolioResponse;
import com.ruipeng.risk_assessment.entity.Asset;
import com.ruipeng.risk_assessment.entity.Portfolio;
import com.ruipeng.risk_assessment.repo.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PortfolioService {

    private final AIService aiService;
    private final PortfolioRepository portfolioRepository;

    @Autowired
    public PortfolioService(AIService aiService, PortfolioRepository portfolioRepository) {
        this.aiService = aiService;
        this.portfolioRepository = portfolioRepository;
    }

    public PortfolioResponse analyzePortfolioRisk(PortfolioRequest request) {
        // 准备数据
        List<Map<String, Object>> assets = request.getAssets().stream()
                .map(asset -> {
                    Map<String, Object> assetMap = new HashMap<>();
                    assetMap.put("name", asset.getName());
                    assetMap.put("type", asset.getType());
                    assetMap.put("value", asset.getValue());
                    assetMap.put("volatility", asset.getHistoricalVolatility());
                    return assetMap;
                })
                .collect(Collectors.toList());

        Map<String, Object> portfolioData = new HashMap<>();
        portfolioData.put("assets", assets);

        // 调用AI服务
        Map<String, Object> aiResponse = aiService.analyzePortfolio(portfolioData);

        // 保存到数据库
        Portfolio portfolio = new Portfolio();
        portfolio.setName(request.getName());
        portfolio.setOwnerName(request.getOwnerName());
        portfolio.setRiskScore((Double) aiResponse.get("riskScore"));
        portfolio.setRiskLevel((String) aiResponse.get("riskLevel"));

        List<String> analysisPoints = (List<String>) aiResponse.get("analysisPoints");
        portfolio.setRiskAnalysis(String.join(", ", analysisPoints));

        // 保存资产
        List<Asset> assetEntities = request.getAssets().stream()
                .map(assetDto -> {
                    Asset asset = new Asset();
                    asset.setName(assetDto.getName());
                    asset.setType(assetDto.getType());
                    asset.setValue(assetDto.getValue());
                    asset.setHistoricalVolatility(assetDto.getHistoricalVolatility());
                    asset.setPortfolio(portfolio);
                    return asset;
                })
                .collect(Collectors.toList());

        portfolio.setAssets(assetEntities);
        Portfolio savedPortfolio = portfolioRepository.save(portfolio);

        // 准备响应
        PortfolioResponse response = new PortfolioResponse();
        response.setId(savedPortfolio.getId());
        response.setRiskScore(savedPortfolio.getRiskScore());
        response.setRiskLevel(savedPortfolio.getRiskLevel());
        response.setRiskAnalysisPoints(Arrays.asList(savedPortfolio.getRiskAnalysis().split(", ")));

        return response;
    }
}
