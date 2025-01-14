package com.ateciot.checkstorelist.alarmstatus;

import java.util.List;

public interface AlarmStatusService {
	public List<AlarmStatus> findAll();
	public AlarmStatus save(AlarmStatus alarmStatus);
	public List<AlarmStatus> findAllByIsSendMail(boolean value);
//	public int updateAlarmStatus(int alarmId);
	//public AlarmStatus add(AlarmStatus alarmStatus);
	public void delete(AlarmStatus alarmStatus);
}
