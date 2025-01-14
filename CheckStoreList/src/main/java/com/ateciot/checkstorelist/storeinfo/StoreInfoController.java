package com.ateciot.checkstorelist.storeinfo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ateciot.checkstorelist.alarmstatus.AlarmStatus;
import com.ateciot.checkstorelist.alarmstatus.AlarmStatusController;
import com.ateciot.checkstorelist.alarmstatus.AlarmStatusService;
import com.ateciot.checkstorelist.config.UserLoginToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value="/api")
public class StoreInfoController {
	
	@Autowired
	StoreInfoService storeInfoService;
	@Autowired
	StoreInfoDetailService storeInfoDetailService;
	@Autowired
	AlarmStatusService alarmStatusService;
	@Autowired
	AlarmStatusController alarmStatusController;
	
	@RequestMapping(method=RequestMethod.POST, path="/storeinfo")
	public StoreInfo createStoreInfo(@RequestBody StoreInfo storeInfo ) throws Exception {
		/*기존에 저장된 데이터가 있다면 그냥 skip 하면 되는 것임.*/
		/*최초 없으면 insert*/
		/*이상하게 짜놨다. */
		StoreInfo originStoreInfo = storeInfoService.findIpAddrByStoreCodeAndCompanyName(storeInfo.getStoreCode(), storeInfo.getCompanyName());
		
		if(originStoreInfo == null) {
			/*초기 데이터가 없다면*/
			originStoreInfo = storeInfoService.createStoreInfo(storeInfo);
		}else {
			if(!(storeInfo.getIpAddrNow().equalsIgnoreCase(originStoreInfo.getIpAddrNow()))){
				/*새로운게 날아왔다.*/
				System.out.println("새로운 IP 정보가 왔습니다.");
				System.out.println(storeInfo.getIpAddrNow());
				System.out.println(originStoreInfo.getIpAddrNow());
				/*새로 온거는 after 이전 거는 before*/
				originStoreInfo.setIpAddrPrev(originStoreInfo.getIpAddrNow());
				System.out.println("기존 아이피 : " + originStoreInfo.getIpAddrNow());
				originStoreInfo.setIpAddrNow(storeInfo.getIpAddrNow());
				System.out.println("새로운 아이피 : " + originStoreInfo.getIpAddrNow());
				originStoreInfo = storeInfoService.createStoreInfo(originStoreInfo);
			}else {
				/*초기 데이터가 있고 기존에 있는거다. */
				/*기존에 있는 데이터라면*/
				/*그냥 시간만 update*/
				originStoreInfo.setUpdatedDate(storeInfo.getUpdatedDate());
				originStoreInfo = storeInfoService.createStoreInfo(originStoreInfo);
			}
		}
//		storedStoreInfo = storeInfoService.createStoreInfo(storedStoreInfo);
		return originStoreInfo;
	}
	
	@RequestMapping(method=RequestMethod.GET, path="/storeinfodetail")
	public List<StoreInfoDetail> findAll(){
		return storeInfoDetailService.findAll();
	}
	
	@RequestMapping(method=RequestMethod.GET, path="/alarmstatus")
	public List<AlarmStatus> findAllAlarmStatus(){
		List<AlarmStatus> listAlarmStatus = alarmStatusService.findAll();
		List<AlarmStatus> temp = new ArrayList<AlarmStatus>();
		for(AlarmStatus alarmStatus : listAlarmStatus) {
			if(alarmStatus.getAlarmState().equalsIgnoreCase("CLEAR")){
				//listAlarmStatus.remove(alarmStatus);
				temp.add(alarmStatus);
			}
		}
		
		listAlarmStatus.removeAll(temp);
		
		return listAlarmStatus;
	}
	
	/*15초마다 로그인하고 로그아웃하고 해야하는 거임???*/
	@Scheduled(fixedDelay = 15000)
	public void getStoreDetail() {
		try {
			UserLoginToken loginToken = getLogin();
			insertStoreDetail(getStoreDetail(loginToken));
			alarmStatusController.insertAlarmStatus(alarmStatusController.getAlarmStatus(loginToken));
			doLogout(loginToken);
			//sendEmail();
			
		}catch(Exception ex) {
			System.out.println(ex.toString());
		}
	}
	
