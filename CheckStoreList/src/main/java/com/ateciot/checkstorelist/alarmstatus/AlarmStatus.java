package com.ateciot.checkstorelist.alarmstatus;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Data
@Table(name="alarmStatus")
@DynamicUpdate
public class AlarmStatus{
	public AlarmStatus(int alarmId, String alarmState, String alarmType, String objectType, String objectId,
			String objectName, String alarmOccurTime, String alarmClearTime, boolean isSendMail) {
		this.alarmId = alarmId;
		this.alarmState = alarmState;
		this.alarmType = alarmType;
		this.objectType = objectType;
		this.objectId = objectId;
		this.objectName = objectName;
		this.alarmOccurTime = alarmOccurTime;
		this.alarmClearTime = alarmClearTime;
		this.isSendMail = isSendMail;
	}

	public AlarmStatus() {
	}

	@Id
	@Column(name = "alarmId", length = 4)
	private int alarmId;
	
	@Column(name = "alarmState", length = 10)
	private String alarmState;
	
	@Column(name = "alarmType", length = 30)
	private String alarmType;
	
	@Column(name = "objectType", length = 20)
	private String objectType;
	
	@Column(name = "objectId", length = 30)
	private String objectId;
	
	@Column(name = "objectName", length = 50)
	private String objectName;
	
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	//@CreatedDate
	@Column(name = "alarmOccurTime", length = 20)
	private String alarmOccurTime;
	
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	//@LastModifiedDate
	@Column(name = "alarmClearTime", length = 20)
	private String alarmClearTime;
	
	@Column(name = "isSendMail")
	//@ColumnDefault("false")
	private boolean isSendMail;

	public int getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(int alarmId) {
		this.alarmId = alarmId;
	}

	public String getAlarmState() {
		return alarmState;
	}

	public void setAlarmState(String alarmState) {
		this.alarmState = alarmState;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getAlarmOccurTime() {
		return alarmOccurTime;
	}

	public void setAlarmOccurTime(String alarmOccurTime) {
		this.alarmOccurTime = alarmOccurTime;
	}

	public String getAlarmClearTime() {
		return alarmClearTime;
	}

	public void setAlarmClearTime(String alarmClearTime) {
		this.alarmClearTime = alarmClearTime;
	}

	public boolean getIsSendMail() {
		return isSendMail;
	}

	public void setIsSendMail(boolean isSendMail) {
		this.isSendMail = isSendMail;
	}
//
//	@Override
//	public String getId() {
//		// TODO Auto-generated method stub
//		return Integer.toString(alarmId);
//	}
//
//	@Override
//	public boolean isNew() {
//		// TODO Auto-generated method stub
//		return alarmId == 0;
//	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		AlarmStatus alarmStatus = (AlarmStatus)obj;
		
		if(this.getAlarmId() == alarmStatus.getAlarmId()) {
			return true;
		}
		return false;
	}
	
	
	

}
