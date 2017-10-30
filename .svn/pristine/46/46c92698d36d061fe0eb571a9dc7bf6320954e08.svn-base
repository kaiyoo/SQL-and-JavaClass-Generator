package delete;

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

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import config.DBConnect;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.MainController;

public class DeleteController implements Initializable {

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
	@FXML void exitDelete(KeyEvent event){
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
		CheckBox[] checkBox = new CheckBox[columnCnt];

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
				Template t = ve.getTemplate("/template/delete.vm");

				List<String> whereTemplate = new ArrayList<String>();

				// vm template에 넘겨줄 데이터 추출
				try {
					for (int i = 0; i < columnCnt; i++) {
						if (!textField[i].getText().trim().isEmpty()) {
							whereTemplate.add(whereTemplate.size() > 0
									? "AND " + rsmd.getColumnName(i + 1) + " = '" + textField[i].getText().trim() + "'"
									: "WHERE " + rsmd.getColumnName(i + 1) + " = '" + textField[i].getText().trim()
											+ "'");
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				
				// 템플릿에 던져줄 데이터 만들기
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("vars", whereTemplate);
				map.put("tableName", tableName);

				// 이제 템플릿과 데이터를 합치자
				VelocityContext context = new VelocityContext(map);

				StringWriter writer = new StringWriter();
				t.merge(context, writer);

				// 쿼리문을 메인화면 쿼리창에 출력
				mainController.setTxtPreView(writer.toString());
				cancel(null);
			}
		});
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
}
