package com.kh.notice.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.notice.model.service.NoticeService;

/**
 * Servlet implementation class NoticeDeleteController
 */
@WebServlet("/delete.no")
public class NoticeDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeDeleteController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1) 인코딩
		// request.setCharacterEncoding("UTF-8");
		// 2) 값 뽑기
		int noticeNo = Integer.parseInt(request.getParameter("nno"));
		// 3) VO가공
		// 4) Service단으로 토스
		int result = new NoticeService().deleteNotice(noticeNo);
		// 5) 결과값에 따라 응답페이지 지정
		if(result > 0 ) { // 성공 => 해당 글 상세보기 페이지로 응답뷰 지정
						  // localhost:8001/jsp/detail.no?nno=상세보기할 글 번호
			request.getSession().setAttribute("alertMsg", "삭제성공");
			response.sendRedirect(request.getContextPath() + "/list.no");
		} else {
			request.setAttribute("errorMsg", "공지사항 삭제에 실패하셨습니다.");
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
