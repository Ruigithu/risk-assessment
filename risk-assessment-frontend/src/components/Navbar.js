import React from 'react';
import { AppBar, Toolbar, Typography, Button, Box } from '@mui/material';
import { Link } from 'react-router-dom';
import AssessmentIcon from '@mui/icons-material/Assessment';

const Navbar = () => {
    return (
        <AppBar position="static">
            <Toolbar>
                <AssessmentIcon sx={{ mr: 2 }} />
                <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                    AI Risk Assessment
                </Typography>
                <Box>
                    <Button color="inherit" component={Link} to="/">
                        Home
                    </Button>
                    <Button color="inherit" component={Link} to="/credit-assessment">
                        Credit Assessment
                    </Button>
                    <Button color="inherit" component={Link} to="/portfolio-analysis">
                        Portfolio Analysis
                    </Button>
                </Box>
            </Toolbar>
        </AppBar>
    );
};

export default Navbar;