// src/pages/PortfolioAnalysisPage.js
import React, { useState } from 'react';
import {
    Typography, Box, Paper, TextField, Button, CircularProgress,
    Card, CardContent, Divider, Alert, Grid, IconButton,
    Table, TableBody, TableCell, TableContainer, TableHead, TableRow
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import AddIcon from '@mui/icons-material/Add';
import axios from 'axios';

// src/pages/PortfolioAnalysisPage.js (继续)
const PortfolioAnalysisPage = () => {
    const [portfolioData, setPortfolioData] = useState({
        name: '',
        ownerName: '',
        assets: []
    });

    const [newAsset, setNewAsset] = useState({
        name: '',
        type: '',
        value: '',
        historicalVolatility: ''
    });

    const [loading, setLoading] = useState(false);
    const [result, setResult] = useState(null);
    const [error, setError] = useState('');

    const handlePortfolioChange = (e) => {
        const { name, value } = e.target;
        setPortfolioData({
            ...portfolioData,
            [name]: value
        });
    };

    const handleAssetChange = (e) => {
        const { name, value } = e.target;
        setNewAsset({
            ...newAsset,
            [name]: value
        });
    };

    const addAsset = () => {
        if (newAsset.name && newAsset.type && newAsset.value) {
            setPortfolioData({
                ...portfolioData,
                assets: [...portfolioData.assets, {
                    ...newAsset,
                    value: parseFloat(newAsset.value),
                    historicalVolatility: parseFloat(newAsset.historicalVolatility)
                }]
            });

            // 重置新资产表单
            setNewAsset({
                name: '',
                type: '',
                value: '',
                historicalVolatility: ''
            });
        }
    };

    const removeAsset = (index) => {
        const updatedAssets = [...portfolioData.assets];
        updatedAssets.splice(index, 1);
        setPortfolioData({
            ...portfolioData,
            assets: updatedAssets
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        // 检查是否有至少一个资产
        if (portfolioData.assets.length === 0) {
            setError('Please add at least one asset to your portfolio.');
            return;
        }

        setLoading(true);
        setError('');

        try {
            const response = await axios.post(
                'http://localhost:8080/api/risk/portfolio-analysis',
                portfolioData
            );

            setResult(response.data);
        } catch (err) {
            console.error('Error:', err);
            setError('An error occurred during portfolio analysis. Please try again.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <Box sx={{ mt: 4 }}>
            <Typography variant="h4" component="h1" gutterBottom>
                Investment Portfolio Risk Analysis
            </Typography>

            <Typography variant="body1" paragraph>
                Add your portfolio assets below to analyze your investment risk.
            </Typography>

            <Grid container spacing={4}>
                <Grid item xs={12} md={6}>
                    <Paper sx={{ p: 3 }}>
                        <Typography variant="h6" gutterBottom>
                            Portfolio Details
                        </Typography>

                        <TextField
                            fullWidth
                            label="Portfolio Name"
                            name="name"
                            value={portfolioData.name}
                            onChange={handlePortfolioChange}
                            margin="normal"
                            required
                        />

                        <TextField
                            fullWidth
                            label="Owner Name"
                            name="ownerName"
                            value={portfolioData.ownerName}
                            onChange={handlePortfolioChange}
                            margin="normal"
                            required
                        />

                        <Divider sx={{ my: 3 }} />

                        <Typography variant="h6" gutterBottom>
                            Add Assets
                        </Typography>

                        <Grid container spacing={2}>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    fullWidth
                                    label="Asset Name"
                                    name="name"
                                    value={newAsset.name}
                                    onChange={handleAssetChange}
                                    margin="normal"
                                    size="small"
                                />
                            </Grid>

                            <Grid item xs={12} sm={6}>
                                <TextField
                                    fullWidth
                                    label="Asset Type"
                                    name="type"
                                    value={newAsset.type}
                                    onChange={handleAssetChange}
                                    margin="normal"
                                    size="small"
                                    placeholder="Stock, Bond, Real Estate..."
                                />
                            </Grid>

                            <Grid item xs={12} sm={6}>
                                <TextField
                                    fullWidth
                                    label="Value (€)"
                                    name="value"
                                    type="number"
                                    value={newAsset.value}
                                    onChange={handleAssetChange}
                                    margin="normal"
                                    size="small"
                                />
                            </Grid>

                            <Grid item xs={12} sm={6}>
                                <TextField
                                    fullWidth
                                    label="Historical Volatility (0-1)"
                                    name="historicalVolatility"
                                    type="number"
                                    value={newAsset.historicalVolatility}
                                    onChange={handleAssetChange}
                                    margin="normal"
                                    size="small"
                                    inputProps={{ min: 0, max: 1, step: 0.01 }}
                                />
                            </Grid>
                        </Grid>

                        <Button
                            variant="outlined"
                            startIcon={<AddIcon />}
                            onClick={addAsset}
                            sx={{ mt: 2 }}
                        >
                            Add Asset
                        </Button>

                        {portfolioData.assets.length > 0 && (
                            <Box sx={{ mt: 3 }}>
                                <Typography variant="h6" gutterBottom>
                                    Portfolio Assets
                                </Typography>

                                <TableContainer component={Paper} sx={{ mt: 2 }}>
                                    <Table size="small">
                                        <TableHead>
                                            <TableRow>
                                                <TableCell>Name</TableCell>
                                                <TableCell>Type</TableCell>
                                                <TableCell align="right">Value (€)</TableCell>
                                                <TableCell align="right">Volatility</TableCell>
                                                <TableCell align="center">Action</TableCell>
                                            </TableRow>
                                        </TableHead>
                                        <TableBody>
                                            {portfolioData.assets.map((asset, index) => (
                                                <TableRow key={index}>
                                                    <TableCell>{asset.name}</TableCell>
                                                    <TableCell>{asset.type}</TableCell>
                                                    <TableCell align="right">{asset.value.toLocaleString()}</TableCell>
                                                    <TableCell align="right">{asset.historicalVolatility}</TableCell>
                                                    <TableCell align="center">
                                                        <IconButton
                                                            size="small"
                                                            color="error"
                                                            onClick={() => removeAsset(index)}
                                                        >
                                                            <DeleteIcon />
                                                        </IconButton>
                                                    </TableCell>
                                                </TableRow>
                                            ))}
                                        </TableBody>
                                    </Table>
                                </TableContainer>
                            </Box>
                        )}

                        <Button
                            type="submit"
                            variant="contained"
                            fullWidth
                            sx={{ mt: 3 }}
                            onClick={handleSubmit}
                            disabled={loading}
                        >
                            {loading ? <CircularProgress size={24} /> : 'Analyze Portfolio Risk'}
                        </Button>
                    </Paper>
                </Grid>

                <Grid item xs={12} md={6}>
                    {error && <Alert severity="error" sx={{ mb: 3 }}>{error}</Alert>}

                    {result && (
                        <Card>
                            <CardContent>
                                <Typography variant="h5" component="div" gutterBottom>
                                    Portfolio Risk Analysis
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
                                    Risk Analysis:
                                </Typography>
                                <ul>
                                    {result.riskAnalysisPoints.map((point, index) => (
                                        <li key={index}>
                                            <Typography variant="body1">{point}</Typography>
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

export default PortfolioAnalysisPage;