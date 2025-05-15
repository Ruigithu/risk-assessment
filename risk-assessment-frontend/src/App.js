// src/App.js
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Container, CssBaseline, ThemeProvider, createTheme } from '@mui/material';
import Navbar from './components/Navbar';
import HomePage from './pages/HomePage';
import CreditAssessmentPage from './pages/CreditAssessmentPage';
import PortfolioAnalysisPage from './pages/PortfolioAnalysisPage';

const theme = createTheme({
  palette: {
    primary: {
      main: '#1976d2',
    },
    secondary: {
      main: '#dc004e',
    },
  },
});

function App() {
  return (
      <ThemeProvider theme={theme}>
        <CssBaseline />
        <Router>
          <Navbar />
          <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
            <Routes>
              <Route path="/" element={<HomePage />} />
              <Route path="/credit-assessment" element={<CreditAssessmentPage />} />
              <Route path="/portfolio-analysis" element={<PortfolioAnalysisPage />} />
            </Routes>
          </Container>
        </Router>
      </ThemeProvider>
  );
}

export default App;