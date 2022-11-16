package com.kh.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {

	// JDBC과정 중 반복적으로 쓰이는 구문들을 각각의 메소드로 정의해둘 곳
	// "재사용할 목적"으로 공통 템플릿 작업 진행
	
	// 이 클래스에서의 모든 메소드들은 전부 다 static메소드로 만들것!
	// 싱글톤패턴 : 메모리 영역에 단 한번만 올라간것을 재사용한 개념
	
	// 공통적인 부분 뽑아내기
	// 1. DB와 접속된 Connection객체를 생성해서 반환시켜주는 메소드
	public static Connection/*connection같은 객체 돌려줌*/ getConnection/*커넥션내놔 메소드*/() {
		
		// Connection 객체를 담을 그릇 생성
		Connection conn = null;
		/* 기존의 방식 : JDBC Driver구문, 내가 접속할 DB의 ur1정보, 계정명, 비밀번호 등을
		 * 			  자바 소스코드 내부에 명시적으로 작성함 => 정적코딩방식(하드코딩)
		 * 
		 * - 문제점 : DBMS가 변경되었을 경우 / 접속할 ur1, 계정명, 비밀번호가 변경되었을경우
		 * 			=> 자바소스코드를 수정해야함
		 * 			수정한 코드를 반영시키고자 하면 프로그램을 재구동을 해야함
		 * 			(사용자입장에서 프로그램 사용중 비정상적으로 종료되었다가 다시 구동될 수 있음)
		 * 			* 유지보수가 불편함
		 * 
		 * - 해결방식 : DB관련된 정보들을 별도로 관리하는 외부파일(driver.properties)로 만들어서 관리
		 * 			  외부파일로 key에 대한 value를 읽어들여서 반영시킬것 => 동적코딩방식 
		 */
		
		Properties prop = new Properties();
		
		// 읽어들이고자 하는 파일의 물리적인 경로
		String fileName = JDBCTemplate.class.getResource("/sql/driver/driver.properties").getPath();
		
		// prop객체로부터 load메소드를 이용해서 각 키에 해당하는 벨류를 가져오겠다
		try {
			prop.load(new FileInputStream(fileName));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			// 1, 2) => 연결시키기
			Class.forName(prop.getProperty("driver"));
			
			conn = DriverManager.getConnection(prop.getProperty("url"),      
											   prop.getProperty("username"),
											   prop.getProperty("password"));
			// getConnection을 부르는 순간 위 식들을 호출~~
			// properties전까지는 "jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC" 이렇게 적었다
			// property에 us1, username, userpassword함으로써 어느 위치에 넣는지에 관계없이 사용할 수 있다~
			// Dao의 28행으로 PreparedStatement pstmt = null; 밑으로 이동~
			conn.setAutoCommit(false);   // <-- 해도되고 안해도 되고~
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;   // <-- getConnection/*커넥션내놔 메소드*/() 이 친구 부르면 conn객체를 돌려줄것~
	} // getConnection end
	
	// 2. 전달받은 Connection 객체를 가지고 트랜잭션 처리를 해주는 메소드
	// 2_1) 전달받은 Connection 객체를 가지고 COMMIT시켜주는 메소드
	public static void commit(Connection conn/*나를부르려면 커넥션객체를 전달시켜줘야해~*/) {
		
		try {
			if(conn != null && !conn.isClosed()) {  // conn.isClosed()는 열리면 true, 닫히면 false 주는기능
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // commit end
	
	// 2_2) 전달받은 Connection 객체를 가지고 ROLLBACK 시켜주는 메소드
	public static void rollback(Connection conn/*나를부르려면 커넥션객체를 전달시켜줘야해~*/) {
		
		try {
			if(conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // rollback end
	
	// 3. 전달받은 JDBC용 객체를 반납시켜주는 메소드(각 객체별로)
	// 3_1) Connection 객체를 전달받아서 반납시켜주는 메소드
	public static void close(Connection conn) {
		
		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	} // close end
	
	// 3_2) Statement 객체를 전달받아서 반납시켜주는 메소드(오버로딩 적용, 매개변수의 개수나 자료형에따라서 다르게 선언가능)
	// => PreparedStatement는 Statement의 자식이니 다형성으로 인해 PreparedStatement객체 또한 매개변수로 전달이 가능
	public static void/*반환형 필요없다*/ close(Statement stmt) {  //Statement import해야
		try {
			if(stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // close end
	
	// 3_3) ResultSet 객체를 전달받아서 반납시켜주는 메소드(오버로딩 적용)
	public static void close(ResultSet rset) {
		try {
			if(rset != null && !rset.isClosed()) {
				rset.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // close end
} // Class end