import React from 'react';
import {AppBar, Toolbar, Typography, Container, Box} from '@mui/material';
import {Route, Link, Routes} from "react-router-dom";
import GridStoreList from '../GridStoreList/GridStoreList';
import GridAlarmStatus from '../GridAlarmStatus/GridAlarmStatus';

const Header: React.FC = () => {

    return(
        <>
        <AppBar position="static" sx={{flexGrow: 1,color: 'white', background: '#4B4E5E', marginBottom: '10px', alignContent: 'center', alignItems: 'center'}}>
            {/* <Typography
            variant="h6"
            noWrap
            component="a"
            href="/"
            sx={{
              mr: 2,
              ml: 2,
            //   mt: 2,
              display: { xs: 'none', md: 'flex' },
              fontWeight: 700,
              letterSpacing: '.1rem',
              color: 'red',
              textDecoration: 'none',
              fontSize: 25,
              marginLeft: '800px',
              alignContent: 'center',
              alignItems: 'center',
              textAlign: 'center'
            }}
          >
            8Seconds 매장 모니터링
          </Typography> */}
          <Box sx={{display: 'flex',flexDirection: 'row', m: '7px'}}>
            <Typography variant='h4' sx={{color: 'red', marginRight: '10px'}}>8Seconds</Typography>
            <Typography variant='h4'>Store Monitoring</Typography>
          </Box>
       



                {/* <Toolbar sx={{color: 'white'}}>
                    <Typography variant='h6' sx={{color: 'white', marginRight: '20px'}}>
                        <Link to="/storeinfo" style={{color: 'white', textDecoration: 'none'}}>STORE</Link>
                    </Typography>
                    <Typography variant='h6'>
                        <Link to="/alarmstatus" style={{color: 'white', textDecoration: 'none'}}>ALARM</Link>
                    </Typography>
                </Toolbar> */}
        </AppBar>
        {/* <Routes>
            <Route path="/" element={<GridStoreList/>}/>
            <Route path="/storeinfo" element={<GridStoreList/>}/>
            <Route path="/alarmstatus" element={<GridAlarmStatus/>}/>
        </Routes> */}
        </>
        
    )
};

export default Header;