<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<style>
	.outer{
		width: 1000px;
		margin: auto;
		background-color: forestgreen;
		margin-top: 5px;
		color: antiquewhite;
	}

	#enroll-form input{ margin : 5px; }

</style>
</head>
<body>
	
	<%@ include file="../common/menubar.jsp" %>
	<!-- ../ 내 위치에서 상위폴더 한개로 올라감 -->

	<div class="outer">
		<br>
		<h2 align="center">회원가입</h2>

		<form action="<%= contextPath%>/insert.me" action="post" id="enroll-form">

			<!-- 회원아이디, 회원비밀번호, 회원명, 전화번호, 이메일, 주소, 취미 -->

			<table align="center">
				<tr>
					<td>* 아이디</td>
					<td><input type="text" name="userId" maxlength="12" required></td>
					<td><button>중복확인</button></td>
					<!-- 중복확인 나중에 AJAX배우고 다음주에 할 것 -->
				</tr>
				<tr>
					<td>* 비밀번호</td>
					<td><input type="password" name="userPwd" maxlength="15" required></td>
					<td></td>
				</tr>
				<tr>
					<td>* 비밀번호 확인</td>
					<td><input type="password" maxlength="15" required></td>
					<td></td>
				</tr>
				<tr>
					<td>* 이름</td>
					<td><input type="text" name="userName" maxlength="5" required></td>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;전화번호</td>
					<td><input type="text" name="phone" placeholder="- 포함해서 입력해주세요"></td>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;이메일</td>
					<td><input type="email" name="email"></td>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;주소</td>
					<td><input type="text" name="address" placeholder="주소 입력"></td>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;취미</td>
					<td colspan="2">
						<input type="checkbox" name="interest" id="climbing" value="등산"><label for="climbing">등산</label>
						<input type="checkbox" name="interest" id="drinking" value="음주"><label for="drinking">음주</label>
						<input type="checkbox" name="interest" id="challenge" value="챌린지"><label for="challenge">챌린지</label>
						<br>
						<input type="checkbox" name="interest" id="community" value="커뮤니티"><label for="community">커뮤니티</label>
						<input type="checkbox" name="interest" id="vegetarian" value="채식"><label for="vegetarian">채식</label>
						<input type="checkbox" name="interest" id="rest" value="휴식"><label for="rest">휴식</label>
					</td>
				</tr>
			</table>
			<br><br>

			<div align="center">
				<button type="submit">회원가입</button>
				<button type="reset">초기화</button>
			</div>

		</form>
	
	
	</div>




</body>
</html>