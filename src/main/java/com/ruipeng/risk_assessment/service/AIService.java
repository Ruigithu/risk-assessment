package com.ruipeng.risk_assessment.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 抽象AI服务类，定义了风险评估服务的基本接口
 */
@Service
public abstract class AIService {

    protected final RestTemplate restTemplate;

    public AIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 分析信用风险
     * @param creditData 信用数据
     * @return 包含风险评分、风险级别和风险因素的结果
     */
    public abstract Map<String, Object> analyzeCredit(Map<String, Object> creditData);

    /**
     * 分析投资组合风险
     * @param portfolioData 投资组合数据
     * @return 包含风险评分、风险级别和风险分析的结果
     */
    public abstract Map<String, Object> analyzePortfolio(Map<String, Object> portfolioData);

}