<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.kh.notice.model.vo.Notice" %>
    
<% 
	Notice n = (Notice)request.getAttribute("n");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 상세조회</title>
<style>

	.outer{
			width: 1000px;
			margin: auto;
			background-color: forestgreen;
			margin-top: 5px;
			color: antiquewhite;
		}


	#detail-area{
		text-align: center;
		border : 1px solid wheat;
	}
</style>
</head>
<body>
	
	<%@include file="../common/menubar.jsp" %>
	
	<div class="outer">
		<br>
		<h2 align="center">참 예쁜 공지사항</h2>
		<br><br>

		<table id="detail-area" align="center" border="1">
			<tr>
				<th width="80">제목</th>
				<td width="380" colspan="3"><%= n.getNoticeTitle() %></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td><%= n.getNoticeWriter() %></td>
				<th>작성일</th>
				<td><%= n.getCreateDate() %></td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="3">
					<p style="height: 150px; text-align: left;" ><%= n.getNoticeContent() %></p>
				</td>
			</tr>
		</table>

		<div align="center">
			<a class="btn btn-sm btn-secondary" href="<%= contextPath %>/list.no">목록가기</a>
			<!-- history.back() 이전페이지로 돌아가게해주는 함수-->
			
			<!-- 작성자만 보이게끔 -->
			<!-- 로그인이 되어있고 그리고 현재 로그인한 사용자가 작성자와 동일한 경우 -->
			<% if(loginUser != null && loginUser.getUserId().equals(n.getNoticeWriter())) { %>
				<a href="<%= contextPath %>/updateForm.no?nno=<%= n.getNoticeNo() %>" class="btn btn-sm btn-warning">수정하기</a>
				<a href="<%= contextPath %>/delete.no?nno=<%= n.getNoticeNo() %>" class="btn btn-sm btn-danger">삭제하기</a>
			<% } %>
		</div>
		<br>
	
	</div>

</body>
</html>