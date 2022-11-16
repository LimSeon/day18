<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, com.kh.notice.model.vo.Notice" %>
<%
	ArrayList<Notice> list = (ArrayList<Notice>)request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 리스트</title>
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
        <h2 align="center">공지사항</h2>
        <br>
        
        <!-- 관리자만 글작성 버튼이 보이게끔 -->
        <!-- 로그인이 되어있고 그리고 관리자일 경우 -->
        <% if(loginUser != null && loginUser.getUserId().equals("admin")) {%>
        	<div style="width:870px;" align="right">
        		<!-- 버튼에 href속성이 없기 때문에 버튼을 눌러서 페이지를 이동시키고 싶으면
        			 onclick="location.href='요청url'"
        		<button>글작성</button>
        		-->
        		<!-- a태그를 버튼모양을 만들고 싶다면?? : 부트스트랩 -->
        		<a class="btn btn-sm btn-info" href="<%= contextPath %>/enrollForm.no">글작성</a>
        		
        	</div>
        	<br><br>
        <% } else { %>
        <% } %>
        
        
        <table class="list-area" align="center">
            <thead>
                <tr>
                    <th>글번호</th>
                    <th width="400">글제목</th>
                    <th width="100">작성자</th>
                    <th>조회수</th>
                    <th width="150">작성일</th>
                </tr>
            </thead>
            <tbody>
               	<!-- 공지사항이 있을수도 있고 없을 수도 있음 -->
               	<% if(list.isEmpty()){ %>
               		<!-- 공지사항이 없다는 뜻 -->
               		<tr>
               			<td colspan="5">공지사항이 존재하지 않습니다.</td>
               		</tr>
               	<% } else { %>
               		<!-- 공지사항이 존재할 경우 -->
               		<% for(Notice n : list)  {%>
               			<tr>
               				<td><%= n.getNoticeNo() %></td>
               				<td><%= n.getNoticeTitle() %></td>
               				<td><%= n.getNoticeWriter() %></td>
               				<td><%= n.getCount() %></td>
               				<td><%= n.getCreateDate() %></td>
               			</tr>
               		<% } %>
               	<% } %>
            </tbody>
        </table>
        <br><br><br>
    </div>

	<script>

		$(function(){
			// 상세페이지 요청

			// 1절 tr요소를 선택을 해서 클릭을 해야 함
			$('.list-area>tbody>tr').click(function(){
				// 2절 상세페이지 요청
				// console.log('클릭~');

				// console.log(location.href);
				
				// 클릭했을 때 하당 공지사항의 번호를 넘겨야함
				// 해당 tr태그의 자손 중에서 첫번째 td태그의 값만 필요함
				// console.log($(this).children().eq(0).text());
				var nno = $(this).children().eq(0).text();
				
				// 글번호를 이용한 요청
				// => 대놓고 요청 => url에 키와 벨류를 노출시켜서 보내버리겠다.
				// GET방식 : 요청할url?키=밸류&키=밸류&키=밸류..
				// "쿼리 스트링"
				// localhost:8001/jsp/detail.no?nno=글번호
				location.href = "<%= contextPath%>/detail.no?nno="+nno;
			});
		})
	</script>

</body>
</html>