package com.ateciot.checkstorelist.storeinfo;

import java.util.ArrayList;
import java.util.List;

public interface StoreInfoDetailService {
//	public ArrayList<StoreInfoDetail> saveAllList(ArrayList<StoreInfoDetail> arrStoreInfoDetail);
	public StoreInfoDetail saveList(StoreInfoDetail storeInfoDetail);
	public List<StoreInfoDetail> findAll();
}
