package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import login.LoginController;

/**
 * 1. 패키지명 : main
 * 2. 타입명 : DBConnect.java
 * 3. 작성일 : 2015. 8. 17. 오후 8:31:44
 * 4. 작성자 : 주용민
 * 5. 설명 : DB접속을 위한 url,user,pwd를 따로 담아둔 클래스
 */
public class DBConnect {
	public static Connection conn;
	public static String url;
	public static String user;
	public static String pwd;

	/**
	 * 1. 메소드명 : connect
	 * 2. 작성일 : 2015. 8. 17. 오후 8:36:21
	 * 3. 작성자 : 주용민
	 * 4. 설명 :	동적바인딩 및 DB 연결
	 * @return
	 * @throws SQLException
	 */
	public static Connection connect() throws SQLException{
		try{
			//동적 바인딩 : 실행시점에 클래스파일을 다운로드 -> 로딩 ->객체생성
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();

			//연결된 DB에 쿼리 날릴 준비
			conn = DriverManager.getConnection(url,user,pwd);
		}catch(Exception e){
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 1. 메소드명 : getConnection
	 * 2. 작성일 : 2015. 8. 17. 오후 8:36:17
	 * 3. 작성자 : 주용민
	 * 4. 설명 :	
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static Connection getConnection() throws SQLException,ClassNotFoundException{
		if(conn != null && !conn.isClosed()) {
			//커넥션 값이 null값이 아니고, 커넥션이 닫히지 않았다면 다시 conn
			return conn;			
		}
		connect(); //연결
		return conn;
	}

}
