package update;

import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import login.LoginDto;
import main.MainController;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import util.ComUtil;
import config.DBConnect;

public class UpdateController implements Initializable {

    @FXML private VBox vBoxLabel;
    @FXML private VBox vBoxTextField;
    @FXML private VBox vBoxTextField2;
    @FXML private Button btnCancel;
    @FXML private Button btnGeneration;

    public Stage currentStage;
	public MainController mainController;
	
	// log4j 설정
	private static final Logger LOG = Logger.getLogger(LoginDto.class);
	
	/**
	 * 1. 메소드명 : exitUpdate
	 * 2. 작성일 : 2015. 8. 22. 오후 2:46:13
	 * 3. 작성자 : 주용민
	 * 4. 설명 :  update팝업 창에서 esc키를 누르면 취소버튼 누른 것과같은 기능
	 * @param event
	 */
	@FXML void exitUpdate(KeyEvent event){
		final KeyCombination key = new KeyCodeCombination(KeyCode.ESCAPE);
		if(key.match(event)) {
			cancel(null);
		}
	}
	
    @FXML 
    void cancel(ActionEvent event) {
		this.currentStage.close();
    }
	
    public void base(String tableName) throws Exception {
		Connection conn = DBConnect.connect();
		String sql = "select * from " + tableName;
		ResultSet rs = conn.createStatement().executeQuery(sql);

		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCnt = rsmd.getColumnCount();

		Label[] label = new Label[columnCnt];
		TextField[] textField = new TextField[columnCnt];
		TextField[] textFieldTerms = new TextField[columnCnt];

		for (int i = 0; i < columnCnt; i++) {
			vBoxLabel.getChildren().add(label[i] = new Label(rsmd.getColumnName(i + 1)));
			textField[i] = new TextField();
			textField[i].setMinWidth(200);
			textFieldTerms[i] = new TextField();
			textFieldTerms[i].setMinWidth(200);			
			
			vBoxTextField.getChildren().add(textField[i]);
			vBoxTextField2.getChildren().add(textFieldTerms[i]);
		}

		btnGeneration.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				VelocityEngine ve = new VelocityEngine();
				ve.setProperty("resource.loader", "classpath");
				ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
				ve.setProperty("input.encoding", "UTF-8");
				ve.init();
				
				Template t = ve.getTemplate("/template/update.vm");
				
				List<String> updateTemplate = new ArrayList<String>();			
				List<String> whereTemplate = new ArrayList<String>();
				
				try {
					for (int i = 0; i < columnCnt; i++) {	
						if (!textField[i].getText().trim().isEmpty()){
							//updateTemplate의 인덱스가 0보다 클때 , 컬럼명 텍스트필드값 0일때 , 제거	후 리스트 추가
							updateTemplate.add(updateTemplate.size() > 0 ? 																
									"\t, " + rsmd.getColumnName(i + 1) + " = '" + textField[i].getText().trim() + "'" :			
									"\t" + rsmd.getColumnName(i + 1) + " = '" + textField[i].getText().trim() + "'");  
							LOG.debug("UpdateController Table textField : " + textField[i]);
						}
						
						if (!textFieldTerms[i].getText().trim().isEmpty()){
						//조건절 텍스트 필드의 값 NULL체크 
							whereTemplate.add(whereTemplate.size() > 0 ? 
								//updateTemplate의 인덱스가 0보다 클때 AND 컬럼명 텍스트필드값 0일때 WHERE 추가	후 리스트 추가
								"AND " + rsmd.getColumnName(i + 1) + " = '" + textFieldTerms[i].getText().trim() + "'" :
								"WHERE " + rsmd.getColumnName(i + 1) + " = '" + textFieldTerms[i].getText().trim() + "'");  
							LOG.debug("UpdateController Table textFieldTerms : " + textFieldTerms[i]);			
						}
					}	
					
					if (updateTemplate.size() == 0) {
						ComUtil.alert("업데이트할 항목을 입력하세요.");
						return;
					}
		
					// 템플릿에 던져줄 데이터 만들기
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("tableName", mainController.getTblTableInfo()); // 테이블명 입력
					map.put("updates", updateTemplate); // SET 이하 데이터 던지기
					map.put("where", whereTemplate); // WHERE 이하 데이터 던지기
			
					// 이제 템플릿과 데이터를 합치자
					VelocityContext context = new VelocityContext(map);
			
					StringWriter writer = new StringWriter();
					t.merge(context, writer);
			
					mainController.setTxtPreView(writer.toString()); // 쿼리문을 소스창에 뿌린다.
					cancel(null);
					
				} catch (SQLException e){
					e.printStackTrace();
				}
			}
		});			
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}			
}