	public UserLoginToken getLogin() {
		try {
			StringBuilder result = new StringBuilder();
			String line = null;
			JsonObject jsonObject = new JsonObject();
			JsonParser parser = new JsonParser();
			JsonElement element;
			URL url = new URL("https://esl.samsungfashion.com/user/login");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setUseCaches(false);
			
			JsonObject header = new JsonObject();
			header.addProperty("token", "");
			JsonObject body = new JsonObject();
			body.addProperty("username", "admin");
			body.addProperty("password", "esl");
			jsonObject.add("header", header);
			jsonObject.add("body", body);
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
			bw.write(jsonObject.toString());
			bw.flush();
			bw.close();
			
			try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))){
				
				while((line = br.readLine()) != null) {
					//System.out.println(line);
					result.append(line);
				}
			}
		
			conn.disconnect();
			
			line = null;
			line = result.toString();
			element = parser.parse(line);
			JsonObject retBody = element.getAsJsonObject().get("body").getAsJsonObject();
			//JsonObject retHeader = element.getAsJsonObject().get("header").getAsJsonObject();
			UserLoginToken userLoginToken = new UserLoginToken();
			/*특수 문자 제거 이거 조심! 다 삭제하면 안됨*/
			userLoginToken.setxAuthToken(retBody.get("x_auth_token").toString().replaceAll("\\\"", ""));
			userLoginToken.setToken(retBody.get("token").toString().replaceAll("\\\"", ""));
			
			return userLoginToken;
			
		}catch(Exception ex) {
			System.out.println(ex);
			return null;
		}
	}
	
	public boolean doLogout(UserLoginToken token) {
		try {
			StringBuilder result = new StringBuilder();
			String line = null;
			JsonObject jsonObject = new JsonObject();
			JsonParser parser = new JsonParser();
			JsonElement element;
			URL url = new URL("https://esl.samsungfashion.com/user/logout");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("x-Auth-Token", token.getxAuthToken());
			conn.setUseCaches(false);
			
			JsonObject header = new JsonObject();
			header.addProperty("token", token.getToken());
			jsonObject.add("header", header);
			jsonObject.add("body", null);
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
			bw.write(jsonObject.toString());
			bw.flush();
			bw.close();
			
			try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))){
				
				while((line = br.readLine()) != null) {
					result.append(line);
				}
			}
		
			conn.disconnect();
			
			line = null;
			line = result.toString();
			element = parser.parse(line);
			JsonObject retBody = element.getAsJsonObject().get("header").getAsJsonObject();
			UserLoginToken userLoginToken = new UserLoginToken();
			
			if(retBody.get("description").toString().equals("OK")) {
				return true;
			}else {
				return false;
			}
		}catch(Exception ex) {
			System.out.println(ex);
			return false;
		}
	}
	
	public JsonArray getStoreDetail(UserLoginToken token) {
		try {
			StringBuilder result = new StringBuilder();
			String line = null;
			JsonObject jsonObject = new JsonObject();
			JsonParser parser = new JsonParser();
			JsonElement element;
			URL url = new URL("https://esl.samsungfashion.com/store/dashboard/status");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json;UTF-8");
			conn.setRequestProperty("x-Auth-Token", token.getxAuthToken());
			conn.setUseCaches(false);
			
			JsonObject header = new JsonObject();
			header.addProperty("token", token.getToken());
			JsonObject body = new JsonObject();
			body.addProperty("search", "");
			body.addProperty("sort_col", "");
			body.addProperty("sort_type", "");
			jsonObject.add("header", header);
			jsonObject.add("body", body);
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
			bw.write(jsonObject.toString());
			
			bw.flush();
			bw.close();
			
			try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))){
				
				while((line = br.readLine()) != null) {
					//System.out.println("LINE : " + line);
					result.append(line);
				}
			}
		
			conn.disconnect();
			
			line = null;
			line = result.toString();
			//element = parser.parse(line);
			
			JsonObject origin = JsonParser.parseString(line).getAsJsonObject();
			
			JsonObject retHeader = origin.getAsJsonObject().get("header").getAsJsonObject();
			JsonArray retBody = origin.get("body").getAsJsonArray();
			UserLoginToken userLoginToken = new UserLoginToken();
			
			if(retHeader.get("description").toString().equals("\"OK\"")) {
				return retBody;
			}else {
				return null;
			}
		}catch(Exception ex) {
			System.out.println(ex);
			return null;
		}
	}
	

	
	public void insertStoreDetail(JsonArray data) {
//		JsonParser parser = new JsonParser();
//		JsonElement element = parser.parse(data.toString());
//		JsonArray jsonArray = new JsonArray();
//		jsonArray.add(element);
//		
//		System.out.println("jsonArray : " + jsonArray);
		
		try {
			
//			Gson gson = new Gson();
//			Type listType = new TypeToken<List<StoreInfoDetail>>() {}.getType(); 
//			List<StoreInfoDetail> listStoreInfoDetail = gson.fromJson(data.toString(), listType);
			//ArrayList<StoreInfoDetail> arrStoreInfoDetail = new ArrayList<StoreInfoDetail>();
			//
			//System.out.println(data.toString());
			
			//storeInfoService.saveAll(listStoreInfo);
			//storeInfoDetailService.saveAllList(listStoreInfo);
		
			
			//System.out.println("listStoreInfoDetail : " + listStoreInfoDetail.get(10).getNetworkStatus());
			
			for(JsonElement jsonElement : data) {
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				
				StoreInfoDetail storeInfoDetail = new StoreInfoDetail();
				storeInfoDetail.setCompanyName("8Seconds");
				storeInfoDetail.setStoreCode(jsonObject.get("str_code").getAsString());
				storeInfoDetail.setActiveDeviceType(jsonObject.get("active_device_type").getAsString());
				storeInfoDetail.setCoreStatus(jsonObject.get("core_status").getAsString());
				storeInfoDetail.setGatewayStatus(jsonObject.get("gateway_status").getAsString());
				storeInfoDetail.setNetworkStatus(jsonObject.get("network_status").getAsString());
				storeInfoDetail.setPosResultPercent(jsonObject.get("pos_result_percent").getAsInt());
				storeInfoDetail.setStoreType(jsonObject.get("str_type").getAsString());
				storeInfoDetail.setSyncStatus(jsonObject.get("sync_status").getAsString());
				storeInfoDetail.setTagConnected(jsonObject.get("connected").getAsInt());
				storeInfoDetail.setTagTotal(jsonObject.get("total").getAsInt());
				storeInfoDetail.setUpdateTime(jsonObject.get("update_time").getAsString());
				storeInfoDetail.setStoreName(jsonObject.get("str_name").isJsonNull() ? "" : jsonObject.get("str_name").getAsString());
				
				storeInfoDetailService.saveList(storeInfoDetail);
				//System.out.println("insert 성공");
			}
		}catch(Exception ex) {
			System.out.println(ex.toString());
		}
	}
}
