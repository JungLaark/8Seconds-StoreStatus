package com.ateciot.checkstorelist.storeinfo;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
public class StoreId implements Serializable{
	private String companyName;
	private String storeCode;
	
//	public StoreId(String companyName, String storeCode) {
//		this.companyName = companyName;
//		this.storeCode = storeCode;
//	}
}
