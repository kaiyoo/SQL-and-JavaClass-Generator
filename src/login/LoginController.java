package login;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.MainController;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import util.ComUtil;
import util.FileUtil;
import config.DBConnect;

public class LoginController implements Initializable {

	@FXML private Button btnLogin;
	@FXML private Button btnSave;
	@FXML private Button btnCancel;
	@FXML private Button btnDelete;
	@FXML private Button btnReset;
	@FXML private TextField txtUserId;
	@FXML private TextField txtPassword;
	@FXML private TextField txtHost;
	@FXML private TextField txtPort;
	@FXML private TextField txtServiceName;
	@FXML private TableView<LoginDto> tblAccountInfo;	
	@FXML private TableColumn<LoginDto, String> colUserId;
    @FXML private TableColumn<LoginDto, String> colHost;
	
	public Stage currentStage;
	public MainController mainController;
	public ObservableList<LoginDto> accountList;
	public static final String TEMPLATE_PATH = "/template";
	public static final String SRC_PATH = "D:/workspace/workspace-java-expert/SGWannabe/src/config/";
	private static final Logger LOG = Logger.getLogger(LoginController.class);


	/**
	 * 1. 메소드명 : enterLogin
	 * 2. 작성일 : 2015. 8. 21. 오후 2:59:11
	 * 3. 작성자 : 주용민
	 * 4. 설명 :  TableView에서 접속하고자하는 ID클릭 후 Enter를 누르면 Login버튼 누른것과 같은 기능 실행
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void enterLogin(KeyEvent event) throws Exception {
		final KeyCombination key = new KeyCodeCombination(KeyCode.ENTER);
		if (key.match(event)) {
			login(null);
		}
	}
	
	
	/**
	 * 1. 메소드명 : clickLogin
	 * 2. 작성일 : 2015. 8. 21. 오후 3:37:39
	 * 3. 작성자 : 주용민
	 * 4. 설명 :  TableView에서 접속하고자하는 ID클릭 후 더블클릭을 하면 Login버튼 누른것과 같은 기능 실행 
	 * @param mouseEvent
	 * @throws Exception
	 */
	@FXML
	void clickLogin(MouseEvent mouseEvent) throws Exception {
		if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
			if (mouseEvent.getClickCount() == 2) {
				login(null);
			}
		}
	}
	
	/**
	 * 1. 메소드명 : escapeLogin
	 * 2. 작성일 : 2015. 8. 21. 오후 4:01:23
	 * 3. 작성자 : 주용민
	 * 4. 설명 :   login화면창에서 esc키를 눌렀다 떼면 cancle버튼 누른것과 같은 기능 실행
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void escapeLogin(KeyEvent event) throws Exception {
		final KeyCombination key = new KeyCodeCombination(KeyCode.ESCAPE);
		if (key.match(event)) {
			cancel(null);
		}
	}
	
	
	/**
	 * 1. 메소드명 : login
	 * 2. 작성일 : 2015. 8. 18. 오후 11:17:27
	 * 3. 작성자 : Jaeryeon Yang
	 * 4. 설명 : 입력된 정보로 로그인 처리
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void login(ActionEvent event) throws Exception {
		// 입력여부 체크
		if(!ComUtil.accountCheck(
				txtUserId.getText()
				, txtPassword.getText()
				, txtHost.getText()
				, txtPort.getText()
				, txtServiceName.getText())){
			return;
		}
		
		DBConnect.url = "jdbc:oracle:thin:@"+txtHost.getText()+":"+txtPort.getText()+":"+txtServiceName.getText();
		DBConnect.user = txtUserId.getText();
		DBConnect.pwd = txtPassword.getText();
		
		try {
			Connection conn = DBConnect.connect();
			conn.createStatement().close();
		} catch (Exception e) {
			ComUtil.alert("Connect fail!");
			return;
		}		
		
		mainController.flag = 1;
		mainController.initialize(null, null);
		this.currentStage.close();
	}
	
	
	/**
	 * 1. 메소드명 : save
	 * 2. 작성일 : 2015. 8. 18. 오전 12:15:12
	 * 3. 작성자 : Jaeryeon Yang
	 * 4. 설명 : 계정정보 파일 저장
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void save(ActionEvent event) throws Exception {
		// 입력여부 체크
		if(!ComUtil.accountCheck(
				txtUserId.getText()
				, txtPassword.getText()
				, txtHost.getText()
				, txtPort.getText()
				, txtServiceName.getText())){
			return;
		}
		
		// 계정 정보 저장할 파일
		File file = new File(SRC_PATH + "account.properties");
		String account = "";
		
		if(accountList.indexOf(tblAccountInfo.getSelectionModel().getSelectedItem()) != -1) {	// 리스트에서 선택된 항목이 있으면 수정
			FileUtil.input(file, null, "init");
			for (LoginDto loginDto : accountList) {
				if(loginDto.getUserId().equals(txtUserId.getText())) {
					account = setAccountList(txtUserId.getText(), txtPassword.getText(), txtHost.getText(), txtPort.getText(), txtServiceName.getText());									
				} else {
					account = setAccountList(loginDto.getUserId(), loginDto.getPassword(), loginDto.getHost(), loginDto.getPort(), loginDto.getServiceName());
				}
			
				FileUtil.input(file, account, "else");
				LOG.debug("Login loginDto : "+ loginDto);
			}
		} else {	// 신규
			account = setAccountList(txtUserId.getText(), txtPassword.getText(), txtHost.getText(), txtPort.getText(), txtServiceName.getText());				
			LOG.debug("Login Account : "+account);	
			// account.properties 파일에 추가
			FileUtil.input(file, account, "else");
		}

		reset(null);
		setTblAccountInfo();
		
	}
	
	@FXML
	void cancel(ActionEvent event) {
		this.currentStage.close();
	}
	
	@FXML
	void reset(ActionEvent event) {
		txtUserId.setText("");
		txtPassword.setText("");
		txtHost.setText("");
		txtPort.setText("");
		txtServiceName.setText("");	
		txtUserId.setDisable(false);
		
		setTblAccountInfo();
	}
	
	/**
	 * 1. 메소드명 : delete
	 * 2. 작성일 : 2015. 8. 20. 오전 9:41:05
	 * 3. 작성자 : Jaeryeon Yang
	 * 4. 설명 : 계정 정보 삭제
	 * @param event
	 * @throws Exception
	 */
	@FXML
    void delete(ActionEvent event) throws Exception {

		if(accountList.indexOf(tblAccountInfo.getSelectionModel().getSelectedItem()) == -1) {
			ComUtil.alert("항목을 선택하시오.");
			return;
		}
		
    	if(ComUtil.confirm("삭제하겠습니까?")) {
    		accountList.remove(accountList.indexOf(tblAccountInfo.getSelectionModel().getSelectedItem()));
    	}
    	
    	// 계정 정보 저장할 파일
		File file = new File(SRC_PATH + "account.properties");
		String account = "";
		
		// account.properties 파일 초기화
		FileUtil.input(file, null, "init");
		
    	for (LoginDto loginDto : accountList) {
    		account = setAccountList(loginDto.getUserId(), loginDto.getPassword(), loginDto.getHost(), loginDto.getPort(), loginDto.getServiceName());
    		
    		// account.properties 파일에 추가
    		FileUtil.input(file, account, "else");
    		LOG.debug("DeleteLogin loginDto : "+loginDto);
    	}
    	
    	reset(null);
		setTblAccountInfo();
    }
	
	
	/**
	 * 1. 메소드명 : setTblAccountInfo
	 * 2. 작성일 : 2015. 8. 20. 오전 9:23:56
	 * 3. 작성자 : Jaeryeon Yang
	 * 4. 설명 : 계정정보 리스트에 출력
	 */
	void setTblAccountInfo() {
		
		getAccountList();
		
		// 계정정보 리스트에 출력
		colUserId.setCellValueFactory( new PropertyValueFactory<LoginDto, String>("userId"));
		colHost.setCellValueFactory( new PropertyValueFactory<LoginDto, String>("host"));
		tblAccountInfo.setItems(accountList);	// 테이블뷰에 출력	
		
		// 리스트에서 선택된 정보를 로그인 정보에 맵핑 
		tblAccountInfo.getSelectionModel().selectedItemProperty().addListener (
			new ChangeListener<LoginDto>() {
				@Override
				public void changed(ObservableValue<? extends LoginDto> observalbe,
						LoginDto oldValue, LoginDto newValue) {
					if(newValue != null) {
						txtUserId.setText(newValue.getUserId());
						txtPassword.setText(newValue.getPassword());
						txtHost.setText(newValue.getHost());
						txtPort.setText(newValue.getPort());
						txtServiceName.setText(newValue.getServiceName());		
						
						txtUserId.setDisable(true);
					}
				}
			}
		);
		
	}
	
	
	/**
	 * 1. 메소드명 : getAccountList
	 * 2. 작성일 : 2015. 8. 20. 오전 9:19:47
	 * 3. 작성자 : Jaeryeon Yang
	 * 4. 설명 : 파일에서 계정정보 가져오기
	 */
	void getAccountList() {
		try {
			// 리스트 초기화
			accountList = FXCollections.observableArrayList();
			
			FileReader fr = new FileReader(SRC_PATH + "account.properties");
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			
			// Pattern
			String urlPattern = "url=(.*)";
			String idPattern = "userId=(.*)";
			String pwPattern = "password=(.*)";
			
			Pattern p1 = Pattern.compile(urlPattern);
			Pattern p2 = Pattern.compile(idPattern);
			Pattern p3 = Pattern.compile(pwPattern);
			
			String userId = "";
			String password = "";
			String host = "";
			String port = "";
			String serviceName = "";
			
			for (int i = 1; (line = br.readLine()) != null; i++) {
				
				Matcher m1 = p1.matcher(line);
				Matcher m2 = p2.matcher(line);
				Matcher m3 = p3.matcher(line);
						
				// url 파싱
				if(m1.find()) {						
					host = m1.group(1).split("@")[1].split(":")[0];
					port = m1.group(1).split("@")[1].split(":")[1];
					serviceName = m1.group(1).split("@")[1].split(":")[2];
				}
				
				// userId 파싱
				if(m2.find()) {
					userId = m2.group(1);
				}
				
				// password 파싱
				if(m3.find()) {
					password = m3.group(1);
					
					// password 파싱되면 accountList에 추가
					accountList.add(new LoginDto(userId, password, host, port, serviceName));
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 1. 메소드명 : setAccountList
	 * 2. 작성일 : 2015. 8. 20. 오전 9:41:55
	 * 3. 작성자 : Jaeryeon Yang
	 * 4. 설명 : velocity를 이용해서 계정 정보 문자열 생성
	 * @param userId
	 * @param password
	 * @param host
	 * @param port
	 * @param serviceName
	 * @return
	 */
	String setAccountList(String userId, String password, String host, String port, String serviceName) {
		VelocityEngine ve = new VelocityEngine();		
		ve.setProperty("resource.loader", "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.setProperty("input.encoding", "UTF-8");
		ve.init();
		
		// template 생성(계정정보)
		Template t = null;
		t = ve.getTemplate(TEMPLATE_PATH + "/accountInfo.vm");
		
		// 템플릿에 던져줄 데이터 만들기
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("userId", userId);
		map.put("password", password);		
		map.put("host", host);
		map.put("port", port);
		map.put("serviceName", serviceName);				
		
		// 템플릿 + 데이터
		VelocityContext context = new VelocityContext(map);
		
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		
		return writer.toString();
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			setTblAccountInfo();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}