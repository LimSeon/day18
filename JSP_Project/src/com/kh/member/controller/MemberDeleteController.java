package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class MemberDeleteController
 */
@WebServlet("/deleteMem.me")
public class MemberDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1) POST 방식으로 인코딩
		request.setCharacterEncoding("UTF-8");
		// 2) 값 뽑기
		// 방법 1. input type="hidden"으로 애초에 요청시 숨겨서 전달
		// String userId = request.getParameter("userId");
		// 방법 2. session 영역에 담겨있는 회원객체로부터 뽑아온다.
		
		// 세션에 담겨있는 기존 르그인된 사용자의 정보를 얻오와보겠음
		HttpSession session = request.getSession();
		String userId = ((Member)session.getAttribute("loginUser")).getUserId();
		String userPwd = request.getParameter("userPwd");
		// 3) vo가공
		
		// 4) Serivce단으로  토스
		// Member deleteMem = new MemberService().deleteMember(userId, userPwd);
		int result = new MemberService().deleteMember(userId, userPwd);
		
		/*
		// 5) 결과값을 통해 성공 실패 응답문
		if(deleteMem == null) {
			session.setAttribute("alertMsg", "회원탈퇴 실패");
			response.sendRedirect(request.getContextPath() + "/myPage.me");
		} else {
			session.setAttribute("alertMsg", "회원탈퇴 성공");
			session.setAttribute("loginUser", deleteMem);
			response.sendRedirect(request.getContextPath() + "/logout.me");
		}*/
		
		if(result > 0) {
			session.removeAttribute("loginUser");
			response.sendRedirect(request.getContextPath());
		} else {
			request.setAttribute("errorMsg", "회원탈퇴에 실패");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
		}
		
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
