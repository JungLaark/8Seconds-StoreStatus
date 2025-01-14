package com.ateciot.checkstorelist.storeinfo;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

/*Gen2+ data 수신*/
@Entity
@Data
@IdClass(StoreId.class)
@Table(name="storeInfo")
public class StoreInfo {
	
	public StoreInfo() {
	}

	@Builder
	public StoreInfo(String companyName, String storeCode, String storeName, String ipAddrNow, String ipAddrPrev) {
		this.storeCode = storeCode.replaceAll("\u0000", "");
		this.storeName = storeName.replaceAll("\u0000", "");
		this.ipAddrNow = ipAddrNow.replaceAll("\u0000", "");
		this.ipAddrPrev = ipAddrPrev.replaceAll("\u0000", "");
		this.companyName = companyName.replaceAll("\u0000", "");
		//this.storeInfoDetail = storeInfoDetail;
	}
	
	@Id
	@Column(name = "companyName", length=20)
	private String companyName;
	
	@Id
	@Column(name = "storeCode", length=30)
	private String storeCode;
	
	@Column(name="storeName", length=60)
	private String storeName;
	
	@Column(name="ipAddrPrev", length=60)
	private String ipAddrPrev;
	
	@Column(name="ipAddrNow", length=60)
	private String ipAddrNow;
	
//	@OneToOne()
//	@JoinColumns({
//		@JoinColumn(name = "companyName"),
//		@JoinColumn(name = "storeCode")
//	})
	//private StoreInfoDetail storeInfoDetail;

	/*날짜*/
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	@CreationTimestamp
	//@CreatedDate
	private LocalDateTime createdDate;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	@UpdateTimestamp
	//@LastModifiedDate
	private LocalDateTime updatedDate;
	
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName.replaceAll("\u0000", "");
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public String getIpAddrNow() {
		return ipAddrNow;
	}

	public void setIpAddrNow(String ipAddrNow) {
		this.ipAddrNow = ipAddrNow.replaceAll("\u0000", "");
	}

	public String getIpAddrPrev() {
		return ipAddrPrev;
	}

	public void setIpAddrPrev(String ipAddrPrev) {
		this.ipAddrPrev = ipAddrPrev.replaceAll("\u0000", "");
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName.replaceAll("\u0000", "");
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode.replaceAll("\u0000", "");
	}


	
	
}
