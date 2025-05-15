package com.ruipeng.risk_assessment.service;


import com.ruipeng.risk_assessment.dto.CreditAssessmentRequest;
import com.ruipeng.risk_assessment.dto.CreditAssessmentResponse;
import com.ruipeng.risk_assessment.entity.CreditProfile;
import com.ruipeng.risk_assessment.repo.CreditProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CreditAssessmentService {

    private final AIService aiService;
    private final CreditProfileRepository creditProfileRepository;

    @Autowired
    public CreditAssessmentService(AIService aiService, CreditProfileRepository creditProfileRepository) {
        this.aiService = aiService;
        this.creditProfileRepository = creditProfileRepository;
    }

    // 修改CreditAssessmentService.java中的assessCreditRisk方法

    public CreditAssessmentResponse assessCreditRisk(CreditAssessmentRequest request) {
        // 准备数据
        Map<String, Object> creditData = new HashMap<>();
        creditData.put("income", request.getIncome());
        creditData.put("debt", request.getDebt());
        creditData.put("creditHistory", request.getCreditHistory());
        creditData.put("age", request.getAge());

        // 调用AI服务
        Map<String, Object> aiResponse = aiService.analyzeCredit(creditData);

        // 创建资料对象
        CreditProfile profile = new CreditProfile();
        profile.setName(request.getName());
        profile.setIncome(request.getIncome());
        profile.setDebt(request.getDebt());
        profile.setCreditHistory(request.getCreditHistory());
        profile.setAge(request.getAge());

        // 检查API响应是否包含错误
        if (aiResponse.containsKey("error")) {
            // 设置默认值
            profile.setRiskScore(0.5);
            profile.setRiskLevel("Medium (Default)");
            profile.setRiskFactors("API Error: " + aiResponse.get("error"));
        } else {
            // 正常处理API响应
            profile.setRiskScore((Double) aiResponse.get("riskScore"));
            profile.setRiskLevel((String) aiResponse.get("riskLevel"));

            // 安全地处理risk factors，防止NullPointerException
            if (aiResponse.containsKey("factors") && aiResponse.get("factors") != null) {
                List<String> factors = (List<String>) aiResponse.get("factors");
                profile.setRiskFactors(String.join(", ", factors));
            } else {
                profile.setRiskFactors("No specific risk factors identified");
            }
        }

        CreditProfile savedProfile = creditProfileRepository.save(profile);

        // 准备响应
        CreditAssessmentResponse response = new CreditAssessmentResponse();
        response.setId(savedProfile.getId());
        response.setRiskScore(savedProfile.getRiskScore());
        response.setRiskLevel(savedProfile.getRiskLevel());

        // 安全地处理响应中的risk factors
        if (savedProfile.getRiskFactors() != null) {
            response.setKeyRiskFactors(Arrays.asList(savedProfile.getRiskFactors().split(", ")));
        } else {
            response.setKeyRiskFactors(List.of("No specific risk factors identified"));
        }

        return response;
    }}
