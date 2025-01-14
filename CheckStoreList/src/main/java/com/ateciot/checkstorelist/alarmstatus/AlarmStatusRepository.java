package com.ateciot.checkstorelist.alarmstatus;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ateciot.checkstorelist.storeinfo.StoreInfo;

@Repository
public interface AlarmStatusRepository extends JpaRepository<AlarmStatus, Integer> {
	List<AlarmStatus> findAllByOrderByAlarmOccurTimeDesc();

	AlarmStatus save(AlarmStatus alarmStatus);
	
	@Query("SELECT * FROM ALARM_STATUS WHERE IS_SEND_MAIL = :value")
	List<AlarmStatus> findAllByIsSendMail(@Param("value") boolean value);
}
