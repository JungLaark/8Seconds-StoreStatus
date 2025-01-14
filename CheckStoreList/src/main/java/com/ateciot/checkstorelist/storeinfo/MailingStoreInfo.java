package com.ateciot.checkstorelist.storeinfo;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class MailingStoreInfo {
	
	@Autowired
	StoreInfoController storeInfoController;
	@Autowired
	JavaMailSender javaMailSender;
	@Autowired
	TemplateEngine templateEngine;
	@Value("${alarm.reciever}")
	public String[] mailReciever;
	/*매일 오전 9시*/
	@Scheduled(cron = "0 0 9 * * *")
	public void sendStoreInfoMail() {
		
		MimeMessage mimeMessage = null;
		MimeMessageHelper mimeMessageHelper = null;
		Context context = null;
		try {
			List<StoreInfoDetail> listStoreInfoDetail = storeInfoController.findAll();
			System.out.println("스토어 리스트 : " + listStoreInfoDetail.size());
			context = new Context();
			context.setVariable("listStoreInfoDetail", listStoreInfoDetail);
			context.setVariable("currentTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E").format(new Date()));
			context.setVariable("storeCount", listStoreInfoDetail.size());
			
			String strHtml = templateEngine.process("Mail/storeList", context);
			mimeMessage = javaMailSender.createMimeMessage();
			
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
			mimeMessageHelper.setFrom("ateciot_dev@ateciot.com", "8Seconds-매장리스트");//이거만 바뀌봤음 뭘로 체크하는지 봐보자
			mimeMessageHelper.setTo(mailReciever);
			mimeMessageHelper.setSubject("[8Seconds]매장리스트");//이거도 바꾸었음
			mimeMessageHelper.setText(strHtml, true);
			
			javaMailSender.send(mimeMessage);
			System.out.println("store list 메일 보내기 성공");
			
		}catch(Exception ex) {
			System.out.println(ex.toString());
		}
	}
}
