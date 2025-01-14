package com.ateciot.checkstorelist.storeinfo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreInfoServiceImpl implements StoreInfoService {
	
	@Autowired
	StoreInfoRepository storeInfoRepository;

	/*Insert Store Info*/
	@Override
	public StoreInfo createStoreInfo(StoreInfo storeInfo) {
		storeInfo = storeInfoRepository.save(storeInfo);
		return storeInfo;
	}

	public List<StoreInfo> findAllList() {
		return storeInfoRepository.findAllByOrderByStoreCode();
	}
	
	public StoreInfo findIpAddrByStoreCodeAndCompanyName(String storeCode, String companyName){
		return storeInfoRepository.findIpAddrByStoreCodeAndCompanyName(storeCode, companyName);
	}
	
//	public ArrayList<StoreInfo> saveAll(ArrayList<StoreInfo> arrStoreInfo){
//		return storeInfoRepository.saveAll(arrStoreInfo);
//	}

}
