package com.ateciot.checkstorelist.storeinfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.Data;
/*ESN store info read*/
@Entity
@Data
@IdClass(StoreId.class)
@Table(name="storeInfoDetail")
public class StoreInfoDetail {
	
	public StoreInfoDetail() {
		
	}
	@Builder
	public StoreInfoDetail(String companyName, String storeCode, String storeType, String coreStatus,
			String networkStatus, String gatewayStatus, String syncStatus, String activeDeviceType,
			int posResultPercent, int tagTotal, int tagConnected, String updateTime, String storeName, StoreInfo storeInfo) {
		this.companyName = companyName.replaceAll("\u0000", "");
		this.storeCode = storeCode.replaceAll("\u0000", "");
		this.storeType = storeType.replaceAll("\u0000", "");
		this.coreStatus = coreStatus.replaceAll("\u0000", "");
		this.networkStatus = networkStatus.replaceAll("\u0000", "");
		this.gatewayStatus = gatewayStatus.replaceAll("\u0000", "");
		this.syncStatus = syncStatus.replaceAll("\u0000", "");
		this.activeDeviceType = activeDeviceType.replaceAll("\u0000", "");
		this.posResultPercent = posResultPercent;
		this.tagTotal = tagTotal;
		this.tagConnected = tagConnected;
		this.updateTime = updateTime.replaceAll("\u0000", "");
		this.storeName = storeName.replaceAll("\u0000", "");
		this.storeInfo = storeInfo;
	}



	/*회사 명*/
	@Id
	@Column(name = "companyName", length = 20)
	private String companyName;
	/*매장 코드*/
	@Id
	@Column(name = "storeCode", length = 30)
	private String storeCode;
	
	/*매장명*/
	@Column(name="storeName", length = 60)
	private String storeName;
	
	/*매장 타입 : 싱글, 등*/
	@Column(name = "storeType", length = 10)
	private String storeType;
	/*core 상태*/
	@Column(name = "coreStatus", length = 7)
	private String coreStatus;
	/*network 상태*/
	@Column(name = "networkStatus", length = 7)
	private String networkStatus;
	/*gateway 상태*/
	@Column(name = "gatewayStatus", length = 7)
	private String gatewayStatus;
	/*sync 상태*/
	@Column(name = "syncStatus", length = 7)
	private String syncStatus;
	/*device type*/
	@Column(name = "activeDeviceType", length = 10)
	private String activeDeviceType;
	/*다운로드 percentage*/
	@Column(name = "posResultPercent", length = 3)
	private int posResultPercent;
	/*tag 전체 개수 */
	@Column(name = "tagTotal", length = 10)
	private int tagTotal;
	/*사용 중인 태그 개수 */
	@Column(name = "tagConnected", length = 10)
	private int tagConnected;
	/*업데이트 시간*/
	@Column(name = "updateTime", length = 20)
	private String updateTime;
	
	@OneToOne()
	@JoinColumns({
		@JoinColumn(name = "companyName"),
		@JoinColumn(name = "storeCode")
	})
	private StoreInfo storeInfo;
	
//	@OneToOne(mappedBy = "storeInfo")
//	private StoreInfo storeInfo;
	
	public String getCompanyName() {
		return companyName;
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

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType.replaceAll("\u0000", "");
	}

	public String getCoreStatus() {
		return coreStatus;
	}

	public void setCoreStatus(String coreStatus) {
		this.coreStatus = coreStatus.replaceAll("\u0000", "");
	}

	public String getNetworkStatus() {
		return networkStatus;
	}

	public void setNetworkStatus(String networkStatus) {
		this.networkStatus = networkStatus.replaceAll("\u0000", "");
	}

	public String getGatewayStatus() {
		return gatewayStatus;
	}

	public void setGatewayStatus(String gatewayStatus) {
		this.gatewayStatus = gatewayStatus.replaceAll("\u0000", "");
	}

	public String getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(String syncStatus) {
		this.syncStatus = syncStatus.replaceAll("\u0000", "");
	}

	public String getActiveDeviceType() {
		return activeDeviceType;
	}

	public void setActiveDeviceType(String activeDeviceType) {
		this.activeDeviceType = activeDeviceType.replaceAll("\u0000", "");
	}

	public int getPosResultPercent() {
		return posResultPercent;
	}

	public void setPosResultPercent(int posResultPercent) {
		this.posResultPercent = posResultPercent;
	}

	public int getTagTotal() {
		return tagTotal;
	}

	public void setTagTotal(int tagTotal) {
		this.tagTotal = tagTotal;
	}

	public int getTagConnected() {
		return tagConnected;
	}

	public void setTagConnected(int tagConnected) {
		this.tagConnected = tagConnected;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime.replaceAll("\u0000", "");
	}

	public StoreInfo getStoreInfo() {
		return storeInfo;
	}

	public void setStoreInfo(StoreInfo storeInfo) {
		this.storeInfo = storeInfo;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	
}
