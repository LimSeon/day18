package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MyPageController
 */
@WebServlet("/myPage.me")
public class MyPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyPageController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 1. Forwarding
		// 2. Redirect
		/*
		RequestDispatcher view = request.getRequestDispatcher("views/member/myPage.jsp");
		view.forward(request, response);
		*/
		
		// 로그 아웃 후 url을 직접 요청 했더니 마이페이지가 뜸
		
		// 접속자의 정보 => session
		// 로그인 전 : loginUser 키값에 해당하는 벨류가 null값
		// 로그인 후 : loginUser 키값에 해당하는 벨류가 Member => 포워딩
		
		HttpSession session = request.getSession();
		// 로그인 했니 // 안했니
		if(session.getAttribute("loginUser") == null) {
			session.setAttribute("alertMsg", "로그인 없이 접근이 불가능합니다.");
			response.sendRedirect(request.getContextPath());
		} else {
			request.getRequestDispatcher("views/member/myPage.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
