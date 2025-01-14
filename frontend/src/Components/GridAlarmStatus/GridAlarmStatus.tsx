import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {AlarmStatus} from '../../Models/AlarmStatus';
import Box from '@mui/material/Box';
import {DataGrid, GridColDef, GridCellParams,
    GridToolbarContainer,
    GridToolbarColumnsButton,
    GridToolbarFilterButton,
    GridToolbarExport,
    GridToolbarDensitySelector,} from '@mui/x-data-grid';
import CircularProgress from '@mui/material/CircularProgress';
import clsx from 'clsx';

const GridAlarmStatus: React.FC = () => {

    const [alarmStatusList, setAlarmStatusList] = useState<AlarmStatus[]>([]);

    // const customGridToolbar = () => {
    //     return (
    //          <GridToolbarContainer style={{color: 'black'}}>
    //            <GridToolbarColumnsButton /> 
    //            <GridToolbarFilterButton  style={{color: 'black'}}/>
    //            <GridToolbarDensitySelector style={{color: 'black'}}/> 
    //            <GridToolbarExport style={{color: 'black'}}/>
    //          </GridToolbarContainer>
    //       );
    // }

    const columns: GridColDef[] = [
        // { field: "id", headerName: "ID", width: 90 },
        {   
            field: 'alarmId',
            headerName: '알람 아이디',
            width: 100,
            editable: false,
            headerAlign: 'center',
            align: 'center',
            headerClassName: 'grid-header',
            hideable: true,
        },
        {
            field: 'alarmOccurTime',
            headerName: '발생 일시',
            width: 150,
            editable: false,
            headerAlign: 'center',
            align: 'center',
            headerClassName: 'grid-header'
        },
        // {
        //     field: 'alarmClearTime',
        //     headerName: '복구 일시',
        //     width: 150,
        //     editable: false,
        //     headerAlign: 'center',
        //     align: 'center',
        //     headerClassName: 'grid-header'
        // },
        {
            field: 'objectType',
            headerName: '알람 타입',
            width: 150,
            editable: false,
            headerAlign: 'center',
            align: 'center',
            headerClassName: 'grid-header',
            // renderCell: (cellValue) => {
            //     return <a style={{textDecoration:'none', color: 'inherit'}} href={`${cellValue.row.ipAddrNow}`} target='_blank' rel='noopener noreferrer'>{cellValue.row.ipAddrNow}</a>;
            // }
        },
        {
            field: 'objectId',
            headerName: '매장 코드',
            width: 150,
            editable: false,
            headerAlign: 'center',
            align: 'center',
            headerClassName: 'grid-header'
        },
        {
            field: 'objectName',
            headerName: '매장명',
            width: 550,
            editable: false,
            // valueGetter(params) {
            //     return params.row.storeInfoDetail.storeType;
            // },
            headerAlign: 'center',
            align: 'center',
            headerClassName: 'grid-header'
        },
        {
            field: 'alarmState',
            headerName: '상태',
            width: 150,
            editable: false,
            // valueGetter(params) {
            //     return params.row.storeInfoDetail.storeType;
            // },
            headerAlign: 'center',
            align: 'center',
            headerClassName: 'grid-header',
            cellClassName: 'grid-cell-color'
        },   
         {
            field: 'alarmType',
            headerName: '메시지',
            width: 730,
            editable: false,
            // valueGetter(params) {
            //     return params.row.storeInfoDetail.storeType;
            // },
            headerAlign: 'center',
            align: 'center',
            headerClassName: 'grid-header',
        },
    ];


    useEffect(() => {

        const fetchData = async() => {
            axios.get("/api/alarmstatus")
            .then((response) => {
                setAlarmStatusList(response.data);
            })
            .catch((err) => {
                console.log(err);
            });
        };

        fetchData();

        const interval = setInterval(() => {
            fetchData();
            console.log("alarmStatus interval 실행");
        }, 10000);

       return () => clearInterval(interval);

    }, []);

    return(
        <Box 
            sx={{ 
                display: 'flex', 
                justifyContent: 'center',
                alignItems: 'center', 
                height: '280px', 
                width: '100%', 
                '& .grid-header': {
                    backgroundColor: '#4B4E5E',
                    fontWeight: 'bold',
                    color: '#fff',
                    fontSize: 16
                  },
                '& .grid-cell.negative': {
                    backgroundColor: '#FF00FF',
                    color: 'white',
                    fontWeight: '1',
                },
                '& .grid-cell.positive': {
                    backgroundColor: '#00b500',
                   color: 'white',
                   fontWeight: '1',
               },
               '& .grid-cell-color': {
                    color: 'white',
                    backgroundColor: '#DC143C'
               },
                }}>
        <DataGrid
          density='compact'
          rows={alarmStatusList}
          columns={columns}
          getRowId={(row: any) => row.alarmId}
          showCellVerticalBorder
          hideFooter={true}
          slots={{
            loadingOverlay: CircularProgress,
            //toolbar: customGridToolbar
          }}
          slotProps={{
          }}
          initialState={{
            pagination: {
              paginationModel: {
                pageSize: 100,
              },
            },
            columns:{
                columnVisibilityModel: {
                    alarmId: false
                }
            }
          }}
          pageSizeOptions={[100]}
        //   checkboxSelection
          disableRowSelectionOnClick
        />
      </Box>
    )
}

export default GridAlarmStatus;