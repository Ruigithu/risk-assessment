import React, { useState } from 'react';
import {
    Typography, Box, Paper, TextField, Button, CircularProgress,
    Card, CardContent, Divider, Alert, Grid
} from '@mui/material';
import axios from 'axios';

const CreditAssessmentPage = () => {
    const [formData, setFormData] = useState({
        name: '',
        income: '',
        debt: '',
        creditHistory: '',
        age: ''
    });

    const [loading, setLoading] = useState(false);
    const [result, setResult] = useState(null);
    const [error, setError] = useState('');

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError('');

        try {
            // 转换数字字段
            const numericFormData = {
                ...formData,
                income: parseFloat(formData.income),
                debt: parseFloat(formData.debt),
                creditHistory: parseInt(formData.creditHistory),
                age: parseInt(formData.age)
            };

            const response = await axios.post(
                'http://localhost:8080/api/risk/credit-assessment',
                numericFormData
            );

            setResult(response.data);
        } catch (err) {
            console.error('Error:', err);
            setError('An error occurred during assessment. Please try again.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <Box sx={{ mt: 4 }}>
            <Typography variant="h4" component="h1" gutterBottom>
                Credit Risk Assessment
            </Typography>

            <Typography variant="body1" paragraph>
                Fill in the form below to get an AI-powered assessment of credit risk.
            </Typography>

            <Grid container spacing={4}>
                <Grid item xs={12} md={6}>
                    <Paper sx={{ p: 3 }}>
                        <form onSubmit={handleSubmit}>
                            <TextField
                                fullWidth
                                label="Name"
                                name="name"
                                value={formData.name}
                                onChange={handleChange}
                                margin="normal"
                                required
                            />

                            <TextField
                                fullWidth
                                label="Annual Income (€)"
                                name="income"
                                type="number"
                                value={formData.income}
                                onChange={handleChange}
                                margin="normal"
                                required
                            />

                            <TextField
                                fullWidth
                                label="Total Debt (€)"
                                name="debt"
                                type="number"
                                value={formData.debt}
                                onChange={handleChange}
                                margin="normal"
                                required
                            />

                            <TextField
                                fullWidth
                                label="Credit History (months)"
                                name="creditHistory"
                                type="number"
                                value={formData.creditHistory}
                                onChange={handleChange}
                                margin="normal"
                                required
                            />

                            <TextField
                                fullWidth
                                label="Age"
                                name="age"
                                type="number"
                                value={formData.age}
                                onChange={handleChange}
                                margin="normal"
                                required
                            />

                            <Button
                                type="submit"
                                variant="contained"
                                fullWidth
                                sx={{ mt: 3 }}
                                disabled={loading}
                            >
                                {loading ? <CircularProgress size={24} /> : 'Assess Risk'}
                            </Button>
                        </form>
                    </Paper>
                </Grid>

                <Grid item xs={12} md={6}>
                    {error && <Alert severity="error" sx={{ mb: 3 }}>{error}</Alert>}

                    {result && (
                        <Card>
                            <CardContent>
                                <Typography variant="h5" component="div" gutterBottom>
                                    Risk Assessment Results
                                </Typography>

                                <Divider sx={{ mb: 2 }} />

                                <Typography variant="body1" color="text.secondary" gutterBottom>
                                    Risk Score:
                                </Typography>
                                <Typography variant="h6" gutterBottom>
                                    {result.riskScore.toFixed(2)} / 1.00
                                </Typography>

                                <Typography variant="body1" color="text.secondary" gutterBottom>
                                    Risk Level:
                                </Typography>
                                <Typography
                                    variant="h6"
                                    color={
                                        result.riskLevel === 'Low' ? 'success.main' :
                                            result.riskLevel === 'Medium' ? 'warning.main' :
                                                'error.main'
                                    }
                                    gutterBottom
                                >
                                    {result.riskLevel}
                                </Typography>

                                <Typography variant="body1" color="text.secondary" gutterBottom sx={{ mt: 2 }}>
                                    Key Risk Factors:
                                </Typography>
                                <ul>
                                    {result.keyRiskFactors.map((factor, index) => (
                                        <li key={index}>
                                            <Typography variant="body1">{factor}</Typography>
                                        </li>
                                    ))}
                                </ul>
                            </CardContent>
                        </Card>
                    )}
                </Grid>
            </Grid>
        </Box>
    );
};

export default CreditAssessmentPage;