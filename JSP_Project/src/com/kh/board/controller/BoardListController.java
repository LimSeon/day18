package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Board;
import com.kh.common.model.vo.PageInfo;

/**
 * Servlet implementation class BoardListController
 */
@WebServlet("/list.bo")
public class BoardListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardListController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 쿼리스트링으로 요청 /jps/list.bo?cpage=1 GET방식 인코딩X
		// 2) request에서 값 뽑기
		
		// ------- 페이징처리 -------
		// 필요한 변수들
		int listCount; 		// 현재 일반게시판의 게시글 총 개수 => BOARD로 부터 조회 COUNT(*)활용 (STATUS = 'Y')
		int currentPage; 	// 현재 페이지(즉, 사용자가 요청한 페이지) => request.getParameter("cpage");
		int pageLimit; 		// 페이지 하단에 보여질 페이징바의 최대 개수 => 10개로 고정
		int boardLimit;		// 한 페이지에 보여질 게시글의 최대 개수 => 10개로 고정
		
		int maxPage;		// 가장 마지막 페이지가 몇 번 페이지 인지(총 페이지의 개수)
		int startPage;		// 페이지 하단에 보여질 페이징의 시작 수
		int endPage;		// 페이지 하단에 보여질 페이징바의 끝 수
		
		// * listCount : 총 게시글의 수
		listCount = new BoardService().selectListCount();
		// System.out.println(listCount);
		
		// * currentPage : 현재 페이지(== 사용자가 요청한 페이지)
		currentPage = Integer.parseInt(request.getParameter("cpage"));
		
		// * pageLimt : 페이징바의 최대 개수
		pageLimit = 10;
		
		// * boardLimit : 한 페이지에 보여질 게시글의 최대 개수
		boardLimit = 10;
		
		// * maxPage : 가장 마지막 페이지가 몇 페이지인지(총 페이지 개수)
		
		/*
		 * boardLinit, listCount에 영향을 받음 
		 * - 공식 구하기
		 * 	  단, boardLimit이 10이라는 가정하에 규칙을 찾아보자 
		 * 	 
		 * 	 총 개수(listCount)	boardLimit(10개)		maxPage(마지막페이지)
		 * 	 100개				10개					= 10번 페이지
		 *	 102개				10개					= 11번 페이지
		 *	 107개				10개					= 11번 페이지
		 *	 110개				10개					= 11번 페이지 
		 *	 111개				10개					= 12번 페이지
		 *	
		 *	=> 나눗셈결과(listCount/boardLimit)를 올림처리 할 경우 maxPage가 된다.
		 *  스탭
		 *  1) listCount를 double로 형변환
		 *  2) listCount / boardLimit
		 *  3) 결과값을 int로 형변환
		 */
		// maxPage = (int)Math.ceil((double)listCount / boardLimit);
		
		maxPage = listCount / boardLimit;
		
		maxPage = (listCount % boardLimit) == 0 ? maxPage : maxPage + 1; 
		
		// * startPage : 페이징 하단에 보여질 페이징바의 시작 수
		/*
		 * pageLimit, currentPage에 영향을 받음
		 * 
		 * - 공식 구하기
		 * 	 단, pageLimit 10이라는 가정하에 규칙을 구해보자
		 * 
		 * startPage : 1, 11, 21, 31, 41 => .. => n * 10 + 1
		 *  
		 *  만약에 pageLimit이 5였다면??
		 *  
		 *  startPage : 1, 6, 11, 16 ... => n * 5 + 1
		 *  
		 *  즉, startPage = n * pageLimit + 1;
		 * 
		 *  currentPage			startPage
		 *  	1					1
		 * 		5					1
		 * 		10					1
		 * 		13					11
		 * 		20					11
		 * 		28					21
		 * 
		 * => 1 ~ 10 : n * 10 + 1 => n = 0
		 * => 11 ~ 20 : n * 10 + 1 => n = 1
		 * => 21 ~ 30 : n * 10 + 1 => n = 2
		 * 
		 * 1   ~ 10	/ 10 => 0 / 1
		 * 11  ~ 20	/ 10 => 1 / 2
		 * 21  ~ 30 / 10 => 2 / 3   1씩 모자르네
		 * 
		 * 0 ~ 9 	/ 10 => 0
		 * 10 ~ 19	/ 10 => 1
		 * 20 ~ 29  / 10 => 2
		 * 
		 * n = (currentPage - 1) / pageLimit
		 * 
		 * startPage = (currentPage -1) / pageLimit * pageLimit + 1;
		 */
		
		startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		
		// * endPage : 페이지 하단에 보여질 페이빙바의 끝 수
		/*
		 * startPage, pageLimit에 영향을 받음(단, maxPage도 마지막 페이징바에 대해선 영향을 준다.) 
		 * 
		 * - 공식 구하기
		 * 단, pageLimit이 10이라는 가정하에
		 * startPage : 1	=> endPage : 10
		 * startPage : 11 	=> endPage : 20
		 * startPage : 21	=> endPage : 30
		 * ....
		 * => endPage = startPage + pageLimit -1 
		 * 
		 */
		
		endPage = startPage + pageLimit - 1; 
		
		// startPage가 11이어서 endPage에는 20이 들어갔는데
		// maxPage가 13이라면?
		// endPage값을 maxPage값으로 변경
		endPage = (endPage > maxPage) ? maxPage : endPage;
		
		// 여기까지 총 7개의 변수를 만들었음!
		// 3) VO로 가공
		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);
		
		// System.out.println(pi);
		// 4) Service로 보내자
		ArrayList<Board> list = new BoardService().selectListCount(pi);
		
		// 5) 응답화면 지정
		request.setAttribute("list", list); // 우리가 실제로 조회한 페이지에 보여질 10개의 게시글
		request.setAttribute("pi", pi); 	// 페이징바를 만들 때 필요한 변수
		
		// views/board/boardListView.jsp 화면으로 응답 => 포워딩
		request.getRequestDispatcher("views/board/boardListView.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
