package insert;

import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
import main.MainController;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import util.ComUtil;
import config.DBConnect;

public class InsertController implements Initializable {

	@FXML private VBox vBoxLabel;
	@FXML private Button btnCancel;
	@FXML private Button btnGeneration;
	@FXML private VBox vBoxTextField;

	public Stage currentStage;
	public MainController mainController;

	
	/**
	 * 1. 메소드명 : exitUpdate
	 * 2. 작성일 : 2015. 8. 22. 오후 2:46:13
	 * 3. 작성자 : 주용민
	 * 4. 설명 :  update팝업 창에서 esc키를 누르면 취소버튼 누른 것과같은 기능
	 * @param event
	 */
	@FXML void exitInsert(KeyEvent event){
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

		for (int i = 0; i < columnCnt; i++) {
			vBoxLabel.getChildren().add(label[i] = new Label(rsmd.getColumnName(i + 1)));
			textField[i] = new TextField();
			textField[i].setMinWidth(200);
			vBoxTextField.getChildren().add(textField[i]);
		}

		btnGeneration.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				VelocityEngine ve = new VelocityEngine();
				ve.setProperty("resource.loader", "classpath");
				ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
				ve.setProperty("input.encoding", "UTF-8");
				ve.init();

				// 이제 템플릿 파일을 읽어보자.
				Template t = ve.getTemplate("/template/insert.vm");

				int valueCnt = 0;
				List<String> columnName = new ArrayList<String>();
				List<String> columnValues = new ArrayList<String>();
				
				try {
					for (int i = 0; i < columnCnt; i++) {
						if (textField[0].getText().isEmpty() && i==0) {						//첫번쨰 값이 공백일때 알림창 배출
							ComUtil.alert(label[0].getText() + "에 값을 입력하시오.");
							return;
						}
						if(!textField[i].getText().trim().isEmpty()){
							columnName.add(rsmd.getColumnName(i + 1));
							columnValues.add(textField[i].getText().trim());
							valueCnt++;
						}
					}
				
				// 템플릿에 던져줄 데이터 만들기
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("tableName", tableName); // 테이블명 입력
				map.put("columnName", columnName); // 컬럼 이름 입력
				map.put("columnValues", columnValues); // 컬럽 값 입력.
				map.put("columnLength", valueCnt);
				
				// 이제 템플릿과 데이터를 합치자
				VelocityContext context = new VelocityContext(map);

				StringWriter writer = new StringWriter();
				t.merge(context, writer);

				mainController.setTxtPreView(writer.toString()); // 쿼리문을 소스창ㅇㅔ 뿌린다.
				cancel(null);
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
}
