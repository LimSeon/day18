package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class MemberInsertController
 */
@WebServlet("/insert.me")
public class MemberInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberInsertController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// POST
		// 1) 인코딩 설정
		request.setCharacterEncoding("UTF-8");
		
		// 2) request객체로부터 요청 시 전달 값 뽑기
		String userId = request.getParameter("userId");						// 필수입력	
		String userPwd = request.getParameter("userPwd");					// 필수입력
		String userName = request.getParameter("userName");					// 필수입력
		String phone = request.getParameter("phone");						// 빈문자열이 들어갈 수 있다.
		String email = request.getParameter("email");						// 빈문자열이 들어갈 수 있다.
		String address = request.getParameter("address");					// 빈문자열이 들어갈 수 있다.
		String[] interestArr = request.getParameterValues("interest");		// [음주, 등산, 챌린지 ..] / null
		
		// 음주, 등산, 챌린지
		// String.join("구분자", 배열명);
		String interest = "";
		
		if(interestArr != null) {
			interest = String.join(",", interestArr);
		}
		// 3) Member 객체에 담기(setter메소드이용)
		Member m = new Member();
		m.setUserId(userId);
		m.setUserPwd(userPwd);
		m.setUserName(userName);
		m.setPhone(phone);
		m.setEmail(email);
		m.setAddress(address);
		m.setInterest(interest);
		
		// 4) 요청처리 (Service단으로 토스)
		int result = new MemberService().insertMember(m);
		
		if(result > 0) { // 성공 
			request.getSession().setAttribute("alertMsg", "회원가입 성공");
			response.sendRedirect(request.getContextPath());
		} else { // 실패
			request.setAttribute("errorMsg", "회원가입에 실패");
			RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
			view.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
