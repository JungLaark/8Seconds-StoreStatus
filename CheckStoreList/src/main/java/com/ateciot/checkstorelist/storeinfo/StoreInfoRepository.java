package com.ateciot.checkstorelist.storeinfo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreInfoRepository extends JpaRepository<StoreInfo, String>{
	StoreInfo save(StoreInfo StoreInfo);
	/*정렬해서 조회*/
	List<StoreInfo> findAllByOrderByStoreCode();
	
	List<StoreInfo> findAll();
	
	/*jpql*/
	@Query(value = "select ipAddrNow from StoreInfo where storeCode = :storeCode and companyName = :companyName")
	StoreInfo findIpAddrByStoreCodeAndCompanyName(@Param("storeCode") String storeCode, @Param("companyName") String companyName);
	
}
