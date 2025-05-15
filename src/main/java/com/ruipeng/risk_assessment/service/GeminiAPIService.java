package com.ruipeng.risk_assessment.service;


import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Primary
public class GeminiAPIService extends AIService {


    private final ChatLanguageModel geminiModel;

    public GeminiAPIService(RestTemplate restTemplate,
                            @Value("${gemini.api.key}") String apiKey) {
        super(restTemplate);
        this.geminiModel = GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .temperature(0.3)
                .modelName("gemini-1.5-flash") // 必须指定模型名称
                .build();
    }

    @Override
    public Map<String, Object> analyzeCredit(Map<String, Object> creditData) {
        System.out.println("使用Gemini API分析信用风险...");

        String prompt = "Analyze the credit risk for a person with the following attributes: " +
                "Income: " + creditData.get("income") + ", " +
                "Debt: " + creditData.get("debt") + ", " +
                "Credit History (months): " + creditData.get("creditHistory") + ", " +
                "Age: " + creditData.get("age") + ". " +
                "Provide a risk score between 0 and 1 (where 0 is lowest risk and 1 is highest risk), " +
                "a risk level (Low, Medium, or High), " +
                "and list 3 key risk factors. Respond in JSON format with the keys 'riskScore', 'riskLevel', and 'factors'.";

        try {
            String response = geminiModel.generate(prompt);
            return parseJsonResponse(response);
        } catch (Exception e) {
            System.out.println("Gemini API调用失败: " + e.getMessage());
            // 在API调用失败时回退到模拟数据
            return createFallbackCreditResponse(creditData);
        }
    }

    @Override
    public Map<String, Object> analyzePortfolio(Map<String, Object> portfolioData) {
        System.out.println("使用Gemini API分析投资组合...");

        String prompt = "Analyze the investment portfolio risk with the following assets: " +
                portfolioData.toString() + ". " +
                "Provide a risk score between 0 and 1 (where 0 is lowest risk and 1 is highest risk), " +
                "a risk level (Low, Medium, or High), " +
                "and provide 3 key risk analysis points. Respond in JSON format with the keys 'riskScore', 'riskLevel', and 'analysisPoints'.";

        try {
            String response = geminiModel.generate(prompt);
            return parseJsonResponse(response);
        } catch (Exception e) {
            System.out.println("Gemini API调用失败: " + e.getMessage());
            // 在API调用失败时回退到模拟数据
            return createFallbackPortfolioResponse(portfolioData);
        }
    }

    private Map<String, Object> parseJsonResponse(String jsonString) {
        // 简单的JSON提取逻辑，实际应用中应使用Jackson或Gson等库
        Map<String, Object> result = new HashMap<>();

        // 提取风险分数
        Pattern scorePattern = Pattern.compile("\"riskScore\"\\s*:\\s*([0-9.]+)");
        Matcher scoreMatcher = scorePattern.matcher(jsonString);
        if (scoreMatcher.find()) {
            result.put("riskScore", Double.parseDouble(scoreMatcher.group(1)));
        } else {
            result.put("riskScore", 0.5); // 默认值
        }

        // 提取风险级别
        Pattern levelPattern = Pattern.compile("\"riskLevel\"\\s*:\\s*\"([A-Za-z]+)\"");
        Matcher levelMatcher = levelPattern.matcher(jsonString);
        if (levelMatcher.find()) {
            result.put("riskLevel", levelMatcher.group(1));
        } else {
            result.put("riskLevel", "Medium"); // 默认值
        }

        // 提取风险因素或分析点
        Pattern factorsPattern = Pattern.compile("\"(?:factors|analysisPoints)\"\\s*:\\s*\\[(.*?)\\]");
        Matcher factorsMatcher = factorsPattern.matcher(jsonString);
        if (factorsMatcher.find()) {
            String factorsStr = factorsMatcher.group(1);
            List<String> factorsList = new ArrayList<>();

            // 匹配JSON数组中的字符串
            Pattern itemPattern = Pattern.compile("\"(.*?)\"");
            Matcher itemMatcher = itemPattern.matcher(factorsStr);
            while (itemMatcher.find()) {
                factorsList.add(itemMatcher.group(1));
            }

            if (jsonString.contains("\"factors\"")) {
                result.put("factors", factorsList);
            } else {
                result.put("analysisPoints", factorsList);
            }
        } else {
            // 默认值
            if (jsonString.contains("credit")) {
                result.put("factors", List.of("Income to debt ratio", "Credit history length", "Age factor"));
            } else {
                result.put("analysisPoints", List.of("Portfolio diversification", "Market volatility", "Asset allocation"));
            }
        }

        return result;
    }

    private Map<String, Object> createFallbackCreditResponse(Map<String, Object> creditData) {
        // 创建模拟响应，逻辑与MockAIService相同
        Map<String, Object> mockResponse = new HashMap<>();
        Double income = (Double) creditData.get("income");
        Double debt = (Double) creditData.get("debt");

        // 简单的风险评估逻辑
        double debtToIncome = debt / (income > 0 ? income : 1);
        double riskScore = Math.min(0.9, Math.max(0.1, debtToIncome * 0.5));

        String riskLevel;
        if (riskScore < 0.3) riskLevel = "Low";
        else if (riskScore < 0.7) riskLevel = "Medium";
        else riskLevel = "High";

        mockResponse.put("riskScore", riskScore);
        mockResponse.put("riskLevel", riskLevel);
        mockResponse.put("factors", List.of(
                "Debt-to-income ratio: " + String.format("%.2f", debtToIncome),
                "Credit history length: " + creditData.get("creditHistory") + " months",
                "Age factor: " + creditData.get("age") + " years"
        ));

        return mockResponse;
    }

    private Map<String, Object> createFallbackPortfolioResponse(Map<String, Object> portfolioData) {
        // 创建模拟响应，逻辑与MockAIService相同
        Map<String, Object> mockResponse = new HashMap<>();
        List<Map<String, Object>> assets = (List<Map<String, Object>>) portfolioData.get("assets");

        double totalValue = 0;
        for (Map<String, Object> asset : assets) {
            totalValue += (Double) asset.get("value");
        }

        double weightedVolatility = 0;
        for (Map<String, Object> asset : assets) {
            double value = (Double) asset.get("value");
            Double volatility = 0.5;
            if (asset.containsKey("volatility")) {
                volatility = (Double) asset.get("volatility");
            } else if (asset.containsKey("historicalVolatility")) {
                volatility = (Double) asset.get("historicalVolatility");
            }
            weightedVolatility += (value / totalValue) * volatility;
        }

        String riskLevel;
        if (weightedVolatility < 0.3) riskLevel = "Low";
        else if (weightedVolatility < 0.6) riskLevel = "Medium";
        else riskLevel = "High";

        mockResponse.put("riskScore", weightedVolatility);
        mockResponse.put("riskLevel", riskLevel);
        mockResponse.put("analysisPoints", List.of(
                "Portfolio diversification level: " + (assets.size() > 3 ? "Good" : "Limited"),
                "Weighted average volatility: " + String.format("%.2f", weightedVolatility),
                "Consider rebalancing to reduce risk exposure"
        ));

        return mockResponse;
    }

}
