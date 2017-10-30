package file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import main.MainController;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import util.ComUtil;
import config.DBConnect;

public class FileController implements Initializable {

	@FXML 	private TextField 	txtPackageName;
    @FXML	private CheckBox 	chkController;
    @FXML 	private CheckBox 	chkDao;
    @FXML 	private CheckBox 	chkService;
    @FXML 	private CheckBox 	chkXml;
    @FXML 	private CheckBox 	chkDto;
    
    public Stage currentStage;
    public MainController mainController;
    
    public static final String TEMPLATE_PATH = "/template";
	public static final String SRC_PATH = "D:/workspace/workspace-java-expert/SGWannabe/src";
	
	
	/**
	 * 1. 메소드명 : exitUpdate
	 * 2. 작성일 : 2015. 8. 22. 오후 2:46:13
	 * 3. 작성자 : 주용민
	 * 4. 설명 :  file팝업 창에서 esc키를 누르면 취소버튼 누른 것과같은 기능
	 * @param event
	 */
	@FXML void exitFile(KeyEvent event){
		final KeyCombination key = new KeyCodeCombination(KeyCode.ESCAPE);
		if(key.match(event)) {
			cancel(null);
		}
	}
	
	/**
     * 1. 메소드명 : fileCreate
     * 2. 작성일 : 2015. 8. 19. 오후 2:48:33
     * 3. 작성자 : 박훈범
     * 4. 설명 :	생성 메소드 구현
     * @param event
     * @throws Exception
     */
    @FXML   
    void fileCreate(ActionEvent event) throws Exception {
    	
    	if(txtPackageName.getText().trim().isEmpty()) {
    		ComUtil.alert("package명을 입력하세요.");
    		return;
    	}
    	
    	if(txtPackageName.getText().indexOf(' ') != -1) {
    		ComUtil.alert("package명에 공백이 들어갈 수 없습니다..\n다시 입력 하세요.");
    		return;
    	}
    	
    	if(!((txtPackageName.getText().trim().charAt(0) >= 'A' && txtPackageName.getText().trim().charAt(0) <= 'Z')
    			|| (txtPackageName.getText().trim().charAt(0) >= 'a' && txtPackageName.getText().trim().charAt(0) <= 'z'))) {

    		ComUtil.alert("잘못 입력 하셨습니다.\n다시 입력 하세요.");
    		return;
    	}
    	
//    	if(chkController.isSelected() || ) {
//    		
//    	}
    	
    	String tableName = mainController.getTblTableInfo();
    	
    	try {
    		// controller 체크박스가 true 면 writeTempleate 메소드 호출
    		if(chkController.isSelected()) {				
    			writeTemplate("Controller", tableName);
    		}

    		// dao 체크박스가 true 면 writeTempleate 메소드 호출
    		if(chkDao.isSelected()) {					
    			writeTemplate("Dao", tableName);
    		}

    		// service 체크박스가 true 면 writeTempleate 메소드 호출
    		if(chkService.isSelected()) {				
    			writeTemplate("Service", tableName);
    		}

    		// dto 체크박스가 true 면 writeTempleate 메소드 호출
    		if(chkDto.isSelected()) {					
    			writeTemplate("Dto", tableName);
    		}

    		// xml 체크박스가 true 면 writeTempleate 메소드 호출
    		if(chkXml.isSelected()) {					
    			writeTemplate("xml", tableName);
    		}

    		ComUtil.alert("파일이 생성되었습니다.");

    	} catch (Exception e) {
    		e.printStackTrace();
    		ComUtil.alert("파일을 생성하는데 실패하였습니다.");
    	}
    	
    }
    	
    
    /**
     * 1. 메소드명 : writeTemplate
     * 2. 작성일 : 2015. 8. 19. 오후 2:48:05
     * 3. 작성자 : 박훈범
     * 4. 설명 :	파일 생성 메소드
     * @param fileType
     * @param tableName
     * @throws Exception
     */
    public  void writeTemplate(String fileType, String tableName) throws Exception {
		
		// 아래의 내용은 velocity를 사용하기 위해서 꼭 해줘야 하는 설정
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty("resource.loader", "classpath");
		ve.setProperty("classpath.resource.loader.class",
				ClasspathResourceLoader.class.getName());
		ve.setProperty("input.encoding", "UTF-8");
		ve.init();
		

		// 템플릿 파일 읽기
		Template t = null;	

		if (fileType.equals("Controller")) {
			t = ve.getTemplate(TEMPLATE_PATH + "/controller.vm");
		} else if (fileType.equals("Service")) {
			t = ve.getTemplate(TEMPLATE_PATH + "/service.vm");
		} else if (fileType.equals("Dao")) {
			t = ve.getTemplate(TEMPLATE_PATH + "/dao.vm");
		} else if (fileType.equals("Dto")) {
			t = ve.getTemplate(TEMPLATE_PATH + "/dto.vm");
		} else if (fileType.equals("xml")) {
			t = ve.getTemplate(TEMPLATE_PATH + "/xml.vm");
		}
		
		//packageName, methodName, bizName, tableName 정보를 담을 map 객체 생성
		Map<String, Object> map = new HashMap<String, Object>();
		
		// map 객체에 정보 담기
		map.put("packageName"	, txtPackageName.getText()); 
		map.put("methodName"	, txtPackageName.getText());
		map.put("bizName"		, txtPackageName.getText().substring(0, 1).toUpperCase() + txtPackageName.getText().substring(1));
		map.put("tableName"		, tableName);
		
		String[] column = null;									//culumn정보담을 배열 생성
		String[] values = null;									//textfield value 정보 담을 배열 생성
		String[] method = null;									//method이름 정보담을 배열 생성
		int colCnt = 0;
		
		Connection conn;

		try {
			conn = DBConnect.connect();
			
			String sql = "select * from " + tableName;
			ResultSet rs = conn.createStatement().executeQuery(sql);
			
			colCnt = rs.getMetaData().getColumnCount();
			
			column = new String[colCnt];						//각 배열들 초기화
			values = new String[colCnt];
			method = new String[colCnt];
			
			//DB의 컬럼명과 텍스트 필드의 벨류값 method명 배열에 저장
			for (int i = 0; i < colCnt; i++) {
				column[i] = rs.getMetaData().getColumnName(i + 1);
				values[i] = getCamelNotation(rs.getMetaData().getColumnName(i + 1), "_");	
				method[i] = getPascalNotation(rs.getMetaData().getColumnName(i + 1), "_");
			}
			
			
		} catch (SQLException e) {
					e.printStackTrace();
				}
		//해당 velocity 의 vars 변수에 값 전달
		map.put("cols",column);
		map.put("vars",values);
		map.put("methods",method);
		map.put("colCnt", colCnt-1);
		
		// 템플릿과 데이터 합치기
		VelocityContext context = new VelocityContext(map);

		StringWriter writer = new StringWriter();
		t.merge(context, writer);

		// 콘솔 출력
		System.out.println(writer.toString());

		// --------
		// 파일로 저장
		// -------
		
		//저장될 패키지 경로 설정
		String dirName = SRC_PATH + "/" + txtPackageName.getText() + "/";
		
		// /src/velocity/sample3/(bizName)+(fileType).java 가 생성
		String fileName;
		
		if(fileType.equals("xml")){
			fileName = dirName + txtPackageName.getText() + ".xml";
		} else{
			fileName = dirName + txtPackageName.getText().substring(0, 1).toUpperCase() + txtPackageName.getText().substring(1) + fileType + ".java";
		}

		// 디렉토리가 존재하는지 체크
		File dir = new File(dirName);
		
		if (!dir.exists()) {
			dir.mkdirs();
		}

		BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));

		t.merge(context, bw);
		bw.flush();
		bw.close();
				
		this.currentStage.close();
		
		
	}
    
    @FXML    
    void cancel(ActionEvent event) {
    	this.currentStage.close();
    }
    
    
    /**
     * 1. 메소드명 : getCamelNotation
     * 2. 작성일 : 2015. 8. 20. 오후 7:53:26
     * 3. 작성자 : 배진상
     * 4. 설명 : 카멜표기법으로 변환
     * @param str
     * @param seperator
     * @return
     */
    public static String getCamelNotation(String str, String seperator) {
		StringBuffer retVal = new StringBuffer();
		if (!str.isEmpty()) {
			String[] strArr = str.split(seperator);
			for (int idx = 0; idx < strArr.length; idx++) {
				if (idx == 0) {
					retVal.append(strArr[idx].toLowerCase());
				} else {
					retVal.append(strArr[idx].toUpperCase().charAt(0) + strArr[idx].toLowerCase().substring(1));
				}
			}
		}
		return retVal.toString();
	}
	

    /**
	 * 1. 메소드명 : getPascalNotation
	 * 2. 작성일 : 2015. 8. 20. 오후 7:53:43
	 * 3. 작성자 : 배진상
	 * 4. 설명 : 파스칼 표기법으로 변환
	 * @param str
	 * @param seperator
	 * @return
	 */
	public static String getPascalNotation(String str, String seperator) {
		StringBuffer retVal = new StringBuffer();
		if(!str.isEmpty()) {
			String[] strArr = str.split(seperator);
			for(int idx = 0; idx < strArr.length ; idx++) {
				retVal.append(strArr[idx].toUpperCase().charAt(0) + strArr[idx].toLowerCase().substring(1));
			}
		}
		return retVal.toString();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}
}
