<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 작성</title>
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
		<h2 align="center">공지사항 작성하기!</h2>
		<br><br>


		<form action="<%= contextPath %>/insert.no" method="post" id="enroll-form">
			<input type="hidden" name="userNo" value="<%= loginUser.getUserNo() %>">
			<table align="center">
				<tr>
					<th width="50">제목</th>
					<td width="580"><input type="text" name="title" required></td>
				</tr>
				<tr>
					<th>내용</th>
					<td></td>
				</tr>
				<tr>
					<td colspan="2">
						<textarea name="content" style="resize:none;" rows="10" required></textarea>
					</td>
				</tr>
			</table>
			<br><br>

			<div align="center">
				<button type="submit" class="btn btn-sm btn-primary">등록하기</button>
				<button type="button" class="btn btn-sm btn-secondary" onclick="history.back();">뒤로가기</button>
				<!-- history.back() 이전페이지로 돌아가게해주는 함수-->
			</div>

		</form>
	
	</div>


</body>
</html>