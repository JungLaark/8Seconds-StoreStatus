import React, {useEffect, useState} from 'react';
import axios from 'axios';
//import { StoreInfo } from '../../Models/StoreInfoDetail';
import { Wrapper } from './Test.styles';


const Test: React.FC = () => {

    // const [storeList, setStoreList] = useState<StoreInfo[]>([]);

    // useEffect(() => {
    //     //axios.get("/api/storeinfo")
    //     axios.get("/api/storeinfo")
    //         .then((response) => {
    //             setStoreList(response.data);
    //             console.log(response.data);
    //         })
    //         .catch((err) => {
    //             console.log(err)
    //         })
        
    // }, []);

    return(
        <Wrapper>
            {/* <div>8Seconds 매장 리스트</div>
            <table className='table'>
                <thead>
                    <tr className="tr">
                        <th className="th">회사명</th>
                        <th className="th">매장 코드</th>
                        <th className="th">매장명</th>
                        <th className="th">IP</th>
                        <th className="th">IP 정보 업데이트 일자</th>
                        <th className="th">Core타입</th>
                        <th className="th">상태</th>
                        <th className="th">Gateway</th>
                        <th className="th">Network</th>
                        <th className="th">Sync</th>
                        <th className="th">태그 전체</th>
                        <th className="th">태그 사용중</th>
                        <th className="th">태그 다운로드(%)</th>
                        <th className="th">EMS 정보 업데이트 일자</th>

                    </tr>
                </thead>
                <tbody>
                {
                storeList.map(item => (
                        <tr>
                            <td className='td'>{item.companyName}</td>
                            <td className='td'>{item.storeCode}</td>
                            <td>{item.storeName}</td>
                            <td className='td'> <a className='a' href={item.ipAddrNow} target='_blank' rel='noopener noreferrer' >{item.ipAddrNow}</a></td>
                            <td className='td'>{item.updatedDate.toString()}</td>
                            <td className='td'>{item.storeInfoDetail.storeType}</td>
                            <td className='td'>{item.storeInfoDetail.coreStatus}</td>
                            <td className='td'>{item.storeInfoDetail.gatewayStatus}</td>
                            <td className='td'>{item.storeInfoDetail.networkStatus}</td>
                            <td className='td'>{item.storeInfoDetail.syncStatus}</td>
                            <td className='td'>{item.storeInfoDetail.tagTotal}</td>
                            <td className='td'>{item.storeInfoDetail.tagConnected}</td>
                            <td className='td'>{item.storeInfoDetail.posResultPercent}</td>
                            <td className='td'>{item.storeInfoDetail.updateTime}</td>



                        </tr>
                )
                )
            }
                </tbody>
            </table>

            <div>Count : {storeList.length}</div> */}
        </Wrapper>
    )
};

export default Test;