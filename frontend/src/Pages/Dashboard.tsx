import React from 'react';
import GridStoreList from '../Components/GridStoreList/GridStoreList';
import GridAlarmStatus from '../Components/GridAlarmStatus/GridAlarmStatus';

const Dashboard : React.FC = () => {

    return(
        <>
        <GridStoreList/>
        <div style={{marginBottom:'20px'}}></div>
        <GridAlarmStatus/>
        </>
    )
}

export default Dashboard;

