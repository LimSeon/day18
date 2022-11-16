package com.kh.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeDetailController
 */
@WebServlet("/detail.no")
public class NoticeDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeDetailController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1) GET방식 = > 인코딩X
		
		// localhost:8001/jsp/detail.no?key=value
		// 2) request로 부터 값뽑기
		int noticeNo = Integer.parseInt(request.getParameter("nno"));
		
		// 3) VO 가공하기
		
		// 4) Service단으로 토스 - 1(클릭 했을 때 공지사항 조회수를 UPDATE)
		int result = new NoticeService().increaseCount(noticeNo);
		
		// 4) Service단으로 토스 -2(UPDATE가 성공했다면 상세조회 요청)
		if(result > 0 ) {
			Notice n = new NoticeService().selectNotice(noticeNo);
			request.setAttribute("n", n);
			request.getRequestDispatcher("views/notice/noticeDetailView.jsp").forward(request, response);
		} else { // 실패 => 에러페이지 보내기
			request.setAttribute("errorMsg", "공지사항 상세조회 실패");
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
