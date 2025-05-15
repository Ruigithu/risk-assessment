// src/pages/HomePage.js
import React from 'react';
import { Typography, Card, CardContent, Grid, Button, Box } from '@mui/material';
import { Link } from 'react-router-dom';
import CreditScoreIcon from '@mui/icons-material/CreditScore';
import AccountBalanceIcon from '@mui/icons-material/AccountBalance';

const HomePage = () => {
    return (
        <Box sx={{ mt: 4 }}>
            <Typography variant="h4" component="h1" gutterBottom>
                AI-Driven Financial Risk Assessment
            </Typography>

            <Typography variant="body1" paragraph>
                Our intelligent system uses advanced AI to analyze and assess financial risks.
                Choose one of our assessment tools below to get started.
            </Typography>

            <Grid container spacing={4} sx={{ mt: 2 }}>
                <Grid item xs={12} md={6}>
                    <Card raised>
                        <CardContent sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', p: 3 }}>
                            <CreditScoreIcon sx={{ fontSize: 60, mb: 2, color: 'primary.main' }} />
                            <Typography variant="h5" component="h2" gutterBottom>
                                Credit Risk Assessment
                            </Typography>
                            <Typography variant="body2" color="text.secondary" sx={{ mb: 2 }}>
                                Evaluate personal or business credit risk with our AI-powered assessment tool.
                                Get instant risk scores and detailed analysis.
                            </Typography>
                            <Button
                                variant="contained"
                                component={Link}
                                to="/credit-assessment"
                                sx={{ mt: 2 }}
                            >
                                Start Credit Assessment
                            </Button>
                        </CardContent>
                    </Card>
                </Grid>

                <Grid item xs={12} md={6}>
                    <Card raised>
                        <CardContent sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', p: 3 }}>
                            <AccountBalanceIcon sx={{ fontSize: 60, mb: 2, color: 'primary.main' }} />
                            <Typography variant="h5" component="h2" gutterBottom>
                                Investment Portfolio Analysis
                            </Typography>
                            <Typography variant="body2" color="text.secondary" sx={{ mb: 2 }}>
                                Analyze your investment portfolio's risk profile with our AI algorithm.
                                Get insights on asset allocation and risk factors.
                            </Typography>
                            <Button
                                variant="contained"
                                component={Link}
                                to="/portfolio-analysis"
                                sx={{ mt: 2 }}
                            >
                                Analyze Portfolio
                            </Button>
                        </CardContent>
                    </Card>
                </Grid>
            </Grid>
        </Box>
    );
};

export default HomePage;