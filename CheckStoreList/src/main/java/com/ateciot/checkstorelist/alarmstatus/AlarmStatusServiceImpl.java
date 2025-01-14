package com.ateciot.checkstorelist.alarmstatus;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlarmStatusServiceImpl implements AlarmStatusService {
	
	@Autowired
	AlarmStatusRepository alarmStatusRepository;

	@Override
	public List<AlarmStatus> findAll() {
		// TODO Auto-generated method stub
		return alarmStatusRepository.findAllByOrderByAlarmOccurTimeDesc();
	}

	@Override
	public AlarmStatus save(AlarmStatus alarmStatus) {
		// TODO Auto-generated method stub
		return alarmStatusRepository.save(alarmStatus);
	}

	@Override
	public List<AlarmStatus> findAllByIsSendMail(boolean value) {
		// TODO Auto-generated method stub
		return alarmStatusRepository.findAllByIsSendMail(value);
	}

	@Override
	public void delete(AlarmStatus alarmStatus) {
		alarmStatusRepository.delete(alarmStatus);
	}

//	@Override
//	public AlarmStatus add(AlarmStatus alarmStatus) {
//		// TODO Auto-generated method stub
//		return alarmStatusRepository.add(alarmStatus);
//	}

//	@Override
//	public int updateAlarmStatus(int alarmId) {
//		// TODO Auto-generated method stub
//		return alarmStatusRepository.updateAlarmStatus(alarmId);
//	}

}
