package com.ateciot.checkstorelist.storeinfo;

import java.util.ArrayList;
import java.util.List;

public interface StoreInfoService {
	public StoreInfo createStoreInfo(StoreInfo storeInfo);
	public List<StoreInfo> findAllList();
	public StoreInfo findIpAddrByStoreCodeAndCompanyName(String storeCode, String companyName);
	//public ArrayList<StoreInfo> saveAll(ArrayList<StoreInfo> arrStoreInfo);
}
