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
 * Servlet implementation class NoticeUpdateController
 */
@WebServlet("/update.no")
public class NoticeUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeUpdateController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1) 인코딩
		request.setCharacterEncoding("UTF-8");
		// 2) 값 뽑기
		// 뽑아야하는 값 : 제목, 내용(제목, 내용은 중복이 발생할 수 있기 때문에 식별자 역할을 할 수 없음) - PK가 noitceNo
		int noticeNo = Integer.parseInt(request.getParameter("nno"));
		String noticeTitle = request.getParameter("title");
		String noticeContent = request.getParameter("content");
		// 3) 가공처리
		Notice n = new Notice();
		n.setNoticeNo(noticeNo);
		n.setNoticeTitle(noticeTitle);
		n.setNoticeContent(noticeContent);
		// 4) Service단으로 토스
		int result = new NoticeService().updateNotice(n);
		
		// 5) 결과값에 따라 응답페이지 지정
		if(result > 0 ) { // 성공 => 해당 글 상세보기 페이지로 응답뷰 지정
						  // localhost:8001/jsp/detail.no?nno=상세보기할 글 번호
			request.getSession().setAttribute("alertMsg", "수정성공");
			response.sendRedirect(request.getContextPath() + "/detail.no?nno=" + noticeNo);
		} else {
			request.setAttribute("errorMsg", "공지사항 수정에 실패하셨습니다.");
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
