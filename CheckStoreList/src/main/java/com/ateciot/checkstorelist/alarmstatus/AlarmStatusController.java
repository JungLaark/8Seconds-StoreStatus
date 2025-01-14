package com.ateciot.checkstorelist.alarmstatus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.ateciot.checkstorelist.config.UserLoginToken;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
@Controller
public class AlarmStatusController {

	@Autowired
	AlarmStatusService alarmStatusService;
	@Autowired
	JavaMailSender javaMailSender;
	@Autowired 
	TemplateEngine templateEngine; /*이렇게 하니 제대로 읽는다.*/

	@Value("${alarm.reciever}")
	public String[] mailReciever;
	
	private static final Logger logger = LoggerFactory.getLogger(AlarmStatusController.class); 
	
	/*아침 8시부터 저녁 9시까지*/
	//@Scheduled(cron = "0/10 8-21 * * * MON-FRI")
	/*30분 간격*/
	@Scheduled(cron = "0 0/30 * * * *")
	public void sendEmail() {

		List<AlarmStatus> listAlarmStatus = null;
		List<AlarmStatus> tempList = null;
		listAlarmStatus = getAlarmList();
		tempList = getAlarmList();

		MimeMessage mimeMessage = null;
		MimeMessageHelper mimeMessageHelper = null;
		Context context = null;
		try {
			
			/*알람 리스트 전체 가지고 옴*/
			/*success 는 어떻게 하는게 나을지 생각해봐야함*/
			for(AlarmStatus alarmStatus : listAlarmStatus) {
				if(alarmStatus.getIsSendMail()
						|| alarmStatus.getAlarmType().contains("ntp_sync_fail")
						|| alarmStatus.getAlarmType().contains("network_fail")
						|| alarmStatus.getAlarmType().contains("cvt_sync_fail")
						|| alarmStatus.getAlarmType().contains("file_sync_fail")
						|| alarmStatus.getAlarmType().contains("gateway_disconnected")
						|| alarmStatus.getAlarmType().contains("master_core_fail")
						|| alarmStatus.getAlarmType().contains("core_restart_sw")
						|| alarmStatus.getAlarmType().contains("core_restart_db")
						) {
					tempList.remove(alarmStatus);
				}
			}

			/*보낼 데이터가 있을 경우에만. */
			if(tempList.size() > 0) {
				context = new Context();

				context.setVariable("listAlarmStatus", tempList);
				context.setVariable("currentTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E").format(new Date()));
				String strHtml = templateEngine.process("Mail/alarmMail", context);
				mimeMessage = javaMailSender.createMimeMessage();

				mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8"); 
				mimeMessageHelper.setFrom("ateciot_dev@ateciot.com", "8Seconds-alarm");
				mimeMessageHelper.setTo(mailReciever);
				mimeMessageHelper.setSubject("[8Seconds]알람");
				mimeMessageHelper.setText(strHtml, true);

				javaMailSender.send(mimeMessage);

				for(AlarmStatus alarmStatus : listAlarmStatus) {
					if(tempList.contains(alarmStatus)) {
						/*보냈다. flag is_send_mail 이거 true로*/
						/*아니면 새로 clear 된 것을 새로 만들어서 넣어? */
						alarmStatus.setIsSendMail(true);
						alarmStatusService.save(alarmStatus);
					}
				}
			}
		}catch(Exception ex) {
			System.out.println(ex.toString());
		}
	}

	public List<AlarmStatus> getAlarmList(){
		return alarmStatusService.findAllByIsSendMail(false);
	}

	public void insertAlarmStatus(JsonArray data) {
		try {
			if(data != null) {
				/*기존에 같은 엔티티가 있으면 save 실행하지 않도록 하기 */
				/*기존 것이랑 비교해서 없다면 그걸 clear 로 바꿔줘야 할거 같은데. */
				List<AlarmStatus> originAlarmStatus = alarmStatusService.findAll();
				List<AlarmStatus> newAlarmStatus = null;
				newAlarmStatus = new ArrayList<AlarmStatus>();

				for(JsonElement jsonElement : data) {
					JsonObject jsonObject = jsonElement.getAsJsonObject();
					AlarmStatus alarmStatus = new AlarmStatus();

					alarmStatus.setAlarmId(jsonObject.get("alarm_id").getAsInt());
					//alarmStatus.setAlarmState(jsonObject.get("alarm_state").getAsString());
					alarmStatus.setAlarmState("ERROR");//초기 데이터는 ERROR 같은게 들어온다면  // 어차피 다 ERROR 임
					alarmStatus.setAlarmType(convertAlarmType(jsonObject.get("alarm_type").getAsString()));
					alarmStatus.setObjectType(jsonObject.get("object_type").getAsString());
					alarmStatus.setObjectId(jsonObject.get("object_id").getAsString());
					alarmStatus.setObjectName(jsonObject.get("object_name").isJsonNull() ? "" : jsonObject.get("object_name").getAsString());
					/*장난치나 occur 아님?ㅋㅋㅋㅋㅋ*/
					alarmStatus.setAlarmOccurTime(jsonObject.get("alarm_occure_time").getAsString());
					alarmStatus.setAlarmClearTime(jsonObject.get("alarm_clear_time").isJsonNull() ? "" : jsonObject.get("alarm_clear_time").getAsString());

					/*이전거의 mail status 설정*/
					for(AlarmStatus origin : originAlarmStatus) {
						if(origin.getAlarmId() == alarmStatus.getAlarmId()) {
							alarmStatus.setIsSendMail(origin.getIsSendMail());
						}
					}
					/*새로운 것들*/
					newAlarmStatus.add(alarmStatus);

					/*저장을 해야할까?*/
					if(originAlarmStatus.size() == 0) {
						alarmStatusService.save(alarmStatus);
					}
				}

				if(originAlarmStatus.size() > 0) {
					List<AlarmStatus> tempOriginAlarmStatus = null;
					tempOriginAlarmStatus  = new ArrayList<AlarmStatus>();

					List<AlarmStatus> temp = null;
					temp = new ArrayList<AlarmStatus>();
					/*list 깊은 복사 얇은 복사.......*/
					temp.addAll(newAlarmStatus); /* = 는 같은 주소 가리키는거라*/
					/*새로운 것*/
					for(AlarmStatus origin : originAlarmStatus) {
						for(AlarmStatus newEntity : newAlarmStatus) {
							if(origin.getAlarmId() == newEntity.getAlarmId()) {
								/*같은 것들*/
								tempOriginAlarmStatus.add(origin);
							}
						}
					}
					/*새로운 것들에서 중복되는 것을 제거한다. 그러면 남아있는 것들이 새로운 것들*/
					System.out.println("####################################################");
					System.out.println("전 temp 크기 : ");
					System.out.println(temp.size());
					System.out.println("####################################################");
					System.out.println("남아있는 것들 : " + temp.removeAll(tempOriginAlarmStatus));
					System.out.println("####################################################");
					System.out.println("remove 후 temp 크기 : ");
					System.out.println(temp.size());

					if(temp.size() > 0) {
						/*새로운 것 저장*/
						for(AlarmStatus item : temp) {
							System.out.println("새로운 것을 저장합니다.");
							System.out.println(item.getAlarmId());
							alarmStatusService.save(item);
						}
					}else {
						System.out.println("새로운 것이 없습니다.");
						logger.info("새롭게 추가된 ALARM이 없습니다.");
					}

					/*##########################################################################################*/
					
					/*Clear*/
					originAlarmStatus.removeAll(newAlarmStatus);

					if(originAlarmStatus.size() > 0) {
						for(AlarmStatus item : originAlarmStatus) {
							if(!item.getAlarmState().equalsIgnoreCase("CLEAR")) {
								item.setAlarmState("CLEAR");
								item.setIsSendMail(false);
							}
							alarmStatusService.save(item);
						}
					}
				}
			}

		}catch(Exception ex) {
			System.out.println("insertAlarmStatus : " + ex.toString());
		}
	}

	/*alarm 한글화*/
	public String convertAlarmType(String alarmType) {
		try {
			String returnString = "";
			if(alarmType.equalsIgnoreCase("ntp_sync_fail")) {
				returnString = "NTP 동기화 실패 (ntp_sync_fail)";
			}else if(alarmType.equalsIgnoreCase("network_fail")) {
				returnString = "네트워크 오류 (network_fail)";
			}else if(alarmType.equalsIgnoreCase("cvt_sync_fail")) {
				returnString = "CSV 파일 동기화 오류 (cvt_sync_fail)";
			}else if(alarmType.equalsIgnoreCase("file_sync_fail")) {
				returnString = "파일 싱크 오류 (file_sync_fail)";
			}else if(alarmType.equalsIgnoreCase("gateway_disconnected")) {
				returnString = "Gateway 미접속 (gateway_disconnected)" ;
			}else if(alarmType.equalsIgnoreCase("master_core_fail")) {
				returnString = "백업 전환 (master_core_fail)";
			}else if(alarmType.equalsIgnoreCase("core_restart_sw")) {
				returnString = "EMS Core 재시작 (core_restart_sw)";
			}else if(alarmType.equalsIgnoreCase("core_restart_db")) {
				returnString = "EMS DB 재시작 (core_restart_db)";
			}else if(alarmType.equalsIgnoreCase("keep_alive_fail")) {
				returnString = "Core 미접속 (keep_alive_fail)";
			}
			return returnString;

		}catch(Exception ex) {
			System.out.println(ex.toString());
			return "한글 변환 시 오류가 발생했습니다.";
		}
	}

	public JsonArray getAlarmStatus(UserLoginToken token) {
		try {
			StringBuilder result = new StringBuilder();
			String line = null;
			JsonObject jsonObject = new JsonObject();
			JsonParser parser = new JsonParser();
			JsonElement element;
			URL url = new URL("https://esl.samsungfashion.com/alarm/dashboard/status");
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
			body.addProperty("sort_col", "alarm_occure_time"); //이거 뭐 occur 인데 와이라노
			body.addProperty("sort_type", "desc");
			jsonObject.add("header", header);
			jsonObject.add("body", body);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
			bw.write(jsonObject.toString());

			//System.out.println(jsonObject.toString());
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

			JsonObject origin = JsonParser.parseString(line).getAsJsonObject();
			JsonObject retHeader = origin.getAsJsonObject().get("header").getAsJsonObject();
			JsonArray retBody = origin.get("body").getAsJsonArray();

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

}
