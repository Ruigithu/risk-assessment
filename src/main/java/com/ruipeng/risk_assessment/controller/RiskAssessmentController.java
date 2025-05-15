package com.ruipeng.risk_assessment.controller;


import com.ruipeng.risk_assessment.dto.CreditAssessmentRequest;
import com.ruipeng.risk_assessment.dto.CreditAssessmentResponse;
import com.ruipeng.risk_assessment.dto.PortfolioRequest;
import com.ruipeng.risk_assessment.dto.PortfolioResponse;
import com.ruipeng.risk_assessment.service.AIService;
import com.ruipeng.risk_assessment.service.CreditAssessmentService;
import com.ruipeng.risk_assessment.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/risk")
@CrossOrigin(origins = "http://localhost:3001") // 允许前端访问
public class RiskAssessmentController {
    @Autowired
    private final CreditAssessmentService creditAssessmentService;

    @Autowired
    private final PortfolioService portfolioService;

    public RiskAssessmentController(CreditAssessmentService creditAssessmentService,
                                    PortfolioService portfolioService) {
        this.creditAssessmentService = creditAssessmentService;
        this.portfolioService = portfolioService;
    }

    @PostMapping("/credit-assessment")
    public ResponseEntity<CreditAssessmentResponse> assessCreditRisk(
            @RequestBody CreditAssessmentRequest request) {
        return ResponseEntity.ok(creditAssessmentService.assessCreditRisk(request));
    }

    @PostMapping("/portfolio-analysis")
    public ResponseEntity<PortfolioResponse> analyzePortfolioRisk(
            @RequestBody PortfolioRequest request) {
        return ResponseEntity.ok(portfolioService.analyzePortfolioRisk(request));
    }

}
