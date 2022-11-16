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
 * Servlet implementation class NoticeUpdateFormController
 */
@WebServlet("/updateForm.no")
public class NoticeUpdateFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeUpdateFormController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// GET방식
		
		// 2) 값 뽑기
		int noticeNo = Integer.parseInt(request.getParameter("nno"));
		
		// 3) 가공
		
		// 4) Service단으로 토스
		// 상세 조회 시 selectNotice메소드를 재활용 => 호출만 하면 끝
		Notice n = new NoticeService().selectNotice(noticeNo);
		// n에 글번호, 글제목, 글내용, 작성자 아이디, 작성일
		
		// 5) 응답뷰지정
		request.setAttribute("n", n);
		
		// 참고 : forwarding이랑 sendRedirect방식
		// - forwarding : 구체적인 파일 디렉토리 경로를 제시해서 응답해야 할 대
		// - sendRedirect : contextPath로 시작하는 url을 응답해야 할 때
		request.getRequestDispatcher("views/notice/noticeUpdateForm.jsp").forward(request, response);
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
