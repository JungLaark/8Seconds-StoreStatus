import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { StoreInfoDetail } from '../../Models/StoreInfoDetail';
import { Typography,
     TableSortLabel,
     Dialog,
     DialogTitle,
     DialogContent,
     DialogContentText,
     DialogActions,
     Button
 } from '@mui/material';
import Box from '@mui/material/Box';
import {
    DataGrid, GridColDef, GridCellParams, GridToolbarQuickFilter
    // GridToolbarContainer,
    // GridToolbarColumnsButton,
    // GridToolbarFilterButton,
    // GridToolbarExport,
    // GridToolbarDensitySelector,
} from '@mui/x-data-grid';
//import {useData} from '@mui/x-data-grid-generator';
import CircularProgress from '@mui/material/CircularProgress';
import clsx from 'clsx';

const GridStoreList: React.FC = () => {

    const [StoreInfoDetailList, setStoreInfoDetailList] = useState<StoreInfoDetail[]>([]);
    const [dialogOpen, setDialogOpen] = useState(false);
    const [ipAddr, setIpAddr] = useState("");
    const [storeCode, setStoreCode] = useState("");
    const [storeName, setStoreName] = useState("");



    //  const customGridToolbar = () => {
    //      return (
    //          <GridToolbarContainer style={{color: 'black'}}>
    //            <GridToolbarColumnsButton />
    //            <GridToolbarFilterButton  style={{color: 'black'}}/>
    //            <GridToolbarDensitySelector style={{color: 'black'}}/>
    //            <GridToolbarExport style={{color: 'black'}}/>
    //          </GridToolbarContainer>
    //        );
    //  }

    const QuickSearchToolbar = () => {
        return (
            <Box
                sx={{
                    p: 1,
                    pb: 0,
                    display: 'flex',
                    flexDirection: 'row',
                }}>
                <div>
                    <Typography sx={{ width: '300px', fontWeight: 'bold' }}>
                        매장 개수 : {StoreInfoDetailList.length}
                    </Typography>
                </div>
                <GridToolbarQuickFilter placeholder='매장명, 매장코드' style={{ color: "black", border: '1px', marginLeft: '1300px', fontWeight: 'bold' }} />
            </Box>
        );
    }

    //form 만들어서 로그인 요청 보냄
    const handleIpAddrClick = (ipAddr: string, storeCode: string) => {
        try{
            let form = document.createElement('form');
            let objs;
    
            objs = document.createElement('input');
            objs.setAttribute('type', 'hidden');
            objs.setAttribute('name', 'param');
            objs.setAttribute('value', '56tZOR2//eTaKBVZ32XEYA==');
            form.appendChild(objs);
    
            form.setAttribute('method', 'post');
            form.setAttribute('action', ipAddr + '/restapi/user/esn_login');
            form.setAttribute('target', storeCode);
    
            document.body.appendChild(form);
            window.open('', storeCode);
    
            form.submit();

        }catch(e){
            console.log("ip Click : " + e);
        }
      
    }

    const handleDialogOpen = (ipAddr: string, storeCode: string, storeName: string) => {
        setDialogOpen(true);
        setIpAddr(ipAddr);
        setStoreCode(storeCode);
        setStoreName(storeName);
    }
    
    const handleDialogClose = () => {
        setDialogOpen(false);
        handleIpAddrClick(ipAddr, storeCode);
        console.log(dialogOpen);
    }

    const columns: GridColDef[] = [
        {
            field: 'storeCode',
            headerName: '매장코드',
            width: 85,
            editable: false,
            headerAlign: 'center',
            align: 'center',
            headerClassName: 'grid-header'
        },
        {
            field: 'storeName',
            headerName: '매장명',
            width: 260,
            editable: false,
            headerAlign: 'center',
            align: 'center',
            headerClassName: 'grid-header',
            // valueGetter(params) {
            //     return params.row.storeInfo?.storeName;
            // },
        },
        {
            field: 'ipAddrNow',
            headerName: 'IP 주소',
            width: 200,
            editable: false,
            headerAlign: 'center',
            align: 'center',
            headerClassName: 'grid-header',
            valueGetter(params) {
                return params.row.storeInfo?.ipAddr;
            },
            renderCell: (cellValue) => {
                /*이전 코드*/
                // return <a style={{ textDecoration: 'none', color: 'inherit', cursor: 'pointer' }} 
                //     target='_blank' rel='noopener noreferrer' href='javascript:void(0);'
                //     onClick={() => handleIpAddrClick(cellValue.row.storeInfo?.ipAddrNow, cellValue.row.storeCode)}
                // >{cellValue.row.storeInfo?.ipAddrNow}
                // </a>;
                return <div style={{cursor: 'pointer'}}
                            onClick={() => handleDialogOpen(cellValue.row.storeInfo?.ipAddrNow, cellValue.row.storeCode, cellValue.row.storeName)}>
                            {cellValue.row.storeInfo?.ipAddrNow}
                      </div>
            },
            cellClassName: (params: GridCellParams<any, String>) => {
                if (params.value == null) {
                    return '';
                }

                return clsx('grid-cell', {
                    negative: params.value !== 'GOOD',
                    positive: params.value === 'GOOD',
                });
            }

        },
        {
            field: 'updatedDate',
            headerName: 'IP 주소 업데이트',
            width: 160,
            editable: false,
            headerAlign: 'center',
            align: 'center',
            headerClassName: 'grid-header',
            valueGetter(params) {
                return params.row.storeInfo?.updatedDate;
            },
        },
        {
            field: 'storeType',
            headerName: 'Core 타입',
            width: 100,
            editable: false,

            headerAlign: 'center',
            align: 'center',
            headerClassName: 'grid-header'
        },
        {
            field: 'coreStatus',
            headerName: 'Core 상태',
            width: 100,
            editable: false,

            headerAlign: 'center',
            align: 'center',
            headerClassName: 'grid-header',
            cellClassName: (params: GridCellParams<any, String>) => {
                if (params.value == null) {
                    return '';
                }

                return clsx('grid-cell', {
                    negative: params.value !== 'GOOD',
                    positive: params.value === 'GOOD',
                });
            }

        },
        {
            field: 'gatewayStatus',
            headerName: 'Gateway',
            width: 100,
            editable: false,

            headerAlign: 'center',
            align: 'center',
            headerClassName: 'grid-header',
            cellClassName: (params: GridCellParams<any, String>) => {
                if (params.value == null) {
                    return '';
                }

                return clsx('grid-cell', {
                    negative: params.value !== 'GOOD',
                    positive: params.value === 'GOOD',
                });
            }
        },
        {
            field: 'networkStatus',
            headerName: 'Network',
            width: 100,
            editable: false,

            headerAlign: 'center',
            align: 'center',
            headerClassName: 'grid-header',
            cellClassName: (params: GridCellParams<any, String>) => {
                if (params.value == null) {
                    return '';
                }

                return clsx('grid-cell', {
                    negative: params.value !== 'GOOD',
                    positive: params.value === 'GOOD',
                });
            }
        },
        {
            field: 'syncStatus',
            headerName: 'Sync',
            width: 100,
            editable: false,

            headerAlign: 'center',
            align: 'center',
            headerClassName: 'grid-header',
            cellClassName: (params: GridCellParams<any, String>) => {
                if (params.value == null) {
                    return '';
                }

                return clsx('grid-cell', {
                    negative: params.value !== 'GOOD',
                    positive: params.value === 'GOOD',
                });
            }
        },
        {
            field: 'tagTotal',
            headerName: '태그 전체 개수',
            width: 150,
            editable: false,

            headerAlign: 'center',
            align: 'center',
            headerClassName: 'grid-header'
        },
        {
            field: 'tagConnected',
            headerName: '사용중인 태그',
            width: 150,
            editable: false,

            headerAlign: 'center',
            align: 'center',
            headerClassName: 'grid-header'
        },
        {
            field: 'posResultPercent',
            headerName: '태그 이미지 다운로드(%)',
            width: 200,
            editable: false,

            headerAlign: 'center',
            align: 'center',
            headerClassName: 'grid-header'
        },
        {
            field: 'updateTime',
            headerName: 'EMS 정보 업데이트',
            width: 180,
            editable: false,

            headerAlign: 'center',
            align: 'center',
            headerClassName: 'grid-header'

        }
    ];


    useEffect(() => {

        const fetchData = async () => {
            axios.get("/api/storeinfodetail")
                .then((response) => {
                    setStoreInfoDetailList(response.data);
                })
                .catch((err) => {
                    console.log(err);
                });
        };

        fetchData();

        const interval = setInterval(() => {
            fetchData();
            console.log("interval 실행");
        }, 10000);

        return () => clearInterval(interval);
    }, []);

    return (
        <Box
            sx={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                height: '580px',
                width: '100%',
                '& .grid-header': {
                    backgroundColor: '#4B4E5E',
                    fontWeight: 'bold',
                    color: '#fff',
                    fontSize: 16,
                },
                '& .grid-cell.negative': {
                    backgroundColor: '#DC143C',
                    color: 'white',
                    fontWeight: '1',
                },
                '& .grid-cell.positive': {
                    backgroundColor: '#00b500',
                    color: 'white',
                    fontWeight: '1',
                },
            }}>
            <DataGrid

                rows={StoreInfoDetailList}
                columns={columns}
                density='compact'
                getRowId={(row: any) => row.storeCode}
                showCellVerticalBorder
                hideFooter={true}
                disableColumnFilter
                disableColumnSelector
                disableDensitySelector
                disableColumnMenu={true}

                slots={{
                    loadingOverlay: CircularProgress,
                    //toolbar: customGridToolbar
                    toolbar: QuickSearchToolbar
                }}
                //   slotProps={{
                //     toolbar: {
                //         showQuickFilter: true,
                //         quickFilterProps: {

                //         }
                //     }
                //   }}
                initialState={{
                    pagination: {
                        paginationModel: {
                            pageSize: 100,
                        },
                    },
                }}
                pageSizeOptions={[100]}
                //   checkboxSelection
                disableRowSelectionOnClick
            />
            <Dialog
                open={dialogOpen}
                onClose={handleDialogClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">
                    {storeName + " EMS로 이동"}
                </DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                    {storeName + " EMS 페이지로 이동합니다. 네트워크 사정에 따라 연결이 불가한 매장이 있을 수 있습니다."}
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleDialogClose}>확인</Button>
                </DialogActions>
            </Dialog>
        </Box>
        
    )
}

export default GridStoreList;