package com.kh.member.model.service;

import java.sql.Connection;

import javax.swing.text.StyledEditorKit.BoldAction;

import com.kh.common.JDBCTemplate;
import com.kh.member.model.dao.MemberDao;
import com.kh.member.model.vo.Member;

public class MemberService {

	public Member loginMember(String userId, String userPwd) {
		
		// Service => Connection객체 생성
		
		// 1) Connection객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) Controller에서 넘어온 전달값과 커넥션 객체를 가지고 DAO 호출
		Member m = new MemberDao().loginMember(conn, userId, userPwd);
		
		// 3) Connection 객체 반납
		JDBCTemplate.close(conn);
		
		// 4) Controller로 결과 넘기기
		return m;
	}

	public int insertMember(Member m) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new MemberDao().insertMember(conn, m);
		// 성공했다면 1, 실패하면 0리턴
		
		if(result > 0) {
			JDBCTemplate.commit(conn); 
		} else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		
		return result;
	}

	public Member updateMember(Member m) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new MemberDao().updateMember(conn, m);
		
		Member updateMem = null;
//		result > 0 ? JDBCTemplate.commit(conn) : JDBCTemplate.rollback(conn);
		
		if(result > 0) {
			JDBCTemplate.commit(conn);
			// 갱신된 회원 객체를 다시 조회해오기
			updateMem = new MemberDao().selectMember(conn, m.getUserId());
		} else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		
		return updateMem;
	}

	public Member updatePwdMember(String userId, String userPwd, String updatePwd) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		// 비밀번호 update관련 DAO메소드 호출
		int result = new MemberDao().updatePwdMember(conn, userId, userPwd, updatePwd);
		
		// 호출 결과에 따라서 성공이면 COMMIT후에 새로 바뀐 회원정보를 다시 받아올 것
		Member updateMem = null;
		
		if(result > 0) { // 성공
			JDBCTemplate.commit(conn);
			updateMem = new MemberDao().selectMember(conn, userId);
			
		} else { // 실패
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		
		return updateMem;
		
	}

	public int deleteMember(String userId, String userPwd) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		// 회원탈퇴 관련 DAO메소드 호출
		int result = new MemberDao().deleteMember(conn, userId, userPwd);
				
		// conn = result > 0 ? JDBCTemplate.commit(conn) : JDBCTemplate.close(conn);
		
		if(result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.close(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}


}
