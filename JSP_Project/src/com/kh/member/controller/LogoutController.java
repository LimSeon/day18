package com.kh.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutController
 */
@WebServlet("/logout.me")
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그아웃 요청에 대한 처리 => session을 만료시킨다(== 무효화 한다)
		// 무효화 메소드 == invalidate() => session에서 제공하는 메소드
		HttpSession session = request.getSession();
		session.invalidate();
		
		// 응답페이지 => sendRedirect방식
		// index.jsp가 보여지게 끔 => localhost:8001/jsp
		
		// response.sendRedirect("/jsp");
		// 프로젝트명이 바뀌면 "" 바꿔져야함
		
		// request.getContextPath() : contextRoot가 나옴
		response.sendRedirect(request.getContextPath());
		// System.out.println(request.getContextPath()); ///jsp
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
