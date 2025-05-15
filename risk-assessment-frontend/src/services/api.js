import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = {
    // 信用风险评估
    assessCreditRisk: async (data) => {
        return axios.post(`${API_BASE_URL}/risk/credit-assessment`, data);
    },

    // 投资组合风险分析
    analyzePortfolioRisk: async (data) => {
        return axios.post(`${API_BASE_URL}/risk/portfolio-analysis`, data);
    }
};

export default api;