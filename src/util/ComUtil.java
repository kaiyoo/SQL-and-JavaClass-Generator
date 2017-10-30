package util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class ComUtil {
	
	/**
	 * 1. 메소드명 : alert
	 * 2. 작성일 : 2015. 8. 19. 오전 9:30:01
	 * 3. 작성자 : Jaeryeon Yang
	 * 4. 설명 : 확인 메시지
	 * @param msg
	 */
	public static void alert(String msg) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("확인");
		alert.setContentText(msg);
		
		if(alert.showAndWait().get() == ButtonType.OK)  {
			
		}
	}
	
	
	/**
	 * 1. 메소드명 : confirm
	 * 2. 작성일 : 2015. 8. 19. 오전 9:30:16
	 * 3. 작성자 : Jaeryeon Yang
	 * 4. 설명 : confirm 메시지
	 * @param msg
	 * @return
	 */
	public static boolean confirm(String msg) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("확인");
		alert.setContentText(msg);
		
		if(alert.showAndWait().get() == ButtonType.OK)  {
			return true;
		}
		
		return false;
	}
	
	public static boolean accountCheck(String userId, String password, String host, String port, String serviceName) {
		
		if(userId.isEmpty()) {
			ComUtil.alert("이름을 입력하시오.");
			return false;
		}
		if(password.isEmpty()) {
			ComUtil.alert("비밀번호를 입력하시오.");
			return false;
		}
		if(host.isEmpty()) {
			ComUtil.alert("Host를 입력하시오.");
			return false;
		}
		if(port.isEmpty()) {
			ComUtil.alert("Port를 입력하시오.");
			return false;
		}
		if(serviceName.isEmpty()) {
			ComUtil.alert("Service Name을 입력하시오.");
			return false;
		}
		
		return true;
	}
}
