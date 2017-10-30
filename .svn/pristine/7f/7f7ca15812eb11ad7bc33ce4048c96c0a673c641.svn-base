package main;

import insert.InsertController;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import login.LoginController;
import login.LoginDto;

import org.apache.log4j.Logger;

import select.SelectController;
import selectTest.SelectTestController;
import update.UpdateController;
import util.ComUtil;
import config.DBConnect;
import delete.DeleteController;
import file.FileController;

public class MainController implements Initializable {

	@FXML private Button btnLogin;
	@FXML private Button btnLogout;
	@FXML private Button btnFile;
	@FXML private Button btnInsert;
	@FXML private Button btnSelect;
	@FXML private Button btnUpdate;
	@FXML private Button btnDelete;
	@FXML private Button btnClear;
	@FXML private Button btnRun;
	@FXML private Label lblInfo;
	@FXML private Label lblResult;
	@FXML private TextArea txtPreView;
	@FXML private TableView tblDataGrid;
	@FXML private TableView<MainVo> tblTableInfo;
	@FXML private TableColumn tableName;
	
	@FXML private Button btnSelectTest;

	public int flag = 0; // 로그인의 여부를 나타내는 값(로그인안되있을시 0,로그인시 1)
	private int num; // 테이블의 컬럼의 갯수를 저장 해논 값
	// log4j 설정
	private static final Logger LOG = Logger.getLogger(LoginDto.class);
	
	
	@FXML
    void selectTest(ActionEvent event) throws Exception {
		if (tblTableInfo.getSelectionModel().getSelectedItem() == null) {
			ComUtil.alert("테이블을 선택하시오.");
			return;
		}
		
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setTitle("SELECT TEST");

		FXMLLoader loader = new FXMLLoader(getClass().getResource(
				"/selectTest/selectTest.fxml"));

		Parent parent = null;

		try {
			parent = loader.load();

			SelectTestController selectTestController = (SelectTestController)loader.getController();
			selectTestController.base(getTblTableInfo());
			selectTestController.currentStage = dialog; 
			selectTestController.mainController = this; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		dialog.setScene(new Scene(parent));
		dialog.setResizable(false);
		dialog.show();
    }
	
	
	/**
	 * 1. 메소드명 : openLoginPopup
	 * 2. 작성일 : 2015. 8. 19. 오후 3:10:15
	 * 3. 작성자 : Jaeryeon Yang
	 * 4. 설명 : 로그인 팝업
	 * @param event
	 */
	@FXML
	void openLoginPopup(ActionEvent event) throws Exception {
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setTitle("CONNECTION");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/login/login.fxml"));

		Parent parent = null;
		
		try {
			parent = loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}

		dialog.setScene(new Scene(parent));
		dialog.setResizable(false);
		dialog.show();

		LoginController loginController = (LoginController)loader.getController();
		loginController.currentStage = dialog;   // stage 정보를 넘겨줌
		loginController.mainController = this; // 부모창(MemberController)의 정보를 넘겨줌
	}

	
	/**
	 * 1. 메소드명 : logout
	 * 2. 작성일 : 2015. 8. 22. 오후 3:10:38
	 * 3. 작성자 : Jaeryeon Yang
	 * 4. 설명 : 로그아웃
	 * @param event
	 */
	@FXML
	void logout(ActionEvent event) throws Exception {
		DBConnect.conn.close();
		DBConnect.url = null;
		DBConnect.user = null;
		DBConnect.pwd = null;
				
		flag = 0;
		init();
	}
	
	
	/**
	 * 1. 메소드명 : insert
	 * 2. 작성일 : 2015. 8. 22. 오후 3:10:50
	 * 3. 작성자 : 오승민
	 * 4. 설명 : insert 쿼리 생성기 팝업
	 * @param event
	 */
	@FXML
	void insert(ActionEvent event) throws Exception {
		if (tblTableInfo.getSelectionModel().getSelectedItem() == null) {
			ComUtil.alert("테이블을 선택하시오.");
			return;
		}
		
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setTitle("INSERT");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/insert/insert.fxml"));

		Parent parent = null;

		try {
			parent = loader.load();
			InsertController insertController = (InsertController)loader.getController();
			insertController.base(getTblTableInfo());
			insertController.currentStage = dialog; 
			insertController.mainController = this; 
		} catch (Exception e) {
			e.printStackTrace();
		}

		dialog.setScene(new Scene(parent));
		dialog.setResizable(false);
		dialog.show();
	}

	
	/**
	 * 1. 메소드명 : select
	 * 2. 작성일 : 2015. 8. 22. 오후 3:11:28
	 * 3. 작성자 : 유홍상
	 * 4. 설명 : select 쿼리 생성기 팝업
	 * @param event
	 */
	@FXML
	void select(ActionEvent event) throws Exception {
		if (tblTableInfo.getSelectionModel().getSelectedItem() == null) {
			ComUtil.alert("테이블을 선택하시오.");
			return;
		}
		
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setTitle("SELECT");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/select/select.fxml"));

		Parent parent = null;

		try {
			parent = loader.load();
			
			SelectController selectController = (SelectController)loader.getController();
			selectController.base(getTblTableInfo());
			selectController.currentStage = dialog; 
			selectController.mainController = this; 
		} catch (Exception e) {
			e.printStackTrace();
		}

		dialog.setScene(new Scene(parent));
		dialog.setResizable(false);
		dialog.show();
	}

	
	/**
	 * 1. 메소드명 : update
	 * 2. 작성일 : 2015. 8. 22. 오후 3:11:44
	 * 3. 작성자 : 유혜수
	 * 4. 설명 : update 쿼리 생성기 팝업
	 * @param event
	 */
	@FXML
	void update(ActionEvent event) throws Exception {
		if (tblTableInfo.getSelectionModel().getSelectedItem() == null) {
			ComUtil.alert("테이블을 선택하시오.");
			return;
		}
		
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setTitle("UPDATE");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/update/update.fxml"));

		Parent parent = null;

		try {
			parent = loader.load();

			UpdateController updateController = (UpdateController)loader.getController();
			updateController.base(getTblTableInfo());
			updateController.currentStage = dialog; 
			updateController.mainController = this; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		dialog.setScene(new Scene(parent));
		dialog.setResizable(false);
		dialog.show();
	}
	

	/**
	 * 1. 메소드명 : delete
	 * 2. 작성일 : 2015. 8. 21. 오후 2:47:20
	 * 3. 작성자 : 한동균	
	 * 4. 설명 : delete 쿼리 생성기 팝업
	 * @param event
	 */
	@FXML
	void delete(ActionEvent event) throws Exception {
		if (tblTableInfo.getSelectionModel().getSelectedItem() == null) {
			ComUtil.alert("테이블을 선택하시오.");
			return;
		}
		
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setTitle("DELETE");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/delete/delete.fxml"));

		Parent parent = null;

		try {
			parent = loader.load();
			DeleteController deleteController=(DeleteController)loader.getController();
			deleteController.currentStage = dialog; 
			deleteController.base(getTblTableInfo());
			deleteController.mainController = this; 
		} catch (Exception e) {
			e.printStackTrace();
		}

		dialog.setScene(new Scene(parent));
		dialog.setResizable(false);
		dialog.show();
	}
	
	
	/**
	 * 1. 메소드명 : file
	 * 2. 작성일 : 2015. 8. 19. 오후 3:58:20
	 * 3. 작성자 : 박훈범
	 * 4. 설명 : file 생성기 팝업
	 * @param event
	 */
	@FXML
	void file(ActionEvent event) throws Exception {
		if (tblTableInfo.getSelectionModel().getSelectedItem() == null) {
			ComUtil.alert("테이블을 선택하시오.");
			return;
		}
		
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setTitle("파일 생성");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/file/file.fxml"));

		Parent parent = null;

		try {
			parent = loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}

		dialog.setScene(new Scene(parent));
		dialog.setResizable(false);
		dialog.show();
		
		FileController fileController = (FileController)loader.getController();
		fileController.currentStage = dialog; 
		fileController.mainController = this; 
	}
	

	/**
	 * 1. 메소드명 : setTable
	 * 2. 작성일 : 2015. 8. 21. 오후 5:05:41
	 * 3. 작성자 : 주용민
	 * 4. 설명 : 테이블리스트 출력
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void setTable() throws Exception {
		Connection conn = DBConnect.connect();
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM TAB";

			rs = stmt.executeQuery(sql);
			ObservableList<MainVo> rowlist = FXCollections.observableArrayList();
			
			tableName = new TableColumn("TableName");			
			tableName.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {

				public ObservableValue<String> call(
						CellDataFeatures<ObservableList, String> param) {
							return new SimpleStringProperty(param.getValue().get(0).toString());
						}
			});
			
			tableName.setMinWidth(209);
			
			// tblTableInfo 에 tableName 컬럼 추가
			tblTableInfo.getColumns().addAll(tableName);			
			
			while (rs.next()) {
				MainVo temp = new MainVo(rs.getString(1));
				rowlist.add(temp);
				tableName.setCellValueFactory(new PropertyValueFactory<MainVo, String>("tableName"));
				tblTableInfo.setItems(rowlist);
			}
			
			tblTableInfo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MainVo>() {
				@Override
				public void changed(
						ObservableValue<? extends MainVo> observable,
						MainVo oldValue, MainVo newValue) {

					try {
						// 선택된 테이블명으로 데이터 그리드에 출력
						if (tblTableInfo.getSelectionModel().getSelectedItem() != null) {
							lblResult.setText("");
							setColTable("select * from " + getTblTableInfo());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	/**
	 * 1. 메소드명 : setColTable
	 * 2. 작성일 : 2015. 8. 19. 오후 3:16:01
	 * 3. 작성자 : 주용민
	 * 4. 설명 : 선택된 테이블의 데이터 출력
	 * @param query
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setColTable(String query) throws Exception {
		try {
			Connection conn = DBConnect.connect();
			
			String sql = query;
			
			if(sql.charAt(0) == 'S' || sql.charAt(0) == 's') {
				tblDataGrid.getColumns().clear();
				ResultSet rs = conn.createStatement().executeQuery(sql);
				num = rs.getMetaData().getColumnCount();
				ObservableList col = FXCollections.observableArrayList();
				ObservableList<ObservableList> row = FXCollections.observableArrayList();
				
				for (int i = 0; i < num; i++) {
					col.add(rs.getMetaData().getColumnName(i + 1));
				}
				
				// row 생성 값 이 null 일시 ""로 출력
				while (rs.next()) {
					ObservableList rowlist = FXCollections.observableArrayList();

					for (int i = 1; i <= num; i++) {
						String str = null;
						if (rs.getString(i) == null) {
							str = "";
							// null 시 ""빈칸을 출력
						} else {
							str = rs.getString(i);
						}
						rowlist.add(str);
					}
					LOG.debug("RUN - " + query);
					row.add(rowlist);
				}

				for (int i = 0; i < num; i++) {
					final int j = i;
					TableColumn tableCol = new TableColumn(col.get(i).toString());
					tableCol.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {

						public ObservableValue<String> call(
								CellDataFeatures<ObservableList, String> param) {
									return new SimpleStringProperty(param.getValue().get(j).toString());
								}
					});
					
					// sql의 필드명 을 table 에 추가
					tblDataGrid.getColumns().addAll(tableCol);
				}
				
				// 테이블에 row 값
				tblDataGrid.setItems(row);
			} else {
				int resultCnt = conn.createStatement().executeUpdate(sql);
				String resultMessage = "";
				
				if(sql.charAt(0) == 'I' || sql.charAt(0) == 'i') {
					resultMessage = " row inserted.";
				} else if (sql.charAt(0) == 'U' || sql.charAt(0) == 'u'){
					resultMessage = " row updated.";
				} else if (sql.charAt(0) == 'D' || sql.charAt(0) == 'd') {
					resultMessage = " row deleted.";
				}
				
				lblResult.setText(">> " + resultCnt + resultMessage);
				lblResult.setTextFill(Paint.valueOf("BLUE"));
//				setColTable("select * from " + getTblTableInfo());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			lblResult.setText(">> " + e.getMessage());
			lblResult.setTextFill(Paint.valueOf("RED"));
		}
	}

	
	/**
	 * 1. 메소드명 : getTblTableInfo
	 * 2. 작성일 : 2015. 8. 20. 오후 7:34:35
	 * 3. 작성자 : Jaeryeon Yang
	 * 4. 설명 : 선택된 테이블명 vo 리턴
	 * @return
	 */
	public String getTblTableInfo() {
		return tblTableInfo.getSelectionModel().getSelectedItem().getTableName(); 
	}
	
	
	/**
	 * 1. 메소드명 : setTxtPreView
	 * 2. 작성일 : 2015. 8. 22. 오후 3:16:52
	 * 3. 작성자 : Jaeryeon Yang
	 * 4. 설명 : 쿼리창에 텍스트 출력
	 * @param text
	 * @throws Exception
	 */
	public void setTxtPreView(String text) {
		txtPreView.setText(text);
	}

	
	/**
	 * 1. 메소드명 : run
	 * 2. 작성일 : 2015. 8. 21. 오후 5:05:00
	 * 3. 작성자 : Jaeryeon Yang
	 * 4. 설명 : txtPreview에 입력된 쿼리 수행
	 * @param event
	 */
	@FXML
	void run(ActionEvent event) throws Exception {
		LOG.debug(txtPreView.getText());
		if(!txtPreView.getText().trim().isEmpty()) {
			String[] query = txtPreView.getText().split(";");
			
			for (String str : query) {
				lblResult.setText("");
				if(!str.trim().isEmpty()) {
					setColTable(str.trim());					
				}
			}
		}
	}
	
	
	/**
	 * 1. 메소드명 : clear
	 * 2. 작성일 : 2015. 8. 21. 오후 5:05:21
	 * 3. 작성자 : Jaeryeon Yang
	 * 4. 설명 : txtPreview 비우기
	 * @param event
	 */
	@FXML
    void clear(ActionEvent event) throws Exception {
		if(ComUtil.confirm("지울래?")){
			txtPreView.setText("");
			lblResult.setText("");
			btnClear.setDisable(true);
			btnRun.setDisable(true);
		}
    }
	
	
	/**
	 * 1. 메소드명 : enterRun
	 * 2. 작성일 : 2015. 8. 21. 오후 2:43:41
	 * 3. 작성자 : 주용민
	 * 4. 설명 :  TextArea에 출력된 Query를 Ctrl+Enter누르면 Run버튼 누른것과 같은 기능 실행
	 * @param event
	 * @throws Exception
	 */
	@FXML
	private void enterRun(KeyEvent event) throws Exception {
		final KeyCombination key = new KeyCodeCombination(KeyCode.ENTER,
				KeyCombination.CONTROL_DOWN);
		if(key.match(event)) {
			run(null);
		}
		
		if(txtPreView.getText().trim().isEmpty()) {
			btnClear.setDisable(true);
			btnRun.setDisable(true);
		} else {
			btnClear.setDisable(false);
			btnRun.setDisable(false);
		}
	}
	
	
	/**
	 * 1. 메소드명 : init
	 * 2. 작성일 : 2015. 8. 21. 오후 7:06:57
	 * 3. 작성자 : Jaeryeon Yang
	 * 4. 설명 : button / input 초기화
	 */
	public void init() throws Exception {
		try {
			if(flag != 0) {
				LOG.debug(DBConnect.url.split("@")[1].split(":")[0]);
				if(DBConnect.url.split("@")[1].split(":")[0].equals("localhost") 
						|| DBConnect.url.split("@")[1].split(":")[0].equals("127.0.0.1")) {
					btnInsert.setDisable(false);
					btnUpdate.setDisable(false);
					btnDelete.setDisable(false);
					btnFile.setDisable(false);					
					txtPreView.setEditable(true);
				} 
				
				lblInfo.setText("Hello, " + DBConnect.user);
				btnLogin.setVisible(false);
				btnLogout.setVisible(true);
				
				tblTableInfo.setDisable(false);
				tblDataGrid.setDisable(false);
				btnSelect.setDisable(false);
				txtPreView.setDisable(false);
				
				setTable();
			} else {
				lblInfo.setText("Please Log On");
				btnLogin.setVisible(true);
				btnLogout.setVisible(false);
				
				tblTableInfo.setDisable(true);
				tblDataGrid.setDisable(true);
				btnInsert.setDisable(true);
				btnSelect.setDisable(true);
				
				btnUpdate.setDisable(true);
				btnDelete.setDisable(true);
				btnFile.setDisable(true);
				btnClear.setDisable(true);				
				btnRun.setDisable(true);				
				txtPreView.setText("");
				lblResult.setText("");
				txtPreView.setDisable(true);
				txtPreView.setEditable(false);
				
				tblTableInfo.getColumns().clear(); 
				tblDataGrid.getColumns().clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)  {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
