<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, com.kh.board.model.vo.Category"%>    
    
<%
	ArrayList<Category> list = (ArrayList<Category>)request.getAttribute("list");
%>    
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일반 게시글 작성하기</title>

<style>

	body{
		box-sizing: border-box;
	}
	.outer{
			width: 1000px;
			margin: auto;
			background-color: forestgreen;
			margin-top: 5px;
			color: antiquewhite;
		}

	#enroll-form input, #enroll-form textarea{ 
		width: 100%;
		}
	
	#enroll-form>table{
		border : 1px solid wheat;
	}
</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
	
	<div class="outer">
	
	<br>
	<h2 align="center">일반게시글 작성하기</h2>
	<br>
	<!-- 파일을 첨부하는 요청을 할 때는 반드시 form태그에 enctype="multipart/form-data"를 추가해야 한다. -->
	<form action="<%= contextPath%>/insert.bo" id="enroll-form" method="post" enctype="multipart/form-data">
	<!-- 제목, 내용, 카테고리, 버튼, 글쓴이, 첨부파일 -->
	<!-- 작성자의 회원번호를 hidden으로 같이 넘겨서 board테이블에 INSERT하게 만들것-->	
	<input type="hidden" name="userNo" value="<%= loginUser.getUserNo() %>">

	<table align="center">
		<tr>
			<th width="150">카테고리</th>
			<td width="600">
				<select name="category">
					<%for(Category c : list){%>
						<option value="<%= c.getCategoryNo()%>"><%= c.getCategoryName() %></option>
					<% }%>
				</select>
			</td>
		</tr>
		<tr>
			<th>제목</th>
			<td><input type="text" name="title" required></td>
		</tr>
		<tr>
			<th>내용</th>
			<td>
				<textarea name="content" style="resize:none" required rows="10"></textarea>
			</td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td><input type="file" name="upfile"><td>
		</tr>
	</table>
	<br>
	<div align="center">
		<button type="submit">작성하기</button>
		<button type="reset">취소하기</button>
	</div>


	</form>
	
	</div>




</body>
</html>