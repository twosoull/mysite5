<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite02/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite02/assets/css/board.css" rel="stylesheet" type="text/css">

</head>


<body>
	<div id="wrap">

		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
			

		<div id="aside">
			<h2>게시판</h2>
			<ul>
				<li><a href="">일반게시판</a></li>
				<li><a href="">댓글게시판</a></li>
			</ul>
		</div>
		<!-- //aside -->

		<div id="content">

			<div id="content-head">
				<h3>게시판</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>게시판</li>
						<li class="last">일반게시판</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="board">
				<div id="list">
					<form action="/mysite02/board" method="get">
						<div class="form-group text-right">
							<input type="text" name = "searches">
							<button type="submit" id=btn_search>검색</button>
						</div>
						<input type = "hidden" name = "action" value="search">
					</form>
					<table >
						<thead>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>글쓴이</th>
								<th>조회수</th>
								<th>작성일</th>
								<th>관리</th>
							</tr>
						</thead>
						<tbody>
					<c:forEach items= "${boardList}" var = "vo"  varStatus="st" >
				
							<tr>
								<td>${vo.no }</td>
								<td class="text-left"><a href="/mysite02/board?action=read&no=${vo.no }">${vo.title}</a></td>
								<td>${vo.uName }</td>
								<td>${vo.hit }</td>
								<td>${vo.regDate }</td>
									<td>
								<c:if test = "${vo.userNo == authUser.no }">
										<a href="/mysite02/board?action=delete&no=${vo.no }">[삭제]</a>
								</c:if>
									</td>
							</tr>
					
					</c:forEach>
						</tbody>
					</table>
		
					<div id="paging">
						<ul>
							<li><a href="">◀</a></li>
							<li><a href="">1</a></li>
							<li><a href="">2</a></li>
							<li><a href="">3</a></li>
							<li><a href="">4</a></li>
							<li class="active"><a href="">5</a></li>
							<li><a href="">6</a></li>
							<li><a href="">7</a></li>
							<li><a href="">8</a></li>
							<li><a href="">9</a></li>
							<li><a href="">10</a></li>
							<li><a href="">▶</a></li>
						</ul>
						
						
						<div class="clear"></div>
					</div>
					
					<!-- url로 접근이 가능 -->
					<c:if test="${authUser != null }">
						<a id="btn_write" href="/mysite02/board?action=writeForm ">글쓰기</a>
					</c:if>
				
				</div>
				<!-- //list -->
			</div>
			<!-- //board -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>

		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
			
	</div>
	<!-- //wrap -->

</body>

</html>
