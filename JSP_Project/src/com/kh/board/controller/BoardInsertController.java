package com.kh.board.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.kh.common.MyFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class BoardInsertController
 */
@WebServlet("/insert.bo")
public class BoardInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1) 인코딩 설정(POST)
		request.setCharacterEncoding("UTF-8");
		
		// 2) 값뽑기 : multipart객체를 이용해보자
		String userNo = request.getParameter("userNo");
		// System.out.println(userNo);
		// form태그로 전송을 하는데 일반방식이 아닌 multipart/form-data 방식으로 전송하는 경우
		// request.getParameter로 값 뽑기가 불가함
		// => multipart라는 객체에 값을 이관해서 다뤄야한다.
		
		// 스탭 0) enctype이 multipart/form-data로 잘 전송되었을 경우
		//			전반적인 내용들이 수정되도록 조건을 걸어줌
		if(ServletFileUpload.isMultipartContent(request)) {
			// System.out.println("안녕");
			
			// 파일 업로드를 위한 라이브러리 다운로드 (http://www.servlets.com) => (http://www.servlets.com/cos/)
			// 파일 업로드를 위한 라이브러리명 : cos.jar(com.oreilly.servlet의 약자)
			
			// 사용자가 파일을 저장하려는 공간을 만들 예정
			// 사용가 파일을 요청하면 응답으로 파일을 주어야 함 
			// 저장하는 공간이 어디에 있어여 하는가?
			// 배포를 위해서는 WebContent에 있어야 함
			// Webcontet/resources/board_upfiles
			// 스텝 1) 전송되는 파일을 처리할 작업
			// (전송되는 파일의 용량제한, 전달된 파일을 저장할 경로)
			// 1_1. 전송파일 용량 제한
			// 	(int maxSize 10MByte)
			/* 
			 * 단위 정리
			 * Byte -> KByte -> MByte -> GByte -> TByte -> PByte ...
			 * 
			 * 1KByte == 1024Byte(2^10)
			 * 1MByte == 1024KByte(2^10) = 1024 * 1024 Byte(2^20)
			 * 
			 */
			int maxSize = 10 * 1024 * 1024; // 10MByte
			
			// 1_2. 전달된 파일을 저장할 서버의 폴더 경로 알아내기(String savePath)
			// 서블릿 클래스에서 경로를 알아내려고 하고 있음
			// getRealPath() 호출하는데 => 인자값으로 WebContent부터 board_upfiles폴더까지의 경로를 제시
			// HttpServletRequest request
			// HttpSession session
			// ServletContext application
			
			HttpSession session = request.getSession();
			ServletContext application = session.getServletContext();
			
			String savePath = application.getRealPath("/resources/board_upfiles/");
			// System.out.println(maxSize);
			// System.out.println(savePath);
			
			// 스탭 2) 서버에 업로드 작업(파일명 수정)
			
			/*
			 * * HttpServletRequest request => MultipartRequest multiRequest객체로 변환
			 * 
			 * MultipartRequeset객체 생성 방법 : 매개변수 생성자를 이용해서 생성(cos.jar에서 제공)
			 * 
			 * [ 표현법 ]
			 * MultipartRequest multiRequest
			 * 		= new MultipartReqest(request, savePath, maxSize, "인코딩", 파일명을 수정시켜주는 객체);
			 * 
			 * 위 구문 한줄 실행만으로 첨부파일이 그대로 무조건 업록드!!
			 * 사용자가 올린 파일명은 그대로 해당 폴더에 업로드 하지 않는 것이기 때문에 일반적이기 때문에
			 * 
			 * 파일명을 수정시켜주는 객체를 생성
			 * 
			 * Q) 파일명을 수정하는 이유는??
			 * A) 같은 파일명이 있을 경우를 대비
			 * 	   파일명에 한글 / 특수문자/ 공백문자가 포함된 경우 서버에 따라 문제가 발생할 수도 있기 때문에
			 * 
			 * 기본적으로 파일명을 수정시켜주는 객체 => FileRenamePolicy객체(cos.jar에서 제공)
			 * => 내부적으로 rename()호출하면서 파일명 수정
			 * => 기본적으로 동일한 파일명이 있을 경우 뒤에 숫자를 카운팅해서 붙여줌
			 * => aaa.jpg, aaa1.jpg, aaa2.jpg
			 * 
			 * 드럽게 성의가 없음...
			 * 
			 * 예 ) KAKAOTALK_YYYYMMDD_HHMMSSRR카카오톡
			 * 
			 * 우리 입맛대로 파일명을 수정해서 절대로 파일명이 안겹치게끔 rename해봅시다.
			 * 
			 */
			
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy());
		}
		
		/*
		userNo
		category
		title
		content
		upfile
		*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
