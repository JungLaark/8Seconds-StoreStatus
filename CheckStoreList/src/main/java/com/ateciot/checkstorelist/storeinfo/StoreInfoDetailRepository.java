package com.ateciot.checkstorelist.storeinfo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreInfoDetailRepository extends JpaRepository<StoreInfoDetail, String> {
	StoreInfoDetail save(StoreInfoDetail storeInfoDetail);
	List<StoreInfoDetail> findAllByOrderByStoreCode();

}
