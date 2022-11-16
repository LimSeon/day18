<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, com.kh.board.model.vo.Board, com.kh.common.model.vo.PageInfo" %>    
<% 
	ArrayList<Board> list = (ArrayList<Board>)request.getAttribute("list");
	PageInfo pi = (PageInfo)request.getAttribute("pi");
	
	// 페이징바 만들 때 필요한 변수 미리 세틍
	int currentPage = pi.getCurrentPage();
	int startPage = pi.getStartPage();
	int endPage = pi.getEndPage();
	int maxPage = pi.getMaxPage();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일반게시판</title>
<style>
	.outer{
		width: 1000px;
		margin: auto;
		background-color: forestgreen;
		margin-top: 5px;
		color: antiquewhite;
	}
    .list-area{
        text-align: center;
        border : 1px solid white;
    }
    .list-area>tbody>tr:hover{
        cursor: pointer;
        background:darkgreen;
    }
</style>
</head>
<body>

	<%@ include file="../common/menubar.jsp" %>

	<div class="outer">
		<br>
		<h2 align="center">일반 게시판</h2>
		<br>
		
		
		<div align="right" style="width:840px">
			<!-- 로그인한 회원만 보여지는 버튼 : -->
			<% if(loginUser != null) { %>
				<a href="<%= contextPath %>/enrollForm.bo" class="btn btn-info btn-sm">글작성</a>
				<br><br>
			<% } %>
		</div>
		

		<table class="list-area" align="center">
			<thead>
				<tr>
					<th width="70">글제목</th>
					<th width="80">카테고리</th>
					<th width="300">제목</th>
					<th width="100">작성자</th>
					<th width="50">조회수</th>
					<th width="100">작성일</th>
				</tr>
			</thead>
			<tbody>
				<!-- 게시글 출력 : 게시글이 있는지 없는지  => isEmpty()이용해서 없는 경우 조건부여-->
				<% if(list.isEmpty()) { %> <!-- 조회결과 없음 -->
					<tr>
						<td colspan="6">조회된 게시글이 없습니다.</td>
					</tr>
				<% } else {%>
					<!-- 반복 : list에 있는 값을 순차적으로 접근해서 뽑아오기 -->
					<% for(Board b : list) {%>
						<tr>
							<td><%= b.getBoardNo() %></td>
							<td><%= b.getCategory() %></td>
							<td><%= b.getBoardTitle() %></td>
							<td><%= b.getBoardWriter() %></td>
							<td><%= b.getCount() %></td>
							<td><%= b.getCreateDate() %></td>
						</tr>
					<% } %>
				<% } %>
			</tbody>
		</table>
		<br><br>
		<div align="center" class="paging-area">
			<% if(1 != currentPage) { %>
				<button class="btn btn-sm btn-info" onclick="location.href='<%= contextPath%>/list.bo?cpage=<%= currentPage - 1%>'">&lt;</button>
			<% } else {%>
				<button class="btn btn-sm btn-info" disabled>&lt;</button>
			<% } %>
			<% for(int i = startPage; i<= endPage; i++) {%>
				<% if(currentPage != i) {%>
					<button class="btn btn-sm btn-info" onclick="location.href='<%= contextPath %>/list.bo?cpage=<%= i %>'"><%= i %></button>
				<% } else {%>
					<button class="btn btn-sm btn-info" disabled><%= i %></button>
				<% } %>
			<% } %>
			<% if(maxPage != currentPage) {%>
				<button class="btn btn-sm btn-info" onclick="location.href='<%= contextPath %>/list.bo?cpage=<%= currentPage + 1 %>'">&gt;</button>
			<% } else {%>
				<button class="btn btn-sm btn-info" disabled>&gt;</button>
			<% } %>
		</div>
		<br><br><br>
	</div>
</body>
</html>