import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.JSONObject;



public class SendStoreInfo {

	public static void main(String[] args) {
		try {
			String storeName = "";
			String storeCode = "";
			String ipAddr = "https://" + args[0].toString().trim() + ":8443";
			String companyName = args[1].toString().trim();

			storeName = getStoreName();
			
			String storeInfo[] = storeName.split("\\(");
			
			storeName = storeInfo[0];
			storeCode = storeInfo[1].substring(0, storeInfo[1].length() - 1);
			
			System.out.println("storeName : " + storeName); // 이거 뭐여 ㅋㅋㅋ 갑자기 깨짐 
			System.out.println("ipAddr : " + ipAddr); // 121.134.83.75
			System.out.println("storeCode : " + storeCode); // 121.134.83.75
			System.out.println("companyName : " + companyName); // 121.134.83.75
			
			
			/*post request */
			postRequest(storeName, ipAddr, storeCode, companyName);
		}catch(Exception ex) {
			System.out.println(ex.toString());
		}
		
	}
	
	public static String getStoreName() {
		Connection conn = null;
		org.sqlite.SQLiteConfig config = null;
		String connString = "";
		String query = "";
		String storeName = "";
		try {

			Class.forName("org.sqlite.JDBC");
			config = new org.sqlite.SQLiteConfig();
			config.setEncoding(org.sqlite.SQLiteConfig.Encoding.UTF8);
			
			
			//connString = "jdbc:sqlite:C:\\Users\\user\\Desktop\\restapi.db";
			connString = "jdbc:sqlite:/esl/web/webapps/ROOT/WEB-INF/classes/restapi.db";
			
			conn = DriverManager.getConnection(connString, config.toProperties());
			//conn = DriverManager.getConnection(connString);
			
			Statement statement = conn.createStatement();
			query = "SELECT info FROM g_config WHERE id='system_title'";
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				storeName = resultSet.getString("info");
			}
			
			System.out.println("Success DB connection & StoreName : " + storeName);//여기서는 잘나오는데 linux 에서는 깨져서 나옴...
			
			return storeName;
		}catch(Exception ex) {
			return ex.toString();
		}
		
	}
	
	public static boolean postRequest(String storeName, String ipAddr, String storeCode, String companyName) {
		try {
			
			//URL url = new URL("http://localhost:8188/api/storeinfo");
			URL url = new URL("http://61.33.142.222:8188/api/storeinfo");
			//String urlParameter = "storeName:" + storeName + "&ipAddr=" + ipAddr + "&storeCode=" + storeCode + "&companyName=" + companyName;
			//String postData = "{" + ;
			//String jsonInputString = "{"name":"storeName", "job":"programmer"}";
			
			org.json.simple.JSONObject bodyData = new org.json.simple.JSONObject();
			bodyData.put("storeName", storeName);
			bodyData.put("ipAddrNow", ipAddr);
			bodyData.put("ipAddrPrev", ""); //이거는 왜 보내는거여?
			bodyData.put("storeCode", storeCode);
			bodyData.put("companyName", companyName);
			
			
			//byte[] postData = bodyData.toString().getBytes(StandardCharsets.UTF_8);
			//int postDataLength = postData.length;
			
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			conn.setUseCaches(false);
			//conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//			conn.setRequestProperty("Content-Type", "application/json");
//			conn.setRequestProperty("charset", "utf-8");
			//conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
			
//			
//			try(DataOutputStream dos = new DataOutputStream(conn.getOutputStream())){
//				dos.writeUTF(bodyData.toJSONString());
//			}
//
//			DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
//			outputStream.writeBytes(bodyData.toString());
//			outputStream.flush();
//			outputStream.close();
			
			/*여기에 encoding 을 안해줬었음ㅋㅋㅋㅋ */
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
			bw.write(bodyData.toJSONString());
			bw.flush();
			bw.close();
			
			
			
			try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))){
				String line;
				
				while((line = br.readLine()) != null) {
					System.out.println(line);
				}
			}
			conn.disconnect();
			return true;
		}catch(Exception ex) {
			System.out.println(ex.toString());
			return false;
		}
	}
}
