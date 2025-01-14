package com.ateciot.checkstorelist.storeinfo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreInfoDetailServiceImpl implements StoreInfoDetailService {

	@Autowired
	StoreInfoDetailRepository storeInfoDetailRepository;

	@Override
	public StoreInfoDetail saveList(StoreInfoDetail storeInfoDetail) {
		return storeInfoDetailRepository.save(storeInfoDetail);
	}
	
	public List<StoreInfoDetail> findAll(){
		return storeInfoDetailRepository.findAllByOrderByStoreCode();
	}
	
//	@Override
//	public ArrayList<StoreInfoDetail> saveAllList(ArrayList<StoreInfoDetail> arrStoreInfoDetail) {
//		// TODO Auto-generated method stub
//		return storeInfoDetailRepository.saveAll(arrStoreInfoDetail);
//	}

}
